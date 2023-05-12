export interface CreateRaceDto {
  name: string;
}

export interface UpdateRaceDto {
  name: string;
  location: string;
  date: Date;
  signUpUntil: Date;
  description: string;
}

export interface RaceDetailDto {
  id: number;
  name: string;
  location: string;
  date: Date;
  signUpUntil: Date;
  description: string;
  isPublic: boolean;
}

export interface RaceDetailListDto {
  races: Array<RaceDetailDto>;
}

export interface UpdateRaceDatesDto {
  date: Date;
  signUpUntil: Date;
}