import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {MapVehicleInformation} from "./model/MapVehicleInformation";

interface ManufacturerResponse {
  id: string
  name: string
}

interface VehicleResponse {
  vin: string
  modelType: string
  passengers: number
  location: number[]
  speed: number,
  distanceVehicleAhead: number,
  distanceVehicleBehind: number,
  nearCrashEvent: boolean,
  crashEvent: boolean
}

@Component({
  selector: 'app-manufacturer',
  templateUrl: './manufacturer.component.html',
  styleUrls: ['./manufacturer.component.scss'],
  providers: []
})
export class ManufacturerComponent implements OnInit {

  manufacturerURL: string = environment.VEHICLE_DATA_SERVICE + '/manufacturer/';
  v2itrackerURL: string = environment.V2I_TRACKER_SERVICE;
  manufacturers: ManufacturerResponse[];
  manufacturersSelect: string;
  vehicles: VehicleResponse[];
  mapVehicleInformations : Map<string, MapVehicleInformation>;

  constructor(private http: HttpClient) {
    this.mapVehicleInformations = new Map();
  }

  ngOnInit() {
    this.http.get<ManufacturerResponse[]>(this.manufacturerURL + 'getAll').subscribe(data => {
      this.manufacturers = data as ManufacturerResponse[];
      if (this.manufacturers.length > 0) {
        console.log("got something");
        this.manufacturersSelect = this.manufacturers[0].name
      }
    }, error => {
      console.error(error);
      return [];
    });
  }

  isSelected() {
    return this.manufacturersSelect != null;
  }

  showVehicleInformation() {
    console.log(this.manufacturersSelect);
    let vin: string = this.manufacturers.find(manufacturer => manufacturer.name == this.manufacturersSelect).id;
    this.http.get<VehicleResponse[]>(this.v2itrackerURL + 'live-vehicle-track').subscribe(data => {
      this.vehicles = data as VehicleResponse[];
      for (let i = 0; i < this.vehicles.length; i++) {
        let vehicle = this.vehicles[i];
        let vehicleInformation = new MapVehicleInformation(i, vehicle.location[1], vehicle.location[0]);
        this.mapVehicleInformations.set(vehicle.vin, vehicleInformation);
      }
    }, error => {
      console.error(error);
      return [];
    });
  }
}
