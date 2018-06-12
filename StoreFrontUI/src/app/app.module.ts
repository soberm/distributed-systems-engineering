import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { GmapsComponent } from './components/gmaps/gmaps.component';
import { FormsModule } from '@angular/forms';
import { ManufacturerComponent } from './components/manufacturer/manufacturer.component';
import { DatasimulatorComponent } from './components/datasimulator/datasimulator.component';



@NgModule({
  declarations: [
    AppComponent,
    GmapsComponent,
    ManufacturerComponent,
    DatasimulatorComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
