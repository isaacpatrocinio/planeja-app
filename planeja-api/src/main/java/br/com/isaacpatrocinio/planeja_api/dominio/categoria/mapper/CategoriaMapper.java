package br.com.isaacpatrocinio.planeja_api.dominio.categoria.mapper;

import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaForm;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.model.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDetalhes toDtoDetalhes(CategoriaEntity entity);

    CategoriaEntity toEntity(CategoriaForm dtoForm);

    void updateEntity(CategoriaForm dtoForm, @MappingTarget CategoriaEntity entity);
}