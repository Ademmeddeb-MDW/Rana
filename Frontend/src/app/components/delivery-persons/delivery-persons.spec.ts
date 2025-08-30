import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryPersons } from './delivery-persons';

describe('DeliveryPersons', () => {
  let component: DeliveryPersons;
  let fixture: ComponentFixture<DeliveryPersons>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeliveryPersons]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeliveryPersons);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
