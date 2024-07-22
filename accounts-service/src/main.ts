import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';

async function bootstrap() {
  if (!process.env.JWT_KEY) {
    throw new Error('JWT_KEY must be defined');
  }
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);
}
bootstrap();
