package br.com.isaacpatrocinio.planeja_api.dominio.categoria;

import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaDetalhes;
import br.com.isaacpatrocinio.planeja_api.dominio.categoria.dto.CategoriaForm;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoriaDetalhes> criar(@Valid @RequestBody CategoriaForm dtoForm) {
        CategoriaDetalhes obj = service.criar(dtoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

//    @GetMapping
//    public ResponseEntity<List<CategoriaDetalhes>> listarTodos() {
//        List<CategoriaDetalhes> list = service.listarTodos();
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }

    @GetMapping
    public Page<CategoriaDetalhes> listarTodosPaginados(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return service.listarTodosPaginados(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDetalhes> obterPorId(@PathVariable(name = "id") UUID id) {
        CategoriaDetalhes obj = service.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaForm dtoForm, @PathVariable(name = "id") UUID id) {
        service.atualizar(dtoForm, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "id") UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatus(@PathVariable(name = "id") UUID id) {
        service.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }
}