import { Test, TestingModule } from '@nestjs/testing';
import { PasswordEncryption } from './password-encryption.service';

describe('dad', () => {
  let service: PasswordEncryption;
  let module: TestingModule;

  beforeAll(async () => {
    module = await Test.createTestingModule({
      imports: [],
      providers: [PasswordEncryption],
    }).compile();

    service = module.get<PasswordEncryption>(PasswordEncryption);
  });

  it('encrypt and compare password', async () => {
    const plainTextPassword = 'mypassword';
    const encyptedPassword = await service.encrypt(plainTextPassword);
    expect(encyptedPassword).toBeDefined();
    const compare = await service.compare(plainTextPassword, encyptedPassword);
    expect(compare).toBe(true);
    const compareFailed = await service.compare(
      'myrandompassword',
      encyptedPassword,
    );
    expect(compareFailed).toBe(false);
  });
});
