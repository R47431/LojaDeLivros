import { Component } from '@angular/core';
import { LivroDto } from '../../model/testeModelos';
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
  formLivro!: FormGroup;
  fileToUpload: File | null = null;
  livros: LivroDto[] = [];

  constructor(private formBuilder: FormBuilder, private livroService: LivroService) {

    this.formLivro = this.formBuilder.group({
      livroId: [''],
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


  mostraLivros() {
    this.livroService.getlivros()
      .subscribe((data: LivroDto[]) => {
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

  deletarLivro(livro_id: number): void {
    this.livroService.deleteLivro(livro_id)
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
    this.formLivro.get('imagemDoLivro')?.setValue(arquivoSelecionado.name); // Definindo o valor aqui
  }

}
