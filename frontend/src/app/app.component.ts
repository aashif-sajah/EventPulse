import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormComponent } from './landing-page/form/form.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,FormComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
