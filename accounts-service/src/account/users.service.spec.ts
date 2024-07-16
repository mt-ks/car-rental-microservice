import { MongooseModule } from '@nestjs/mongoose';
import { Test, TestingModule } from '@nestjs/testing';
import { MongoMemoryServer } from 'mongodb-memory-server';
import { UsersService } from './users.service';
import { User, UserSchema } from './schemas/user.schema';
import staticFakeCreateAccountDto from './faker/create-account-dto-fake';
import { PasswordEncryption } from './password-encryption.service';
import { BadRequestException, UnauthorizedException } from '@nestjs/common';
import mongoose from 'mongoose';
import createFakeAccountDto from './faker/create-account-dto-fake';

describe('UsersService Test', () => {
  let module: TestingModule;
  let service: UsersService;
  let mongod: MongoMemoryServer;

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

  it('create method test', async () => {
    const dto = createFakeAccountDto();
    const create = await service.create(dto);
    // check all dto keys in created user in db
    for (var key in dto) {
      expect(create[key]).not.toBeUndefined();
    }

    // It throws an error when trying to create account with a used email.
    await expect(service.create(dto)).rejects.toThrow(BadRequestException);
  });

  it('login() test / authentication success', async () => {
    const dto = createFakeAccountDto();
    const password = dto.password;
    const create = await service.create(dto);
    expect(create).toHaveProperty('_id');
    const login = await service.login({
      email: dto.email,
      password: password,
    });
    expect(login).toHaveProperty('_id');
  });

  it('Tests whether an email address is registered.', async () => {
    const isEmailInUse = await service.isEmailInUse('uniqueemail@unique.com');
    expect(isEmailInUse).toBe(false);
  });

  afterAll(async () => {
    await mongod.stop();
    await mongoose.disconnect();
  });
});
