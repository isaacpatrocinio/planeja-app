package br.com.isaacpatrocinio.planeja_api.dominio.cartao;

import br.com.isaacpatrocinio.planeja_api.common.exceptions.RegistroNaoEncontradoException;
import br.com.isaacpatrocinio.planeja_api.common.exceptions.ValidationException;
import br.com.isaacpatrocinio.planeja_api.common.validation.CampoInvalido;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoForm;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.mapper.CartaoMapper;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.model.CartaoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartaoService {
    private final CartaoValidator validator;
    private final CartaoRepository repository;
    private final CartaoMapper mapper;

    public CartaoService(CartaoValidator validator, CartaoRepository repository, CartaoMapper mapper) {
        this.validator = validator;
        this.repository = repository;
        this.mapper = mapper;
    }

    public CartaoDetalhes criar(CartaoForm form) {
        var result = validator.validar(form, null);
        if (result.isInvalido()) {
            throw new ValidationException(result.getCamposInvalidos());
        }
        CartaoEntity entity = mapper.toEntity(form);
        repository.save(entity);
        return mapper.toDetalhes(entity);
    }

    public CartaoDetalhes obterDetalhes(UUID id) {
        var obj = repository.findById(id);
        var dto = obj.map(mapper::toDetalhes);
        return dto.orElseThrow(RegistroNaoEncontradoException::new);
    }

    public List<CartaoDetalhes> obterTodos() {
        var entityList = repository.findAll();
        return entityList
                .stream()
                .map(mapper::toDetalhes)
                .toList();
    }

    public Page<CartaoDetalhes> obterTodosComPaginacao(
            PageRequest pageRequest
    ) {
        return repository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }

    @Transactional
    public void atualizar(UUID id, CartaoForm objAtualizado) {
        var obj = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        var result = validator.validar(objAtualizado, id);
        if (result.isInvalido()) {
            throw new ValidationException(result.getCamposInvalidos());
        }

        mapper.updateEntity(objAtualizado, obj);
    }

    public void deletar(UUID id) {
        var obj = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        repository.delete(obj);
    }

    @Transactional
    public void mudarStatus(UUID id) {
        var obj = repository
                .findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        obj.setAtivo(!obj.getAtivo());
    }
}