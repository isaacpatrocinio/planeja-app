import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CartaoService } from '../cartao-service';
import { DadosCartaoForm, DetalhesCartao } from '../dados-cartao';
import { ValidationResponse } from '../../common/validation/validation-error-model'
import { CommonModule } from '@angular/common';
import { ToastrService } from '@iqx-limited/ngx-toastr';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

interface CadastroCartaoForm {
  nome: FormControl<string>;
  bandeira: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-cartao',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './cadastro-cartao.html',
  styleUrl: './cadastro-cartao.scss',
})

export class CadastroCartao
  implements OnInit {

  form!: FormGroup<CadastroCartaoForm>;
  service = inject(CartaoService);
  toast = inject(ToastrService);
  rotaAtiva = inject(ActivatedRoute)
  router = inject(Router)
  idCartaoEdicao?: string | null;

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCartaoForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required }),
      bandeira: new FormControl('', { nonNullable: true, validators: Validators.required })
    })

    this.carregarDadosParaEdicao()
  }

  carregarDadosParaEdicao() {
    this.idCartaoEdicao = this.rotaAtiva.snapshot.queryParamMap.get('id')
    if (!this.idCartaoEdicao) {
      return;
    }

    this.service
      .obterPorId(this.idCartaoEdicao)
      .subscribe({
        next: (cartao: DetalhesCartao) => {
          this.form.patchValue({
            nome: cartao.nome,
            bandeira: cartao.bandeira
          })
        },
        error: () => console.log("Erro ao carregar os dados do cartão.")
      })
  }

  isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.toast.error('Erro de validação. Verifique os campos informados.')
      this.form.markAllAsTouched()
      return true
    }
    return false
  }

  handleSubmit() {
    if (this.isFormInvalid()) return

    const dadosCartao = this.form.value as DadosCartaoForm;
    const requisicao: Observable<DetalhesCartao | void> = (this.idCartaoEdicao)
      ? this.service.atualizar(this.idCartaoEdicao, dadosCartao)
      : this.service.criar(dadosCartao)

    requisicao
      .subscribe({
        next: (response : DetalhesCartao | void) => {
          this.toast.success('Cartão cadastrado/atualizado com sucesso.')
          this.form.reset()
          this.idCartaoEdicao = null
        },
        error: (error) => {
          this.onApiError(error)
          console.log(error)
        }
      })
  }

  private aplicarErroValidacaoApi(response: ValidationResponse): void {
    response
      .camposInvalidos
      .forEach(c => {
        const control = this.form.get(c.campo)
        if (control) {
          control.setErrors(
            { apiError: c.erro }
          )
          control.markAsTouched()
        }
      })
  }

  private onApiError(response: any): void {
    if (response.status === 422) {
      this.aplicarErroValidacaoApi(response.error)
      this.toast.error('Erro de validação. Verifique os campos informados.')
      return
    }
    this.toast.error('Ocorreu um erro a processar a requisição.')
  }
}