import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import {MapVehicleInformation} from "../manufacturer/model/MapVehicleInformation";
import {TimerObservable} from "rxjs/observable/TimerObservable";
import {GmapsComponent} from "../gmaps/gmaps.component";

interface EmergencyServiceResponse {
  id: string
  name: string
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
  selector: 'app-emergency',
  templateUrl: './emergency.component.html',
  styleUrls: ['./emergency.component.scss']
})
export class EmergencyComponent implements OnInit {

  emergencyServices: EmergencyServiceResponse[];
  emergencyServiceSelect: string;
  crashNotifications: NotificationResponse[];
  nearCrashNotifications: NotificationResponse[];
  mapVehicleInformations: Map<string, MapVehicleInformation>;
  private alive: boolean;
  private interval: number;
  doCenter: boolean;

  @ViewChild("gmapsComponent") gmapsComponent: GmapsComponent;

  constructor(private http: HttpClient) {
    this.mapVehicleInformations = new Map();
    this.alive = false;
    this.interval = 2000;
    // this.markers = [];
    this.doCenter = true;
  }

  ngOnInit() {
    this.loadEmergencyService();
    this.startLive();
  }

  loadEmergencyService() {
    this.http.get<EmergencyServiceResponse[]>(environment.VEHICLE_DATA_SERVICE + 'emergencyService').subscribe(data => {
      this.emergencyServices = data as EmergencyServiceResponse[];
      if (this.emergencyServices != null && this.emergencyServices.length > 0) {
        // console.log("got something");
        this.emergencyServiceSelect = this.emergencyServices[0].name
      }
    }, error => {
      console.error(error);
      return [];
    });
  }

  showNotifications() {
    if (this.emergencyServices != null) {
      // console.log(this.emergencyServiceSelect);
      let id: string = this.emergencyServices.find(emergencyService => emergencyService.name == this.emergencyServiceSelect).id;
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
            let vehicleInformation;
            let existingVehicle = this.mapVehicleInformations.get(notification.vin);
            if (existingVehicle != null) {
              vehicleInformation = new MapVehicleInformation(existingVehicle.vehicleAlias, notification.location[1], notification.location[0]);
              notification.aliasInMap = existingVehicle.vehicleAlias.toString();
            } else {
              vehicleInformation = new MapVehicleInformation(i, notification.location[1], notification.location[0]);
              notification.aliasInMap = i.toString();
            }
            // console.log("notification: " + notification.id);
            if (notification.type == "CrashEventNotification") {
              this.crashNotifications.push(notification);
              this.mapVehicleInformations.set(notification.vin + i, vehicleInformation);
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
        this.showNotifications();
        this.gmapsComponent.refresh();
      });
  }

  stopLive() {
    console.log("Stopped live");
    this.alive = false;
  }
}
