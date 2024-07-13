import { Body, Controller, Get } from '@nestjs/common';
import { AccountsService } from './accounts.service';
import { CreateAccountDto } from './dtos/create-account.dto';

@Controller('api/accounts')
export class AccountsController {
  constructor(private accountsService: AccountsService) {}

  @Get('/signin')
  async signin(@Body() createAccountDto: CreateAccountDto) {
    return await this.accountsService.create(createAccountDto);
  }

  @Get('/signup')
  async signup() {
    return 'signup';
  }
}
