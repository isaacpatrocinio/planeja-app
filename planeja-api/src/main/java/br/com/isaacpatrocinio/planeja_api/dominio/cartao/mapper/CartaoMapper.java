package br.com.isaacpatrocinio.planeja_api.dominio.cartao.mapper;

import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoForm;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.CartaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartaoMapper {
    CartaoEntity toEntity(CartaoForm form);
    CartaoDetalhes toDetalhes(CartaoEntity entity);
    void updateEntity(CartaoForm form, @MappingTarget CartaoEntity entity);
}