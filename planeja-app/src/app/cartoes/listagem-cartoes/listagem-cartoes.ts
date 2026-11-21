import { Component, inject, OnInit } from '@angular/core';
import { CartaoService } from '../cartao-service';
import { PageResult } from '../../common/pagination/page-result';
import { Observable } from 'rxjs';
import { DetalhesCartao } from '../dados-cartao';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listagem-cartoes',
  imports: [CommonModule],
  templateUrl: './listagem-cartoes.html',
  styleUrl: './listagem-cartoes.scss',
})
export class ListagemCartoes
  implements OnInit {

    service = inject(CartaoService)
  listagem$!: Observable<PageResult<DetalhesCartao>>;
  paginaAtual = 0;
  totalPaginas = 10;

  ngOnInit(): void {
    this.listarCartoes()
  }

  listarCartoes() {
    this.listagem$ = this.service
      .listar(this.paginaAtual, this.totalPaginas)
  }

  navegar(pagina: number) {
    this.paginaAtual = pagina
    this.listarCartoes()
  }

  navegarProximo(listagem: PageResult<DetalhesCartao>) {
    if(!listagem.last) {
      this.navegar(listagem.number + 1)
    }
  }

  navegarAnterior(listagem: PageResult<DetalhesCartao>) {
    if(!listagem.first) {
      this.navegar(listagem.number - 1)
    }
  }

  paginas(totalPages : number) : number[] {
    return Array.from({length: totalPages}, (value, index) => index)
  }

  registroInicial(listagem : PageResult<DetalhesCartao>) {
    if(listagem.totalElements === 0) return 0

    return (listagem.number * listagem.size) + 1
  }

  registroFinal(listagem : PageResult<DetalhesCartao>) {
    if(listagem.totalElements === 0) return 0

    return (Math.min((listagem.number + 1 * listagem.size), listagem.totalElements))
  }
}