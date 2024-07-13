import { Module } from '@nestjs/common';
import { AccountsModule } from './account/accounts.module';
import { ConfigModule } from '@nestjs/config';
import { MongooseModule } from '@nestjs/mongoose';
import { MongooseConfigService } from './config/mongoose.config';

@Module({
  imports: [
    MongooseModule.forRootAsync({
      imports: [ConfigModule],
      useClass: MongooseConfigService,
    }),
    AccountsModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
