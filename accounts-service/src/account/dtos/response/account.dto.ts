import { Expose } from 'class-transformer';

export class AccountDto {
  @Expose()
  id: string;

  @Expose()
  email: string;

  @Expose()
  name: string;

  @Expose()
  surname: string;

  @Expose()
  nationality: string;
}
