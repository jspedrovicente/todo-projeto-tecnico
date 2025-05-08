import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tarefa } from '../models/tarefa.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TarefaService {
  private readonly apiUrl = 'http://localhost:8080/tarefas';


  constructor(private http: HttpClient) { }


  listar(nome: string = '', prioridade: string = '', situacao: string = '', pagina = 0, tamanho = 10, campoOrdem = 'nome', tipoOrdem = 'DESC'): Observable<any> {
    let params = new HttpParams()
      .set('nome', nome)
      .set('prioridade', prioridade)
      .set('situacao', situacao)
      .set('pagina', pagina)
      .set('tamanho', tamanho)
      .set('ordenarPor', campoOrdem)
      .set('direcao', tipoOrdem);

    return this.http.get(this.apiUrl, { params });
  }

  criar(tarefa: Tarefa): Observable<Tarefa> {
    return this.http.post<Tarefa>(this.apiUrl, tarefa);
  }

  editar(id: string, tarefa: Tarefa): Observable<Tarefa> {
    return this.http.put<Tarefa>(`${this.apiUrl}/${id}`, tarefa);
  }

  buscarPorId(id: string): Observable<Tarefa> {
    return this.http.get<Tarefa>(`${this.apiUrl}/${id}`);
  }

  concluir(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/concluir`, {});
  }

  pendente(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/pendente`, {});
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
