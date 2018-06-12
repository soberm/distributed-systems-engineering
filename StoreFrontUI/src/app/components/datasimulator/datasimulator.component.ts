import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-datasimulator',
  templateUrl: './datasimulator.component.html',
  styleUrls: ['./datasimulator.component.scss']
})
export class DatasimulatorComponent implements OnInit {

  @Output() startLiveEvent = new EventEmitter();
  @Output() stopLiveEvent = new EventEmitter();

  callParent() {

  }

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  stopSimulator() {
    this.http.get(environment.DATA_SIMULATOR_SERVICE + 'simulator/stop').subscribe(data => {
      console.log("Stopped Simulator")
      this.stopLiveEvent.next();
    }, error => {
      console.error("Simulator stop failed: \n" + error);
      return [];
    });
  }

  start(id: string) {
    this.http.get(environment.DATA_SIMULATOR_SERVICE + 'simulator/start?id=' + id).subscribe(data => {
      console.log("Started Simulator Scenario " + id);
      this.startLiveEvent.next();
    }, error => {
      console.error("Cannot start Simulator Scenario: " + id + "\n" + error);
      return [];
    });
  }
}
