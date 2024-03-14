import { Component } from '@angular/core';
import { LivroService } from '../../service/livro.service';
import { LivroModelo } from '../../model/LivroModelo';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

livro = new LivroModelo;
livros: LivroModelo[]=[];
  constructor(  private livroServise: LivroService
    ){}

    ngOnInit(): void {
      this.listaLivros();
    }

    listaLivros(): void {
      this.livroServise.listaLivros().subscribe((data) => (this.livros = data));
    }

}
