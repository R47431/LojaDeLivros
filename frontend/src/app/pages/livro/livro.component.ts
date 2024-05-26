import { Component } from '@angular/core';
import { ModeloLivro } from '../../model/modelolivro';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { LivroService } from '../../service/livro.service';

@Component({
  selector: 'app-livro',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './livro.component.html',
  styleUrl: './livro.component.css'
})
export class LivroComponent {
  formLivro: FormGroup;
  fileToUpload: File | null = null;
  livros: ModeloLivro[] = [];
  livro: ModeloLivro = new ModeloLivro();


  constructor(private formBuilder: FormBuilder, private livroService: LivroService) {
    this.formLivro = this.formBuilder.group({
      livro_id: [''],
      imagemDoLivro: [''],
      titulo: [''],
      nomeDoAutor: [''],
      nacionalidade: [''],
      data: [''],
      editora: [''],
      genero: [''],
      sinopse: ['']
    });
  }

  ngOnInit(): void {
    this.mostraLivros();

  }

  mostraLivros() {
    this.livroService.getlivros()
      .subscribe((data: ModeloLivro[]) => {
        this.livros = data;

      });
  }

  cadastrarLivro(): void {
    if (this.fileToUpload) {
      this.livroService.registerLivro(this.formLivro.value, this.fileToUpload)
        .subscribe({
          next: (data) => {
            alert('Livro cadastrado com sucesso!');
          },
          error: (error) => {
            alert('Erro ao cadastrar o livro!');
            console.error(error);
          }
        });
    } else {
      alert('Por favor, anexe uma imagem do livro.');
    }
  }

  updatelivro(): void {
    if (this.fileToUpload) {
      this.livroService.updateLivro(this.formLivro.value, this.fileToUpload)
        .subscribe({
          next: (data) => {
            alert('Livro alterado com sucesso!');
          },
          error: (error) => {
            alert('Erro ao alterado o livro!');
            console.error(error);
          }
        });
    } else {
      alert('Por favor, anexe uma imagem do livro.');
    }
  }

  deletarLivro(): void {
    this.livroService.deleteLivro(this.livro.livro_id)
      .subscribe({
        next: value => {
          
          alert('Livro deletado com sucess');
        },
        error(err) {
          alert('Erro ao deleta o livro!');
          console.error(err);
        }
      });
  }


  selecionarArquivo(event: any): void {
    const arquivoSelecionado = event.target.files[0];
    this.fileToUpload = arquivoSelecionado;
    this.livro.imagemDoLivro = arquivoSelecionado.name;
  }
}
