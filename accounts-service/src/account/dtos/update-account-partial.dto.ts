import { PartialType } from '@nestjs/swagger';
import { UpdateAccountDto } from './update-account.dto';

export class UpdateAccountPartialDto extends PartialType(UpdateAccountDto) {}
