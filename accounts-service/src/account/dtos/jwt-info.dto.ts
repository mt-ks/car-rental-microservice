export class JwtInfoDto {
  id: string;
  permissions: JwtInfoPermissionsDto[];
}

export class JwtInfoPermissionsDto {
  id: string;
  user_id: string;
  permission_name: string;
}
