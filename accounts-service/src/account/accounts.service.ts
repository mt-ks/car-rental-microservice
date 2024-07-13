import { BadRequestException, Inject, Injectable } from '@nestjs/common';
import { CreateAccountDto } from './dtos/create-account.dto';
import { InjectModel } from '@nestjs/mongoose';
import { Account } from './schemas/account.schema';
import { Model } from 'mongoose';

@Injectable()
export class AccountsService {
  constructor(@InjectModel(Account.name) private model: Model<Account>) {}

  async create(createAccountDto: CreateAccountDto) {
    const isEmailInUse = await this.isEmailInUse(createAccountDto.email);
    if (isEmailInUse) {
      throw new BadRequestException('Email already in use');
    }
    const account = new this.model(createAccountDto);
    return account.save();
  }

  async isEmailInUse(email: string) {
    const find = await this.model.findOne({ email });
    return !!find;
  }

  update() {}
}
