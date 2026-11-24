package br.com.isaacpatrocinio.planeja_api.dominio.cartao;

import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.cartao.dto.CartaoForm;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@CrossOrigin("*")
public class CartaoController {
    private final CartaoService service;

    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartaoDetalhes> criar(
            @RequestBody @Valid CartaoForm dtoForm) {
        var dtoDetalhes = service.criar(dtoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoDetalhes);
    }

//    @GetMapping
//    public ResponseEntity<List<CartaoDetalhes>> obterTodos() {
//        var list = service.obterTodos();
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }

    @GetMapping
    public Page<CartaoDetalhes> obterTodosComPaginacao(
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "10")
            int size)
    {
        return service
                .obterTodosComPaginacao(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoDetalhes> obterDetalhes(@PathVariable UUID id) {
        var obj = service.obterDetalhes(id);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody CartaoForm objAtualizado) {
        service.atualizar(id, objAtualizado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatus(@PathVariable(name = "id") UUID id) {
        service.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }
}

