import {
  Body,
  Controller,
  Get,
  Post,
  Put,
  Req,
  Res,
  UseGuards,
} from '@nestjs/common';
import { UsersService } from './users.service';
import { CreateAccountDto } from './dtos/create-account.dto';
import { Serialize } from './interceptors/serialize.interceptor';
import { AccountsCreateResponseDto } from './dtos/response/accounts-create-response.dto';
import { Response, Request } from 'express';
import { JwtService } from '@nestjs/jwt';
import { PermissionService } from './permission.service';
import { LoginAccountDto } from './dtos/login-account.dto';
import { AccountDto } from './dtos/response/account.dto';
import { AuthGuard } from './guards/auth.guard';
import { JwtInfo } from './decorators/jwt-info.decorator';
import { JwtInfoDto } from './dtos/jwt-info.dto';
import { UpdateAccountDto } from './dtos/update-account.dto';
import { AccountsUpdateResponseDto } from './dtos/response/accounts-update-response.dto';
import { UpdateAccountPartialDto } from './dtos/update-account-partial.dto';

@Controller('api/accounts')
export class AuthController {
  constructor(
    private usersService: UsersService,
    private permissionService: PermissionService,
    private jwtService: JwtService,
  ) {}

  @Serialize(AccountsCreateResponseDto)
  @Post('/create')
  async create(
    @Body() createAccountDto: CreateAccountDto,
    @Res({ passthrough: true }) res: Response,
  ) {
    const user = await this.usersService.create(createAccountDto);
    const token = this.jwtService.sign({
      id: user.id,
      permissions: await this.permissionService.getAllPermissions(user.id),
    });
    res.setHeader('Authorization', `Bearer ${token}`);
    return { status: 'ok', user };
  }

  @Serialize(AccountsCreateResponseDto)
  @Post('/login')
  async login(
    @Body() loginAccountDto: LoginAccountDto,
    @Res({ passthrough: true }) res: Response,
  ) {
    const user = await this.usersService.login(loginAccountDto);
    this.permissionService.create({
      permission_name: 'vehicles:create',
      user_id: user.id,
    });
    const token = this.jwtService.sign(
      {
        id: user.id,
        name: user.name,
        email: user.email,
        permissions: await this.permissionService.getAllPermissions(user.id),
      },
      {
        secret: process.env.JWT_KEY,
      },
    );
    res.setHeader('Authorization', `Bearer ${token}`);
    return {
      status: 'ok',
      user,
    };
  }

  @Get('/info')
  @Serialize(AccountsCreateResponseDto)
  @UseGuards(AuthGuard)
  async me(@JwtInfo() jwtInfo: JwtInfoDto) {
    const user = await this.usersService.getUser(jwtInfo.id);

    return { status: 'ok', user };
  }

  @Put('/edit')
  @Serialize(AccountsUpdateResponseDto)
  @UseGuards(AuthGuard)
  async edit(
    @Body() userInfo: UpdateAccountPartialDto,
    @JwtInfo() info: JwtInfoDto,
  ) {
    const user = await this.usersService.update(info.id, userInfo);
    return {
      status: 'ok',
      user,
    };
  }
}
