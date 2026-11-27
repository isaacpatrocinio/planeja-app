import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from '@iqx-limited/ngx-toastr';
import { Observable } from 'rxjs';
import { Header } from '../../common/components/header/header';
import { PageResult } from '../../common/pagination/page-result';
import { CategoriaService } from '../categoria-service';
import { DetalhesCategoria } from '../dados-categoria';

@Component({
  selector: 'app-listagem-categorias',
  imports: [CommonModule, RouterLink, Header],
  templateUrl: './listagem-categorias.html',
  styleUrl: './listagem-categorias.scss',
})
export class ListagemCategorias
  implements OnInit {

  service = inject(CategoriaService)
  router = inject(Router)
  toast = inject(ToastrService)
  listagem$!: Observable<PageResult<DetalhesCategoria>>;
  paginaAtual = 0;
  totalPaginas = 10;

  ngOnInit(): void {
    this.listarCategorias()
  }

  listarCategorias() {
    this.listagem$ = this.service
      .listar(this.paginaAtual, this.totalPaginas)
  }

  navegar(pagina: number) {
    this.paginaAtual = pagina
    this.listarCategorias()
  }

  navegarProximo(listagem: PageResult<DetalhesCategoria>) {
    if (!listagem.last) {
      this.navegar(listagem.number + 1)
    }
  }

  navegarAnterior(listagem: PageResult<DetalhesCategoria>) {
    if (!listagem.first) {
      this.navegar(listagem.number - 1)
    }
  }

  paginas(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (value, index) => index)
  }

  registroInicial(listagem: PageResult<DetalhesCategoria>) {
    if (listagem.totalElements === 0) return 0

    return (listagem.number * listagem.size) + 1
  }

  registroFinal(listagem: PageResult<DetalhesCategoria>) {
    if (listagem.totalElements === 0) return 0

    return (Math.min((listagem.number + 1) * listagem.size, listagem.totalElements))
  }
  
  mudarStatus(idCartao: string) : void {
    this.service.mudarStatus(idCartao)
      .subscribe({
        next: () => {
          this.toast.success("Registro atualizado com sucesso.")
          this.listarCategorias()
        }
      })
  }
}