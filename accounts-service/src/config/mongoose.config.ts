import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import {
  MongooseModuleOptions,
  MongooseOptionsFactory,
} from '@nestjs/mongoose';
import * as fs from 'fs';

@Injectable()
export class MongooseConfigService implements MongooseOptionsFactory {
  constructor(private configService: ConfigService) {}

  createMongooseOptions(): MongooseModuleOptions {
    return {
      uri: process.env.MONGODB_REPLICASET_URI,
    };
  }

  readMongoSecretSync(secretFileName: string) {
    const secret = fs
      .readFileSync(`/run/secrets/${secretFileName}`, 'utf8')
      .trim();
    return secret;
  }
}
