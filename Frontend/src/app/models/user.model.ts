export interface UserResponseDto {
  idUser: number;
  loginUser: string;
  nomUser: string;
  prenomUser: string;
  mobileUser: string;
  siteUser: string;
  mail: string;
  dateCreationUser: string;
  active: boolean;
}

export interface UserDto {
  loginUser: string;
  nomUser: string;
  prenomUser: string;
  passwordUser: string;
  mobileUser?: string;
  siteUser?: string;
  mail: string;
  pwdMail?: string;
  active: boolean;
}

export interface UserUpdateDto {
  loginUser?: string;
  nomUser?: string;
  prenomUser?: string;
  mobileUser?: string;
  siteUser?: string;
  mail?: string;
  pwdMail?: string;
  active?: boolean;
}