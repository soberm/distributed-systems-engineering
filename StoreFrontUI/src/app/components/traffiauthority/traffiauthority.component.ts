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
  clearanceTimeAccidentSpot: number,
  isNew: boolean
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
  allAccidentStatisticsIDs: number[];

  constructor(private http: HttpClient) {
    this.alive = false;
    this.interval = 5000;
    this.doCenter = true;
    this.allAccidentStatisticsIDs = [];
  }

  ngOnInit() {
    this.startLive();
  }

  loadStatistics() {
    this.http.get<AccidentStatisticsResponse[]>(environment.GOVSTAT_SERVICE + 'accident-statistics').subscribe(data => {
      this.accidentStatistics = (data as AccidentStatisticsResponse[]).reverse();
      for(let i = 0; i < this.accidentStatistics.length; i++) {
        let statistic = this.accidentStatistics[i];
        // console.log("Length statistics " + this.allAccidentStatisticsIDs.length);
        if (this.allAccidentStatisticsIDs.indexOf(statistic.id) < 0) {
          // console.log("A new id " + statistic.id);
          statistic.isNew = true;
          this.allAccidentStatisticsIDs.push(statistic.id);
        } else {
          // console.log("Not a new id " + statistic.id);
          statistic.isNew = false;
        }
      }
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
