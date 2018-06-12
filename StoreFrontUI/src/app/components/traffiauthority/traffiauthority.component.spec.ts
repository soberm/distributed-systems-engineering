import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TraffiauthorityComponent } from './traffiauthority.component';

describe('TraffiauthorityComponent', () => {
  let component: TraffiauthorityComponent;
  let fixture: ComponentFixture<TraffiauthorityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TraffiauthorityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TraffiauthorityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
