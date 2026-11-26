package br.com.isaacpatrocinio.planeja_api.dominio.categoria;

import br.com.isaacpatrocinio.planeja_api.dominio.categoria.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
    boolean existsByNome(String nome);
}