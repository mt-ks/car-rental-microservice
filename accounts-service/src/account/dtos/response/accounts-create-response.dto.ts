import { Exclude, Expose, Type } from 'class-transformer';
import { AccountDto } from './account.dto';

export class AccountsCreateResponseDto {
  @Expose()
  status: string;

  @Expose()
  token: string;

  @Expose()
  @Type(() => AccountDto)
  user: AccountDto;
}
