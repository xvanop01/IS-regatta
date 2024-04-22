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

export enum RegistrationStatusEnum {
  APPLIED = 'APPLIED',
  REGISTERED = 'REGISTERED'
}

export const RegistrationStatus = {
  [RegistrationStatusEnum.APPLIED]: 'applied',
  [RegistrationStatusEnum.REGISTERED]: 'registered'
}

export interface CrewDetailDto {
  id: number;
  raceId: number;
  shipName: string;
  shipRegistration: string;
  shipOwnerId: number;
  shipOwnerName: string;
  raceName: string;
  status: RegistrationStatusEnum;
}

export interface ShipSignUpListDto {
  ships: Array<number>;
}

export interface CrewDetailListDto {
  crews: Array<CrewDetailDto>;
}

export interface UserRaceInfoDto {
  crewId: number;
  raceId: number;
  status: RegistrationStatusEnum;
}
