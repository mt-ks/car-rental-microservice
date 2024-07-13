import { Test, TestingModule } from '@nestjs/testing';
import { MongooseModule } from '@nestjs/mongoose';

import { PermissionService } from './permission.service';
import { Permission, PermissionSchema } from './schemas/permission.schema';
import { MongoMemoryServer } from 'mongodb-memory-server';
import mongoose from 'mongoose';
import { CreatePermissionDto } from './dtos/create-permission.dto';

describe('Permission Service', () => {
  let service: PermissionService;
  let mongod: MongoMemoryServer;
  let module: TestingModule;

  beforeAll(async () => {
    mongod = await MongoMemoryServer.create();
    const uri = mongod.getUri();

    module = await Test.createTestingModule({
      imports: [
        MongooseModule.forRoot(uri),
        MongooseModule.forFeature([
          { name: Permission.name, schema: PermissionSchema },
        ]),
      ],
      providers: [PermissionService],
    }).compile();

    service = module.get<PermissionService>(PermissionService);
  });
  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('create a permission', async () => {
    const dto = {
      permission_name: 'accounts:create',
      user_id: '000-000-001',
    };
    const create = await service.create(dto);
    expect(create).toHaveProperty('_id');
    expect(create.permission_name).toBe(dto.permission_name);
    expect(create.user_id).toBe(dto.user_id);
  });

  it('Update permission of user', async () => {
    const dto = {
      permission_name: 'accounts:created',
      user_id: '000-000-002',
    };
    const create = await service.create(dto);
    const update = await service.update(create._id as string, {
      permission_name: 'accounts:create',
    });
    expect(create).toHaveProperty('_id');
    expect(create.permission_name).toBe('accounts:created');
    expect(update.permission_name).toBe('accounts:create');
  });

  it('Check getAllPermissions and delete', async () => {
    const dto = {
      permission_name: 'accounts:create',
      user_id: '000-000-003',
    };
    const create = await service.create(dto);

    expect(create).toHaveProperty('_id');
    const deletePerm = await service.delete(dto.user_id, dto.permission_name);
    const all = await service.getAllPermissions(dto.user_id);
    expect(deletePerm).toHaveProperty('_id');
    expect(all.length).toEqual(0);
  });

  afterAll(async () => {
    await mongod.stop();
    await mongoose.disconnect();
  });
});
