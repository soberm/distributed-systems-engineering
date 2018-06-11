import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {filter} from "rxjs/operators";

interface ManufacturerResponse {
  id: string
  name: string
}

interface VehicleResponse {
  vin: string
  modelType: string
}

@Component({
  selector: 'app-manufacturer',
  templateUrl: './manufacturer.component.html',
  styleUrls: ['./manufacturer.component.scss'],
  providers: []
})
export class ManufacturerComponent implements OnInit {

  manufacturerURL: string = environment.VEHICLE_DATA_SERVICE + '/manufacturer/';
  manufacturers: ManufacturerResponse[];
  manufacturersSelect:string;
  vehicles: VehicleResponse[];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get<ManufacturerResponse[]>(this.manufacturerURL + 'getAll').subscribe(data => {
      this.manufacturers = data as ManufacturerResponse[];
      if(this.manufacturers.length > 0) {
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
    let vin:string = this.manufacturers.find(manufacturer => manufacturer.name == this.manufacturersSelect).id;
    this.http.get<VehicleResponse[]>(this.manufacturerURL + vin + '/vehicle').subscribe(data => {
      this.vehicles = data as VehicleResponse[];
    }, error => {
      console.error(error);
      return [];
    });
  }
}
