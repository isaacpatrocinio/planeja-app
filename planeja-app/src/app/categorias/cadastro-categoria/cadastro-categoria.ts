import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ɵInternalFormsSharedModule, ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { ToastrService } from '@iqx-limited/ngx-toastr';
import { Header } from '../../common/components/header/header';
import { DadosCategoriaForm, DetalhesCategoria } from '../dados-categoria';
import { CategoriaService } from '../categoria-service';
import { ValidationResponse, CampoInvalido } from '../../common/validation/validation-error-model';

interface CadastroCategoriaForm {
  nome: FormControl<string>;
}

@Component({
  selector: 'app-cadastro-categoria',
  imports: [RouterModule, ɵInternalFormsSharedModule, ReactiveFormsModule, Header],
  templateUrl: './cadastro-categoria.html',
  styleUrl: './cadastro-categoria.scss',
})
export class CadastroCategoria {
  form!: FormGroup<CadastroCategoriaForm>;
  service = inject(CategoriaService)
  toast = inject(ToastrService)

  ngOnInit(): void {
    this.form = new FormGroup<CadastroCategoriaForm>({
      nome: new FormControl('', { nonNullable: true, validators: Validators.required })
    })
  }

  isFormInvalid(): boolean {
    if (this.form.invalid) {
      this.form.markAllAsTouched()
      return true
    }
    return false
  }

  handleSubmit(): void {
    if (this.isFormInvalid()) return

    const dadosCategoria = this.form.value as DadosCategoriaForm
    const requisicao: Observable<DetalhesCategoria> = this.service.criar(dadosCategoria)

    requisicao.subscribe({
      next: () => {
        this.toast.success('Categoria cadastrada com sucesso.')
        this.form.reset()
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