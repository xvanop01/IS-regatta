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
  SIGNED_UP = 'SIGNED_UP'
}

export const RaceUserStatus = {
  [RaceUserStatusEnum.APPLIED]: 'Applied',
  [RaceUserStatusEnum.SIGNED_UP]: 'Signed up'
}
