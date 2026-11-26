package br.com.isaacpatrocinio.planeja_api.dominio.cartao;

import br.com.isaacpatrocinio.planeja_api.common.validation.CampoInvalido;
import br.com.isaacpatrocinio.planeja_api.common.validation.ValidationResult;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoForm;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    private final CartaoRepository repository;

    public CartaoValidator(CartaoRepository repository) {
        this.repository = repository;
    }

    public ValidationResult validar(CartaoForm form, UUID id) {
        var result = ValidationResult.obter();
        boolean isListaNaoVazia = !repository.findByNomeAndNotId(form.nome(), id).isEmpty();
        if (isListaNaoVazia) {
            result.add(new CampoInvalido("nome", "Já cadastrado."));
        }
        return result;
    }
}