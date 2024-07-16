import {
  BadRequestException,
  Inject,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { CreateAccountDto } from './dtos/create-account.dto';
import { InjectModel } from '@nestjs/mongoose';
import { User } from './schemas/user.schema';
import { Model } from 'mongoose';
import { LoginAccountDto } from './dtos/login-account.dto';
import { PasswordEncryption } from './password-encryption.service';

@Injectable()
export class UsersService {
  constructor(
    @InjectModel(User.name) private model: Model<User>,
    @Inject() private passwordService: PasswordEncryption,
  ) {}

  async create(createAccountDto: CreateAccountDto) {
    const isEmailInUse = await this.isEmailInUse(createAccountDto.email);
    if (isEmailInUse) {
      throw new BadRequestException('Email already in use');
    }

    createAccountDto.password = await this.passwordService.encrypt(
      createAccountDto.password,
    );

    const account = new this.model(createAccountDto);

    return account.save();
  }

  async isEmailInUse(email: string) {
    const find = await this.model.findOne({ email });
    return !!find;
  }

  async login(loginDto: LoginAccountDto) {
    const account = await this.model.findOne({ email: loginDto.email });
    if (!account) {
      throw new UnauthorizedException('incorrect email or password');
    }

    const compare = await this.passwordService.compare(
      loginDto.password,
      account.password,
    );

    if (!compare) {
      throw new UnauthorizedException('incorrect email or password');
    }

    return account;
  }

  async getUser(id: string) {
    return await this.model.findOne({ _id: id });
  }
}
