import { Injectable } from '@angular/core';
import { LivroDto } from '../model/testeModelos';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  
  constructor(
    private http: HttpClient
  ) {}
  
  url = 'http://localhost:8080/livro';

  getlivros(): Observable<LivroDto[]> {
    return this.http.get<LivroDto[]>(`${this.url}`);
  }

  getLivroId(id: number): Observable<LivroDto> {
    const url = `${this.url}/${id}`;
    return this.http.get<LivroDto>(url)
  }

  registerLivro(livro: LivroDto, file: File): Observable<LivroDto> {
    const formData = new FormData();
    formData.append('livro', new Blob([JSON.stringify(livro)], { type: 'application/json' }));
    formData.append('imagem', file);
    return this.http.post<LivroDto>(this.url, formData);
  }

  updateLivro(livro: LivroDto, file: File): Observable<LivroDto> {
    const formData = new FormData();
    formData.append('livro', new Blob([JSON.stringify(livro)], { type: 'application/json' }));
    formData.append('imagem', file);
    return this.http.put<LivroDto>(this.url, formData);
  }

  deleteLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

}
