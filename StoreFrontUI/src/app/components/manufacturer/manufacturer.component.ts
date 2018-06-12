import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {MapVehicleInformation} from "./model/MapVehicleInformation";
import {TimerObservable} from "rxjs/observable/TimerObservable";
import 'rxjs/add/operator/takeWhile';
import {GmapsComponent} from "../gmaps/gmaps.component";
import Marker = google.maps.Marker;

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
  private markers: Marker[];
  doCenter: boolean;


  constructor(private http: HttpClient) {
    this.mapVehicleInformations = new Map();
    this.alive = true;
    this.interval = 1000;
    this.markers = [];
    this.doCenter = true;
  }

  ngOnInit() {
    this.loadManufacturers();

    let mapProp = {
      center: new google.maps.LatLng(18.5793, 73.8143),
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(this.gmapElement.nativeElement, mapProp);

    TimerObservable.create(2000, this.interval)
      .takeWhile(() => this.alive)
      .subscribe(() => {
        this.showVehicleInformation();
        this.refresh();
      });
  }

  isSelected() {
    return this.manufacturersSelect != null;
  }

  loadManufacturers() {
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

  showVehicleInformation() {
    if (this.manufacturers != null) {
      console.log(this.manufacturersSelect);
      let id: string = this.manufacturers.find(manufacturer => manufacturer.name == this.manufacturersSelect).id;
      this.http.get<VehicleResponse[]>(this.v2itrackerURL + 'live-vehicle-track', {
        params: new HttpParams().set('manufacturer', id)
      }).subscribe(data => {
        this.vehicles = data as VehicleResponse[];
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
          // this.refresh();
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


  @ViewChild('gmap') gmapElement: any;
  map: google.maps.Map;

  // @Input() mapVehicleInformations: Map<string, MapVehicleInformation>;
  latitude: number;
  longitude: number;

  // @Output() messageEvent = new EventEmitter<string>();

  setMapType(mapTypeId: string) {
    this.map.setMapTypeId(mapTypeId)
  }

  refresh() {
    let vehicleInformation: MapVehicleInformation;
    for (let i = 0; i < this.markers.length; i++) {
      this.markers[i].setMap(null);
    }
    for (let key of Array.from(this.mapVehicleInformations.keys())) {
      vehicleInformation = this.mapVehicleInformations.get(key);
      console.log("Information in map " + vehicleInformation.vehicleAlias + ": "
        + vehicleInformation.latitude + "/" + vehicleInformation.longitude);
      let marker = new google.maps.Marker({
        position: new google.maps.LatLng(vehicleInformation.latitude, vehicleInformation.longitude),
        map: this.map,
        label: vehicleInformation.vehicleAlias.toString()
      });
      this.markers.push(marker);
    }
    if (vehicleInformation != null && this.doCenter) {
      this.map.setCenter(new google.maps.LatLng(vehicleInformation.latitude, vehicleInformation.longitude));
      this.doCenter = false;
    }
  }
}
