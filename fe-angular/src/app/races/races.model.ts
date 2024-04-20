export interface CreateUdateRaceDto {
  name: string;
  location: string;
  date: string;
  signUpUntil: string;
  description: string;
  isPublic: boolean;
}

export interface RaceDetailDto {
  id: number;
  name: string;
  location: string;
  date: string;
  signUpUntil: string;
  description: string;
  isPublic: boolean;
  mainOrganizerId: number;
  mainOrganizerName: string;
}

export interface RaceUserInfoDto {
  raceId: number;
  userId: number;
  status: RaceUserStatusEnum;
}

export enum RaceUserStatusEnum {
  APPLIED = 'APPLIED',
  REGISTERED = 'REGISTERED'
}

export const RaceUserStatus = {
  [RaceUserStatusEnum.APPLIED]: 'Applied',
  [RaceUserStatusEnum.REGISTERED]: 'Registered'
}

export enum CrewStatusEnum {
  APPLIED = 'APPLIED',
  REGISTERED = 'REGISTERED'
}

export interface CrewDetailDto {
  id: number;
  shipName: string;
  shipRegistration: string;
  shipOwnerName: string;
  status: CrewStatusEnum
}

export interface ShipSignUpListDto {
  ships: Array<number>;
}

export interface CrewDetailListDto {
  crews: Array<CrewDetailDto>;
}
