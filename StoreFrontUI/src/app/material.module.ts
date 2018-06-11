import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule } from '@angular/material';

@NgModule({
  imports: [MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule],
  exports: [MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule],
})
export class MaterialModule { }
