export interface CreateUserDto {
  username: string;
  password: string;
}

export interface UpdateUserDto {
  username: string;
  password: string;
  email: string;
  fullName: string;
}

export interface UserDetailDto {
  id: number;
  username: string;
  email: string;
  fullName: string;
}

export interface RoleDto {
  id: number;
  role: string;
}

export interface RoleListDto {
  roles: Array<RoleDto>;
}
