import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {MapVehicleInformation} from "./model/MapVehicleInformation";
import {TimerObservable} from "rxjs/observable/TimerObservable";
import 'rxjs/add/operator/takeWhile';
import {GmapsComponent} from "../gmaps/gmaps.component";

interface ManufacturerResponse {
  id: string
  name: string
}

interface VehicleResponse {
  vin: string,
  aliasInMap: string,
  modelType: string
  passengers: number
  location: number[]
  speed: number,
  distanceVehicleAhead: number,
  distanceVehicleBehind: number,
  nearCrashEvent: boolean,
  crashEvent: boolean
}

interface NotificationResponse {
  id: number,
  vin: string,
  aliasInMap: string,
  modelType: string
  passengers: number
  location: number[]
  speed: number,
  distanceVehicleAhead: number,
  distanceVehicleBehind: number,
  type: string,
  date: string
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
  mapVehicleInformations: Map<string, MapVehicleInformation>;
  private alive: boolean;
  private interval: number;
  doCenter: boolean;
  crashNotifications: NotificationResponse[];
  nearCrashNotifications: NotificationResponse[];

  @ViewChild("gmapsComponent") gmapsComponent: GmapsComponent;

  constructor(private http: HttpClient) {
    this.mapVehicleInformations = new Map();
    this.alive = false;
    this.interval = 2000;
    // this.markers = [];
    this.doCenter = true;
  }

  ngOnInit() {
    this.loadManufacturers();
    this.startLive();
  }

  isSelected() {
    return this.manufacturersSelect != null;
  }

  loadManufacturers() {
    this.http.get<ManufacturerResponse[]>(this.manufacturerURL + 'getAll').subscribe(data => {
      this.manufacturers = data as ManufacturerResponse[];
      if (this.manufacturers != null && this.manufacturers.length > 0) {
        console.log("got something");
        this.manufacturersSelect = this.manufacturers[0].name
      }
    }, error => {
      console.error(error);
      return [];
    });
  }

  showVehicleInformation() {
    if (this.manufacturers != null) {
      console.log(this.manufacturersSelect);
      let id: string = this.manufacturers.find(manufacturer => manufacturer.name == this.manufacturersSelect).id;
      this.http.get<VehicleResponse[]>(this.v2itrackerURL + 'live-vehicle-track', {
        params: new HttpParams().set('manufacturer', id)
      }).subscribe(data => {
        this.vehicles = data as VehicleResponse[];
        if(this.vehicles == null) {
          return;
        }
        for (let i = 0; i < this.vehicles.length; i++) {
          let vehicle = this.vehicles[i];
          let vehicleInformation;
          let existingVehicle = this.mapVehicleInformations.get(vehicle.vin);
          if (existingVehicle != null) {
            vehicleInformation = new MapVehicleInformation(existingVehicle.vehicleAlias, vehicle.location[1], vehicle.location[0]);
            vehicle.aliasInMap = existingVehicle.vehicleAlias.toString();
          } else {
            vehicleInformation = new MapVehicleInformation(i, vehicle.location[1], vehicle.location[0]);
            vehicle.aliasInMap = i.toString();
          }
          this.mapVehicleInformations.set(vehicle.vin, vehicleInformation);
          console.log("vehicle " + vehicle.vin + " is at \n" + vehicleInformation.latitude + "/" + vehicleInformation.longitude);
        }
      }, error => {
        console.error(error);
        return [];
      });
    }
  }

  showNotifications() {
    if (this.manufacturers != null) {
      console.log(this.manufacturersSelect);
      let id: string = this.manufacturers.find(manufacturer => manufacturer.name == this.manufacturersSelect).id;
      this.http.get<NotificationResponse[]>(environment.NOTYFIER_SERVICE + 'notification', {
        params: new HttpParams().set('receiver', id).append('top', 'true')
      }).subscribe(data => {
        let notifications: NotificationResponse[];
        notifications = data as NotificationResponse[];
        if(notifications != null && notifications.length > 0) {
          this.crashNotifications = [];
          this.nearCrashNotifications = [];
          for (let i = 0; i < notifications.length; i++) {
            let notification = notifications[i];
            let existingVehicle = this.mapVehicleInformations.get(notification.vin);
            if(existingVehicle == null) {
              return;
            }
            notification.aliasInMap = existingVehicle.vehicleAlias.toString();
            console.log("notification: " + notification.id);
            if (notification.type == "CrashEventNotification") {
              this.crashNotifications.push(notification);
            } else if(notification.type == "NearCrashEventNotification") {
              this.nearCrashNotifications.push(notification);
            }
          }
        }
      }, error => {
        console.error(error);
        return [];
      });
    }
  }

  onChange(newValue) {
    this.mapVehicleInformations.clear();
    this.doCenter = true;
  }

  ngOnDestroy() {
    this.alive = false;
  }

  startLive() {
    console.log("Started live");
    if(this.alive == true) {
      return;
    }
    this.alive = true;
    TimerObservable.create(2000, this.interval)
      .takeWhile(() => this.alive)
      .subscribe(() => {
        this.showVehicleInformation();
        this.showNotifications();
        this.gmapsComponent.refresh();
      });
  }

  stopLive() {
    console.log("Stopped live");
    this.alive = false;
  }
}
