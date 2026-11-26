package br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaForm(
        @NotBlank(message = "Campo obrigatório.")
        String nome
) {
}