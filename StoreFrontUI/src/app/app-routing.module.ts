import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ManufacturerComponent} from "./components/manufacturer/manufacturer.component";
import {DatasimulatorComponent} from "./components/datasimulator/datasimulator.component";
import {GmapsComponent} from "./components/gmaps/gmaps.component";
import {AppComponent} from "./app.component";

const routes: Routes = [
  {path: 'manufacturer', component: ManufacturerComponent},
  // {path: 'emergency-service'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routingComponents = [ManufacturerComponent, DatasimulatorComponent, GmapsComponent];
