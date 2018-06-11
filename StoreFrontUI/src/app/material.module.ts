import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatListModule} from '@angular/material';

@NgModule({
  imports: [MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatListModule],
  exports: [MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatListModule],
})
export class MaterialModule { }
