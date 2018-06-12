import {Component, Input, OnInit, ViewChild} from '@angular/core';
import { } from '@types/googlemaps';
import {MapVehicleInformation} from "../manufacturer/model/MapVehicleInformation";
import Marker = google.maps.Marker;

@Component({
  selector: 'app-gmaps',
  templateUrl: './gmaps.component.html',
  styleUrls: ['./gmaps.component.scss']
})
export class GmapsComponent implements OnInit {
  @ViewChild('gmap') gmapElement: any;
  map: google.maps.Map;
  private markers: Marker[];

  @Input() mapVehicleInformations: Map<string, MapVehicleInformation>;
  @Input() doCenter: boolean;

  constructor() {
    this.markers = [];
  }
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

  refresh() {
    console.log(this.mapVehicleInformations.size);
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

  function1(){
    console.log("Method in maps");
  }
}


