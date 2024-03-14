import { ClienteModelo } from "./ClienteModelo";
import { LivroModelo } from "./LivroModelo";

export class EmprestimoModelo {
  emprestimoId: number = 0;
  cliente: ClienteModelo = new ClienteModelo;
  livro: LivroModelo = new LivroModelo;
  dataEmprestimo: Date = new Date();
  dataDevolucao: Date = new Date();
}