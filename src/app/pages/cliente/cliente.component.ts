import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ClienteService } from '../../service/cliente.service';
import { ClienteDTO } from '../../model/testeModelos';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent {
  clienteForm!: FormGroup;
  clientes: ClienteDTO[] =[];


  constructor(private formBuilder: FormBuilder, private clienteService: ClienteService) { 

    this.clienteForm = this.formBuilder.group({
      nome: [''],
      dataDeNascimento: [''],
      numeroDeTelefone: [''],
      email: ['']
    });
  }

  ngOnInit(): void {
    this.listCliente();

  }

  listCliente(): void {
    this.clienteService.getcliente()
    .subscribe((data: ClienteDTO[]) => this.clientes =  data)
  }

  resgisteCliente(): void {
    this.clienteService.registerCliente(this.clienteForm.value)
    .subscribe(data => this.clientes.push(data))
  }

  deletaCliente(id:number):void {
    this.clienteService.deleteCliente(id)
    .subscribe()
  }

}
