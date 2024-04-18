export interface ShipDetailDto {
  id: number;
  name: string;
  registration: string;
  ownerName: string;
}

export interface CreateUpdateShipDto {
  name: string;
  registration: string;
}
