import { Injectable } from '@angular/core';
import { ModeloLivro } from '../model/modelolivro';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LivroService {



  constructor(
    private http: HttpClient
  ) { }

  url = 'http://localhost:8080/livro';

  getlivros(): Observable<ModeloLivro[]> {
    return this.http.get<ModeloLivro[]>(`${this.url}`);
  }

  getLivroId(id: number): Observable<ModeloLivro> {
    const url = `${this.url}/${id}`;
    return this.http.get<ModeloLivro>(url)
  }

  registerLivro(livro: ModeloLivro, file: File): Observable<ModeloLivro> {
    const formData = new FormData();
    formData.append('livro', new Blob([JSON.stringify(livro)], { type: 'application/json'  }));
    formData.append('imagem', file);
    return this.http.post<ModeloLivro>(this.url, formData);
  }

  updateLivro(livro: ModeloLivro, file: File): Observable<ModeloLivro> {
    const formData = new FormData();
    formData.append('livro', new Blob([JSON.stringify(livro)], { type: 'application/json'  }));
    formData.append('imagem', file);
    return this.http.put<ModeloLivro>(this.url, formData);
  }

  deleteLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

}
