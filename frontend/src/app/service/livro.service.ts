import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LivroModelo } from '../model/LivroModelo';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  private url: string = "http://localhost:8080/biblioteca";

  constructor(private http: HttpClient) { }


  listaLivros(): Observable<LivroModelo[]>{
    return this.http.get<LivroModelo[]>(this.url+"/lista");
  }


}
