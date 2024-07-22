import { Expose, Type } from 'class-transformer';
import { AccountDto } from './account.dto';

export class AccountsUpdateResponseDto {
  @Expose()
  status: string;

  @Expose()
  @Type(() => AccountDto)
  user: AccountDto;
}
