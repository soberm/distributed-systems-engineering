import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ManufacturerComponent} from "./components/manufacturer/manufacturer.component";
import {DatasimulatorComponent} from "./components/datasimulator/datasimulator.component";
import {GmapsComponent} from "./components/gmaps/gmaps.component";
import {EmergencyComponent} from "./components/emergency/emergency.component";

const routes: Routes = [
  {path: 'manufacturer', component: ManufacturerComponent},
  {path: 'emergency', component:EmergencyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routingComponents = [ManufacturerComponent, DatasimulatorComponent, GmapsComponent, EmergencyComponent];
