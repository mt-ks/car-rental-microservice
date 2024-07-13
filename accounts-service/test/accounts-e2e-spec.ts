import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AccountsModule } from '../src/account/accounts.module';
import { MongooseModule } from '@nestjs/mongoose';
import { Account, AccountSchema } from '../src/account/schemas/account.schema';
import { AccountsService } from '../src/account/accounts.service';
import { MongoMemoryServer } from 'mongodb-memory-server';
import { ConfigModule, ConfigService } from '@nestjs/config';
import mongoose from 'mongoose';

describe('AccountsController (e2e)', () => {
  let app: INestApplication;
  let accountsService: AccountsService;
  let mongoServer: MongoMemoryServer;
  beforeEach(async () => {
    mongoServer = await MongoMemoryServer.create();
    const mongoUri = mongoServer.getUri();

    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [
        MongooseModule.forRootAsync({
          useFactory: async (configService: ConfigService) => ({
            uri: mongoUri,
          }),
        }),
        MongooseModule.forFeature([
          { name: Account.name, schema: AccountSchema },
        ]),
        AccountsModule,
      ],
    }).compile();

    accountsService = moduleFixture.get<AccountsService>(AccountsService);
    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/api/accounts/signup (GET)', () => {
    return request(app.getHttpServer())
      .get('/api/accounts/signup')
      .expect(200)
      .expect('signup');
  });
  afterAll(async () => {
    await mongoServer.stop();
    await mongoose.disconnect();
  });
});
