import { Controller, Get } from '@nestjs/common';

@Controller('api/accounts')
export class AccountsController {
  @Get('/signin')
  async signin() {
    return 'signing';
  }

  @Get('/signup')
  async signup() {
    return 'signup';
  }
}
