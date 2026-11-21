import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CartaoService } from '../cartao-service';
import { DadosCartaoForm, DetalhesCartao } from '../dados-cartao';
import { ValidationResponse } from '../../common/validation/validation-error-model'
import { CommonModule } from '@angular/common';
import { ToastrService } from '@iqx-limited/ngx-toastr';

interface CadastroCartaoForm {
  nome: FormControl<string>;
  bandeira: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-cartao',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cadastro-cartao.html',
  styleUrl: './cadastro-cartao.scss',
})

export class CadastroCartao
  implements OnInit {

  form!: FormGroup<CadastroCartaoForm>;
  service = inject(CartaoService);
  toast = inject(ToastrService);

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCartaoForm>({
      nome: new FormControl(
        '', {
        nonNullable: true,
        validators: Validators.required
      }),
      bandeira: new FormControl(
        '', {
        nonNullable: true,
        validators: Validators.required
      })
    })
  }

  isFormInvalid() : boolean {
    if(this.form.invalid) {
      this.toast.error('Erro de validação. Verifique os campos informados.')
      this.form.markAllAsTouched()
      return true
    }
    return false
  }

  handleSubmit() {
    if(this.isFormInvalid()) return
    const dadosCartao = this.form.value as DadosCartaoForm;
    this.service
      .criar(dadosCartao)
      .subscribe({
        next: (response: DetalhesCartao) => {
          this.toast.success('Cartão cadastrado/atualizado com sucesso.')
          console.log(response)
        },
        error: (error) => {
          this.onApiError(error)
          console.log(error)
        }
      })
  }

  private aplicarErroValidacaoApi(response : ValidationResponse) : void {
    response
      .camposInvalidos
      .forEach(c => {
        const control = this.form.get(c.campo)
        if(control) {
          control.setErrors(
            {apiError: c.erro}
          )
          control.markAsTouched()
        }
      })
  }

  private onApiError(response : any) : void {
    if(response.status === 422) {
      this.aplicarErroValidacaoApi(response.error)
      this.toast.error('Erro de validação. Verifique os campos informados.')
      return
    }
    this.toast.error('Ocorreu um erro a processar a requisição.')
  }
}