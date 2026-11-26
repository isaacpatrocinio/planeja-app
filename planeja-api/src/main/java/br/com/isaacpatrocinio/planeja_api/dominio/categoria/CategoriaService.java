package br.com.isaacpatrocinio.planeja_api.dominio.categoria;

import br.com.isaacpatrocinio.planeja_api.common.exceptions.RegistroNaoEncontradoException;
import br.com.isaacpatrocinio.planeja_api.common.exceptions.ValidationException;
import br.com.isaacpatrocinio.planeja_api.common.validation.ValidationResult;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaForm;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.mapper.CategoriaMapper;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.model.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final CategoriaValidator validator;

    public CategoriaService(CategoriaRepository repository, CategoriaMapper mapper, CategoriaValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public CategoriaDetalhes criar(CategoriaForm dtoForm) {
        ValidationResult result = validator.validar(dtoForm, null);
        if (result.isInvalido()) {
            throw new ValidationException(result.getCamposInvalidos());
        }

        CategoriaEntity entity = mapper.toEntity(dtoForm);
        repository.save(entity);
        return mapper.toDtoDetalhes(entity);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDetalhes> listarTodos() {
        var listCategoria = repository.findAll();
        return listCategoria
                .stream()
                .map(mapper::toDtoDetalhes)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<CategoriaDetalhes> listarTodosPaginados(PageRequest request) {
        var listCategoria = repository.findAll(request);
        return listCategoria.map(mapper::toDtoDetalhes);
    }

    @Transactional(readOnly = true)
    public CategoriaDetalhes obterPorId(UUID id) {
        var objCategoria = repository.findById(id);
        var dtoDetalhes = objCategoria.map(mapper::toDtoDetalhes);
        return dtoDetalhes.orElseThrow(RegistroNaoEncontradoException::new);
    }

    @Transactional
    public void atualizar(CategoriaForm dtoForm, UUID id) {
        var objCategoria = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        mapper.updateEntity(dtoForm, objCategoria);
        repository.save(objCategoria);
    }

    @Transactional
    public void deletar(UUID id) {
        var objCategoria = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        repository.delete(objCategoria);
    }

    @Transactional
    public void mudarStatus(UUID id) {
        var objCategoria = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        objCategoria.setAtivo(!objCategoria.getAtivo());
        repository.save(objCategoria);
    }
}
