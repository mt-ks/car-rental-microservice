import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema()
export class User extends Document {
  @Prop({ required: true, unique: true })
  email: string;

  @Prop({ required: true })
  password: string;

  @Prop({ required: true })
  name: string;

  @Prop({ required: true })
  surname: string;

  @Prop({ default: null })
  phone_number: string;

  @Prop({ default: false })
  is_email_verified: boolean;

  @Prop({ default: false })
  is_phone_verified: boolean;

  @Prop()
  nationality: string;
}

export const UserSchema = SchemaFactory.createForClass(User);
