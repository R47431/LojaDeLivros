export class LivroModelo {
  livroId: number =  0;
  imagemDoLivro: string = '';
  imagem: string = '';
  titulo: string = '';
  nomeDoAutor: string = '';
  nacionalidade: string = '';
  data: Date = new Date();
  editora: string = '';
  genero: string = '';
  sinopse: string = '';

  constructor() {
    // You can leave the constructor empty if you're only initializing properties above
  }
}