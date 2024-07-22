import { Test, TestingModule } from '@nestjs/testing';
import { AuthController } from './auth.controller';
import { UsersService } from './users.service';
import {
  BadRequestException,
  Res,
  UnauthorizedException,
} from '@nestjs/common';
import { PermissionService } from './permission.service';
import { JwtModule, JwtService } from '@nestjs/jwt';
import { LoginAccountDto } from './dtos/login-account.dto';
import { CreateAccountDto } from './dtos/create-account.dto';
import { UpdateAccountDto } from './dtos/update-account.dto';
import { JwtInfoDto } from './dtos/jwt-info.dto';

describe('AuhtController', () => {
  const users = [];
  const headers = [];
  let jwtService: JwtService;
  const resFakeClass = {
    setHeader: (key: string, value: any) => {
      headers[key] = value;
    },
  } as any;
  let fakeUsersService = {
    login: (loginDto: LoginAccountDto) => {
      const user = users.find((obj) => obj.email === loginDto.email);
      if (!user) {
        throw new UnauthorizedException('incorrect email or password');
      }
      return user;
    },
    create: (createAccountDto: CreateAccountDto) => {
      const isEmailInUse = users.find(
        (obj) => obj.email === createAccountDto.email,
      );
      if (isEmailInUse) {
        throw new BadRequestException('email already in use');
      }
      createAccountDto.password = jwtService.sign({
        password: createAccountDto.password,
      });
      users.push(createAccountDto);
      return createAccountDto;
    },
    update: (id: string, info: Partial<UpdateAccountDto>) => {
      return info;
    },
  };
  let fakePermissionService = {
    getAllPermissions: (userId: string) => {
      return [
        {
          id: '0-0-0-0-0',
          permission_name: 'accounts:create',
          user_id: '000-000-000',
        },
      ];
    },
  };
  let controller: AuthController;
  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        JwtModule.register({
          global: true,
          secret: 'myjwtsecret',
          signOptions: { expiresIn: '60s' },
        }),
      ],
      controllers: [AuthController],
      providers: [
        {
          provide: UsersService,
          useValue: fakeUsersService,
        },
        {
          provide: PermissionService,
          useValue: fakePermissionService,
        },
      ],
    }).compile();

    controller = module.get<AuthController>(AuthController);
    jwtService = module.get<JwtService>(JwtService);
  });

  it('create() method test.', async () => {
    const create = await controller.create(
      {
        email: 'email',
        password: 'password',
        name: 'test name',
        surname: 'test surname',
        nationality: 'TR',
      },
      resFakeClass,
    );
    expect(create).toHaveProperty('status');
    expect(create.status).toBe('ok');
    expect(headers['Authorization']).not.toBeUndefined();
  });

  it('login() method test', async () => {
    const login = await controller.login(
      { email: 'email', password: 'password' },
      resFakeClass,
    );

    expect(login.status).not.toBeUndefined();
    expect(login.status).toBe('ok');
  });

  it('edit() method test', async () => {
    const info = {
      name: 'new name',
    } as Partial<UpdateAccountDto>;
    const fakeJwtInfoDto = { id: '1', permissions: [] } as JwtInfoDto;
    const edit = await controller.edit(info, fakeJwtInfoDto);
    expect(edit.status).toBe('ok');
    expect(edit.user.name).toBe(info.name);
  });
});
