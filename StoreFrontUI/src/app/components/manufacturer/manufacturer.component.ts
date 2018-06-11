import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";


interface ManufacturerResponse {
  information: string
}

@Component({
  selector: 'app-manufacturer',
  templateUrl: './manufacturer.component.html',
  styleUrls: ['./manufacturer.component.scss']
})
export class ManufacturerComponent implements OnInit {

  manufacturers : any[] = ['Porsche', 'Mercedes', 'VW', 'BMW'];
  manufacturersSelect = 0;
  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get<ManufacturerResponse>(environment.API_URL + '/' + environment.VEHICLE_DATA_SERVICE + '/information').subscribe(data =>
      {
        console.log(data.information)
      },
      (err:HttpErrorResponse) => {
        if(err.error instanceof Error) {
          console.log("Client-side Error occured")
        }
        console.log("Server-side Error occured")
      }
    )
  }
}
