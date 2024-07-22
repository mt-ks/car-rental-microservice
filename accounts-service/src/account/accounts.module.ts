import { Module, ValidationPipe } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { UsersService } from './users.service';
import { User, UserSchema } from './schemas/user.schema';
import { MongooseModule } from '@nestjs/mongoose';
import { Permission, PermissionSchema } from './schemas/permission.schema';
import { PasswordEncryption } from './password-encryption.service';
import { JwtModule } from '@nestjs/jwt';
import { PermissionService } from './permission.service';
import { AuthGuard } from './guards/auth.guard';
import { APP_PIPE } from '@nestjs/core';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema },
      { name: Permission.name, schema: PermissionSchema },
    ]),
    JwtModule.register({
      global: true,
      secret: process.env.JWT_KEY,
      signOptions: { expiresIn: '60s' },
    }),
  ],

  controllers: [AuthController],
  providers: [
    UsersService,
    PermissionService,
    PasswordEncryption,
    AuthGuard,
    { provide: APP_PIPE, useValue: new ValidationPipe({ whitelist: true }) },
  ],
  exports: [AuthGuard],
})
export class AccountsModule {}
