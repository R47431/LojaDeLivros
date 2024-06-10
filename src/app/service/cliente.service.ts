import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClienteDTO } from '../model/testeModelos';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) {
  }

  url = 'http://localhost:8080/clientes';

  getcliente(): Observable<ClienteDTO[]> {
    return this.http.get<ClienteDTO[]>(`${this.url}/lista`);
  }

  registerCliente(cliente: ClienteDTO): Observable<ClienteDTO> {
    return this.http.post<ClienteDTO>(`${this.url}`, cliente);
  }


  deleteCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
