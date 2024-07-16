import { CreateAccountDto } from '../dtos/create-account.dto';

const createFakeAccountDto = () => {
  const emailNumber = Math.floor(Math.random() * (100000000 - 1) + 1);

  return {
    email: `test${emailNumber}@test.com`,
    password: 'password',
    name: 'Mt',
    surname: 'Ks',
    nationality: 'TR',
  };
};

export default createFakeAccountDto;
