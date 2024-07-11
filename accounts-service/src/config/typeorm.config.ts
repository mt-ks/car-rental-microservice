import { TypeOrmOptionsFactory, TypeOrmModuleOptions } from '@nestjs/typeorm';
import { ConfigService } from '@nestjs/config';
import { Injectable } from '@nestjs/common';

@Injectable()
export class TypeOrmConfigService implements TypeOrmOptionsFactory {
  constructor(private config: ConfigService) {}

  createTypeOrmOptions(): TypeOrmModuleOptions {
    switch (process.env.NODE_ENV) {
      case 'test':
        return {
          type: this.config.get<any>('DATABASE_TYPE'),
          synchronize: true,
          database: this.config.get<string>('DATABASE_NAME'),
          autoLoadEntities: true,
          migrationsRun: true,
        };
      case 'development':
      case 'production':
        return {
          type: 'postgres',
          synchronize: false,
          host: this.config.get<string>('DATABASE_HOST'),
          port: this.config.get<number>('DATABASE_PORT'),
          username: this.config.get<string>('DATABASE_USER'),
          password: this.config.get<string>('DATABASE_PASSWORD'),
          database: this.config.get<string>('DATABASE_NAME'),
          autoLoadEntities: true,
          migrationsRun: true,
          retryAttempts: 10,
        };
    }
  }
}
