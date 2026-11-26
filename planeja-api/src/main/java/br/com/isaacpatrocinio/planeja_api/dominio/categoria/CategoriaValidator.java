package br.com.isaacpatrocinio.planeja_api.dominio.categoria;

import br.com.isaacpatrocinio.planeja_api.common.validation.CampoInvalido;
import br.com.isaacpatrocinio.planeja_api.common.validation.ValidationResult;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaForm;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.model.CategoriaEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoriaValidator {

    private final CategoriaRepository repository;

    public CategoriaValidator(CategoriaRepository repository) {
        this.repository = repository;
    }

    public ValidationResult validar(CategoriaForm dtoForm, UUID id) {
        ValidationResult result = ValidationResult.obter();
        boolean existeCategoriaRegistrada = repository.existsByNome(dtoForm.nome());
        if (existeCategoriaRegistrada) {
            result.add(new CampoInvalido("nome", "Já existe uma categoria cadastrada com esse nome."));
        }
        return result;
    }
}