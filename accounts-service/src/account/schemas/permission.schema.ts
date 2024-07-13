import { Schema, Prop, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema()
export class Permission extends Document {
  @Prop({ required: true })
  user_id: string;

  @Prop({ required: true })
  permission_name: string;
}

export const PermissionSchema = SchemaFactory.createForClass(Permission);
