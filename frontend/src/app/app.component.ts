import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ClienteComponent } from './pages/cliente/cliente.component';
import { LivroComponent } from './pages/livro/livro.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [RouterOutlet, ClienteComponent, LivroComponent]
})
export class AppComponent {
  title = 'frontend';
}
