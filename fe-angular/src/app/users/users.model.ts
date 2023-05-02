export interface CreateUserDto {
  username: string;
  password: string;
}

export interface UserDetailDto {
  id: number;
  username: string;
}

export interface RoleDto {
  id: number;
  role: string;
}

export interface RoleListDto {
  roles: Array<RoleDto>;
}
