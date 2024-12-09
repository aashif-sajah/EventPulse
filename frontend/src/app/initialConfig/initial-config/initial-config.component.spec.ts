import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialConfigComponent } from './initial-config.component';

describe('InitialConfigComponent', () => {
  let component: InitialConfigComponent;
  let fixture: ComponentFixture<InitialConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InitialConfigComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InitialConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
