import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Inventories } from './inventories';

describe('Inventories', () => {
  let component: Inventories;
  let fixture: ComponentFixture<Inventories>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Inventories]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Inventories);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
