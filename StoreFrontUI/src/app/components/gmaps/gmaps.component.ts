import {Component, Input, OnInit, Output, ViewChild} from '@angular/core';
import { } from '@types/googlemaps';
import MarkerOptions = google.maps.MarkerOptions;
import {EventEmitter} from "selenium-webdriver";
import {MapVehicleInformation} from "../manufacturer/model/MapVehicleInformation";

@Component({
  selector: 'app-gmaps',
  templateUrl: './gmaps.component.html',
  styleUrls: ['./gmaps.component.scss']
})
export class GmapsComponent implements OnInit {
  @ViewChild('gmap') gmapElement: any;
  map: google.maps.Map;

  @Input() mapVehicleInformations: Map<string, MapVehicleInformation>;
  latitude:number;
  longitude:number;
  // @Output() messageEvent = new EventEmitter<string>();

    ngOnInit() {
    let mapProp = {
      center: new google.maps.LatLng(18.5793, 73.8143),
      zoom: 12,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(this.gmapElement.nativeElement, mapProp);
  }

  setMapType(mapTypeId: string) {
    this.map.setMapTypeId(mapTypeId)
  }

  refresh(e:any){
    e.preventDefault();
    let vehicleInformation: MapVehicleInformation;
    for (let key of Array.from(this.mapVehicleInformations.keys())) {
      vehicleInformation = this.mapVehicleInformations.get(key);
      new google.maps.Marker({
        position: new google.maps.LatLng(vehicleInformation.latitude, vehicleInformation.longitude),
        map: this.map,
        label: vehicleInformation.vehicleAlias.toString()
      });
    }
    this.map.setCenter(new google.maps.LatLng(vehicleInformation.latitude, vehicleInformation .longitude));
  }

}


