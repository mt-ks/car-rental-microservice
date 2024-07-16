import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AccountsModule } from '../src/account/accounts.module';
import { MongooseModule } from '@nestjs/mongoose';
import { User, UserSchema } from '../src/account/schemas/user.schema';
import { UsersService } from '../src/account/users.service';
import { MongoMemoryServer } from 'mongodb-memory-server';
import { ConfigService } from '@nestjs/config';
import mongoose from 'mongoose';
import createFakeAccountDto from '../src/account/faker/create-account-dto-fake';

describe('AccountsController (e2e)', () => {
  let app: INestApplication;
  let accountsService: UsersService;
  let mongoServer: MongoMemoryServer;
  beforeAll(async () => {
    mongoServer = await MongoMemoryServer.create();
    const mongoUri = mongoServer.getUri();

    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [
        MongooseModule.forRootAsync({
          useFactory: async (configService: ConfigService) => ({
            uri: mongoUri,
          }),
        }),
        AccountsModule,
      ],
    }).compile();

    accountsService = moduleFixture.get<UsersService>(UsersService);
    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/api/accounts/create successfuly (POST)', async () => {
    const response = await request(app.getHttpServer())
      .post('/api/accounts/create')
      .send({
        email: 'test@test.com',
        password: 'staticpassword',
        name: 'Mt',
        surname: 'Ks',
        nationality: 'TR',
      });

    expect(response.body).toHaveProperty('status');
    expect(response.body).toHaveProperty('user');
    expect(response.body).toHaveProperty('user.email');
    expect(response.body).toHaveProperty('user.name');
    expect(response.body).toHaveProperty('user.surname');
    expect(response.body).toHaveProperty('user.nationality');
    expect(response.body).not.toHaveProperty('user.password');
    expect(response.headers).toHaveProperty('authorization');
  });

  it('/api/accounts/create failed (POST)', async () => {
    const response = await request(app.getHttpServer())
      .post('/api/accounts/create')
      .send({
        email: 'test@test.com',
        password: 'staticpassword',
        name: 'Mt',
        surname: 'Ks',
        nationality: 'TR',
      });

    expect(response.body).toHaveProperty('error');
    expect(response.body).toHaveProperty('message');
    expect(response.body).toHaveProperty('statusCode');
  });

  it('/api/accounts/login create account and login successfully & failure (POST)', async () => {
    const response = await request(app.getHttpServer())
      .post('/api/accounts/create')
      .send({
        email: 'test@test.com',
        password: 'staticpassword',
        name: 'Mt',
        surname: 'Ks',
        nationality: 'TR',
      });

    expect(response.body).toHaveProperty('error');
    expect(response.body).toHaveProperty('message');
    expect(response.body).toHaveProperty('statusCode');

    const loginSuccess = await request(app.getHttpServer())
      .post('/api/accounts/login')
      .send({ email: 'test@test.com', password: 'staticpassword' });

    expect(loginSuccess.body).toHaveProperty('status');
    expect(loginSuccess.body).toHaveProperty('user');
    expect(loginSuccess.body).toHaveProperty('user.id');
    expect(loginSuccess.body).toHaveProperty('user.email');
    expect(loginSuccess.body).toHaveProperty('user.name');
    expect(loginSuccess.body).toHaveProperty('user.surname');
    expect(loginSuccess.body).toHaveProperty('user.nationality');

    const loginFailed = await request(app.getHttpServer())
      .post('/api/accounts/login')
      .send({ email: 'test@test.com', password: 'staticpasswordx' });

    expect(loginFailed.statusCode).toEqual(401);
  });

  it('/api/accounts/info (GET)', async () => {
    const createAcc = await request(app.getHttpServer())
      .post('/api/accounts/create')
      .send(createFakeAccountDto());

    const res = await request(app.getHttpServer())
      .get('/api/accounts/info')
      .set('Authorization', `${createAcc.headers.authorization}`);

    expect(res.body).toHaveProperty('status');
    expect(res.body).toHaveProperty('user');
    expect(res.body).toHaveProperty('user.id');
  });

  afterAll(async () => {
    await mongoose.disconnect();
    await mongoServer.stop();
    await app.close();
  });
});
