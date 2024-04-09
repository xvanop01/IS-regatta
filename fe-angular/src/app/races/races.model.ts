export interface CreateRaceDto {
  name: string;
  location: string;
  date: string;
  signUpUntil: string;
  description: string;
  isPublic: boolean;
}

export interface UpdateRaceDto {
  name: string;
  location: string;
  date: string;
  signUpUntil: string;
  description: string;
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
}

export interface RaceDetailListDto {
  races: Array<RaceDetailDto>;
}

export interface UpdateRaceDatesDto {
  date: string;
  signUpUntil: string;
}
