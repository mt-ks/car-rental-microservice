import { Test, TestingModule } from '@nestjs/testing';
import { MongooseModule } from '@nestjs/mongoose';

import { MongoMemoryServer } from 'mongodb-memory-server';
import mongoose from 'mongoose';
import { AccountsService } from './accounts.service';
import { Account, AccountSchema } from './schemas/account.schema';
import { BadRequestException } from '@nestjs/common';

describe('Accounts Service Test', () => {
  let service: AccountsService;
  let mongod: MongoMemoryServer;
  let module: TestingModule;

  beforeAll(async () => {
    mongod = await MongoMemoryServer.create();
    const uri = mongod.getUri();

    module = await Test.createTestingModule({
      imports: [
        MongooseModule.forRoot(uri),
        MongooseModule.forFeature([
          { name: Account.name, schema: AccountSchema },
        ]),
      ],
      providers: [AccountsService],
    }).compile();

    service = module.get<AccountsService>(AccountsService);
  });

  it('AccountsService should be defined', () => {
    expect(service).toBeDefined();
  });

  it('Create account successfuly. Throw exception when trying create account with used email', async () => {
    const createAccountDto = {
      email: 'test@test.com',
      password: 'mypassword',
      name: 'Mt',
      surname: 'Ks',
      nationality: 'TR',
    };
    const create = await service.create(createAccountDto);
    await expect(service.create(createAccountDto)).rejects.toThrow(
      BadRequestException,
    );
    expect(create).toHaveProperty('_id');
  });

  afterAll(async () => {
    await mongod.stop();
    await mongoose.disconnect();
  });
});
