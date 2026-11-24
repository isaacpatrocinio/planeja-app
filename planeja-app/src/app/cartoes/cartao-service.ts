import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DadosCartaoForm, DetalhesCartao } from './dados-cartao';
import { Observable } from 'rxjs';
import { PageResult } from '../common/pagination/page-result';

@Injectable({
  providedIn: 'root',
})

export class CartaoService {
  http = inject(HttpClient);

  baseUrl = "http://localhost:8080/cartoes"

  criar(dados: DadosCartaoForm): Observable<DetalhesCartao> {
    return this.http.post<DetalhesCartao>(this.baseUrl, dados)
  }

  listar(page: number = 0, size: number = 10)
    : Observable<PageResult<DetalhesCartao>> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`
    return this.http.get<PageResult<DetalhesCartao>>(url)  
  }

  obterPorId(id: string) : Observable<DetalhesCartao>{
    const url = `${this.baseUrl}/${id}`
    return this.http.get<DetalhesCartao>(url)
  }

  atualizar(id: string, dadosAtualizados: DadosCartaoForm) : Observable<void> {
    const url = `${this.baseUrl}/${id}`
    return this.http.put<void>(url, dadosAtualizados)
  }

  mudarStatus(id: string) : Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/status`, null)
  }
}