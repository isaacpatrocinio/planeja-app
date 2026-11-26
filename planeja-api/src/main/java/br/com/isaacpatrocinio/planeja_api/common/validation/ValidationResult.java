package br.com.isaacpatrocinio.planeja_api.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private List<CampoInvalido> camposInvalidos;

    private ValidationResult(List<CampoInvalido> campoInvalidos) {
        this.camposInvalidos = campoInvalidos;
    }

    public static ValidationResult obter() {
        return new ValidationResult(new ArrayList<>());
    }

    public void add(CampoInvalido campoInvalido) {
        this.camposInvalidos.add(campoInvalido);
    }

    public boolean isInvalido() {
        return !camposInvalidos.isEmpty();
    }

    public List<CampoInvalido> getCamposInvalidos() {
        return camposInvalidos;
    }
}