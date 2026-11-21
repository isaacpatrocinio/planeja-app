package br.com.isaacpatrocinio.planeja_api.common.exceptions;

import br.com.isaacpatrocinio.planeja_api.common.validation.CampoInvalido;

import java.util.List;

public class ValidationException extends RuntimeException{

    private List<CampoInvalido> camposInvalidos;

    public ValidationException(List<CampoInvalido> camposInvalidos) {
        super("Erro de validação.");
        this.camposInvalidos = camposInvalidos;
    }

    public List<CampoInvalido> getCamposInvalidos() {
        return camposInvalidos;
    }
}
