import { Test, TestingModule } from '@nestjs/testing';
import { MongooseModule } from '@nestjs/mongoose';

import { MongoMemoryServer } from 'mongodb-memory-server';
import mongoose from 'mongoose';
import { UsersService } from './users.service';
import { User, UserSchema } from './schemas/user.schema';
import { BadRequestException, UnauthorizedException } from '@nestjs/common';
import { PasswordEncryption } from './password-encryption.service';

describe('Accounts Service Test', () => {
  let service: UsersService;
  let mongod: MongoMemoryServer;
  let module: TestingModule;

  beforeAll(async () => {
    mongod = await MongoMemoryServer.create();
    const uri = mongod.getUri();

    module = await Test.createTestingModule({
      imports: [
        MongooseModule.forRoot(uri),
        MongooseModule.forFeature([{ name: User.name, schema: UserSchema }]),
      ],
      providers: [UsersService, PasswordEncryption],
    }).compile();

    service = module.get<UsersService>(UsersService);
  });

  it('AccountsService should be defined', () => {
    expect(service).toBeDefined();
  });

  it('Create account successfuly. Throw exception when trying create account with used email', async () => {
    let createAccountDto = {
      email: 'test@test.com',
      password: 'staticpassword',
      name: 'Mt',
      surname: 'Ks',
      nationality: 'TR',
    };
    createAccountDto.email = 'test2@test.com';
    const create = await service.create(createAccountDto);
    expect(create).toHaveProperty('_id');
    await expect(service.create(createAccountDto)).rejects.toThrow(
      BadRequestException,
    );
  });

  it('create awccount and login successfully', async () => {
    let createAccountDto = {
      email: 'test@test.com',
      password: 'staticpassword',
      name: 'Mt',
      surname: 'Ks',
      nationality: 'TR',
    };
    createAccountDto.email = 'test3@test.com';
    const create = await service.create(createAccountDto);

    expect(create).toHaveProperty('_id');

    const login = await service.login({
      email: createAccountDto.email,
      password: 'staticpassword',
    });
    expect(login).toHaveProperty('email');
    // throws an error if auth failed.
    await expect(
      service.login({
        email: createAccountDto.email,
        password: 'thispasswordisincorrect',
      }),
    ).rejects.toThrow(UnauthorizedException);
  });

  afterAll(async () => {
    await mongod.stop();
    await mongoose.disconnect();
  });
});
