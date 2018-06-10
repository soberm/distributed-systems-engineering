import { Component, OnInit } from '@angular/core';

import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from '../environments/environment';

interface SomeResponse {
  information: string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'app';

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get<SomeResponse>(environment.API_URL + '/notyfier/information').subscribe(data =>
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

