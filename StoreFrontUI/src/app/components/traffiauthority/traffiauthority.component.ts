import { Component, OnInit } from '@angular/core';
import {MapVehicleInformation} from "../manufacturer/model/MapVehicleInformation";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {TimerObservable} from "rxjs/observable/TimerObservable";

interface AccidentStatisticsResponse {
  id: number,
  vin: string,
  aliasInMap: string,
  modelType: string
  passengers: number
  arrivalTimeEmergencyService: number,
  clearanceTimeAccidentSpot: number
}

@Component({
  selector: 'app-traffiauthority',
  templateUrl: './traffiauthority.component.html',
  styleUrls: ['./traffiauthority.component.scss']
})
export class TraffiauthorityComponent implements OnInit {
  accidentStatistics: AccidentStatisticsResponse[];
  private alive: boolean;
  private interval: number;
  doCenter: boolean;

  constructor(private http: HttpClient) {
    this.alive = false;
    this.interval = 5000;
    this.doCenter = true;
  }

  ngOnInit() {
    this.startLive();
  }

  loadStatistics() {
    this.http.get<AccidentStatisticsResponse[]>(environment.GOVSTAT_SERVICE + 'accident-statistics').subscribe(data => {
      this.accidentStatistics = data as AccidentStatisticsResponse[];
    }, error => {
      console.error(error);
      return [];
    });
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
    TimerObservable.create(0, this.interval)
      .takeWhile(() => this.alive)
      .subscribe(() => {
        this.loadStatistics();
      });
  }

  stopLive() {
    console.log("Stopped live");
    this.alive = false;
  }
}
