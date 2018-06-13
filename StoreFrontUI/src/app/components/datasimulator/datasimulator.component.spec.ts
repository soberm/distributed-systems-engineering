import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatasimulatorComponent } from './datasimulator.component';

describe('DatasimulatorComponent', () => {
  let component: DatasimulatorComponent;
  let fixture: ComponentFixture<DatasimulatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatasimulatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatasimulatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
