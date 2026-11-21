package br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto;

import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.BandeiraCartao;

import java.time.LocalDateTime;

public record CartaoDetalhes(String id, String nome, BandeiraCartao bandeira, LocalDateTime dataCadastro) {
}