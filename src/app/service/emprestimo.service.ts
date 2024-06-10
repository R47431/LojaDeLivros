import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  constructor(private http: HttpClient) { }
 
  url = "http://localhost:8080/emprestimo";


  fazerEmprestimo(clienteId: number, livroId: number): Observable<void>{
    return this.http.post<void>(`${this.url}/${clienteId}/${livroId}`, {});

  }

  devolverEmprestimo(clienteId: number, livroId: number, emprestimoId: number): Observable<void>{
    return this.http.put<void>(`${this.url}/${clienteId}/${livroId}/${emprestimoId}`, {});

  }

  deletaEmprestimo(emprestimoId: number): Observable<void>{
    return this.http.delete<void>(`${this.url}/${emprestimoId}`);

  }
}
