import { IsNotEmpty, IsString, MinLength } from 'class-validator';

export class UpdateAccountDto {
  @IsString()
  @MinLength(3)
  @IsNotEmpty()
  name: string;

  @IsString()
  surname: string;

  @IsString()
  nationality: string;
}
