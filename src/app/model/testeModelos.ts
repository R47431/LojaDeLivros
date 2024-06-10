// cliente.model.ts
export interface ClienteComEmprestimosDto {
  clienteId: number;
  nome: string;
  email: string;
  emprestemos: EmprestimoComLivroDto[];
}

// cliente-dto.model.ts
export interface ClienteDTO {
  nome: string;
  dataDeNascimento: Date;
  numeroDeTelefone: string;
  email: string;
}

// emprestimo.model.ts
export interface EmprestimoComLivroDto {
  emprestimoId: number;
  dataEmprestimo: Date;
  dataDevolucao: Date;
  livro: LivroDto;
}

// livro.model.ts
export interface LivroDto {
  livroId: number;
  imagemDoLivro: string;
  titulo: string;
  nomeDoAutor: string;
  nacionalidade: string;
  data: Date;
  editora: string;
  genero: string;
  sinopse: string;
}

//emprestimo.model.ts
export interface Emoprestimo{
  emprestimo: number;
  dataEmprestimo: Date;
  dataDevolucao: Date;
  cliente: ClienteDTO;
  livro: LivroDto;


}


