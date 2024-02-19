import { EmprestimoModelo } from "./EmprestimoModelo";

export class ClienteModelo {
  clienteId: number = 0;
  nome: string = '';
  dataDeNascimento: Date = new Date();
  numeroDeTelefone: number = 0;
  email: string = '';
  emprestimos: EmprestimoModelo[] = [];
}
