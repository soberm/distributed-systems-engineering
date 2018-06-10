import { Component, OnInit,ViewChild } from '@angular/core';
import { } from '@types/googlemaps';
import MarkerOptions = google.maps.MarkerOptions;

@Component({
  selector: 'app-gmaps',
  templateUrl: './gmaps.component.html',
  styleUrls: ['./gmaps.component.scss']
})
export class GmapsComponent implements OnInit {
  @ViewChild('gmap') gmapElement: any;
  map: google.maps.Map;

  latitude:number;
  longitude:number;

  ngOnInit() {
    var mapProp = {
      center: new google.maps.LatLng(18.5793, 73.8143),
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(this.gmapElement.nativeElement, mapProp);
  }

  setMapType(mapTypeId: string) {
    this.map.setMapTypeId(mapTypeId)
  }

  setCenter(e:any){
    e.preventDefault();
    this.map.setCenter(new google.maps.LatLng(this.latitude, this.longitude));
    new google.maps.Marker({
      position: new google.maps.LatLng(48.210033, 16.363449),
      map: this.map,
      label: "A"
    });
    new google.maps.Marker({
      position: new google.maps.LatLng(48.220033, 16.373449),
      map: this.map,
      label: "B"
    });
  }

}


