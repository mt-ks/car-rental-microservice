import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Permission } from './schemas/permission.schema';
import { Model } from 'mongoose';
import { CreatePermissionDto } from './dtos/create-permission.dto';

@Injectable()
export class PermissionService {
  constructor(
    @InjectModel(Permission.name) private permissionModel: Model<Permission>,
  ) {}

  create(createPermissionDto: CreatePermissionDto) {
    const createPermission = new this.permissionModel(createPermissionDto);
    return createPermission.save();
  }

  async getAllPermissions(user_id: string) {
    const permissions = await this.permissionModel.find({ user_id });
    const list = new Set();
    permissions.forEach((item) => {
      list.add(item.permission_name);
    });

    return [...list];
  }

  update(id: string, updateDto: Partial<CreatePermissionDto>) {
    return this.permissionModel.findOneAndUpdate({ _id: id }, updateDto, {
      new: true,
    });
  }

  delete(user_id: string, permission_name: string) {
    return this.permissionModel.findOneAndDelete({ user_id, permission_name });
  }
}
