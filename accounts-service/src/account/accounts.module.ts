import { Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { UsersService } from './users.service';
import { User, UserSchema } from './schemas/user.schema';
import { MongooseModule } from '@nestjs/mongoose';
import { Permission, PermissionSchema } from './schemas/permission.schema';
import { PasswordEncryption } from './password-encryption.service';
import { JwtModule } from '@nestjs/jwt';
import { PermissionService } from './permission.service';
import { AuthGuard } from './guards/auth.guard';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema },
      { name: Permission.name, schema: PermissionSchema },
    ]),
    JwtModule.register({
      global: true,
      secret: 'myjwtsecret',
      signOptions: { expiresIn: '60s' },
    }),
  ],

  controllers: [AuthController],
  providers: [UsersService, PermissionService, PasswordEncryption, AuthGuard],
  exports: [AuthGuard],
})
export class AccountsModule {}
