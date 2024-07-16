import { Injectable } from '@nestjs/common';
import { scrypt, randomBytes } from 'crypto';
import { promisify } from 'util';

@Injectable()
export class PasswordEncryption {
  private scryptAsync = promisify(scrypt);

  async encrypt(password: string) {
    const salt = randomBytes(8).toString('hex');
    const buf = (await this.scryptAsync(password, salt, 64)) as Buffer;

    return `${buf.toString('hex')}.${salt}`;
  }

  async compare(suppliedPassword: string, storedPassword: string) {
    const [hashedPassword, salt] = storedPassword.split('.');
    const buf = (await this.scryptAsync(suppliedPassword, salt, 64)) as Buffer;

    return buf.toString('hex') === hashedPassword;
  }
}
