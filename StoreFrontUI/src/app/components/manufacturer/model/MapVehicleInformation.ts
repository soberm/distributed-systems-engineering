export class MapVehicleInformation {
  vehicleAlias: number;
  longitude: number;
  latitude: number;

  constructor(vehicleAlias, longitude, latitude) {
    this.vehicleAlias = vehicleAlias;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
