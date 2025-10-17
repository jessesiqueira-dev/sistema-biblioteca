package com.biblioteca.sistema_biblioteca.controller;

import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.service.LivroService;
import jakarta.validation.Valid; // Importante para validar o corpo da requisição
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Anotação necessária para que esta classe funcione como um controlador REST 
@RequestMapping("/livros") // Endpoint raiz solicitado
public class LivroController {

    @Autowired
    private LivroService livroService;

    // GET localhost:8080/livros
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        // Retorna a lista de livros com status 200 OK
        return ResponseEntity.ok(livroService.listarTodos());
    }

    // GET localhost:8080/livros/<id>
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        // Se existir, retorna 200 OK, senão 404 Not Found
        return livroService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST localhost:8080/livros
    // @Valid: dispara as validações (NotBlank, Size, NotNull) na entidade Livro
    @PostMapping
    public ResponseEntity<?> criarLivro(@Valid @RequestBody Livro livro) {
        // Retorna o livro criado com status 201 CREATED
        Livro novoLivro = livroService.salvar(livro);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    // PUT localhost:8080/livros/<id>
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        // O método update retorna Optional. Se estiver presente, retorna o livro atualizado (200 OK)
        return livroService.atualizarLivro(id, livro)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build()); // Senão, retorna 404 Not Found
    }

    // DELETE localhost:8080/livros/<id>
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long id) {
        // Se o serviço confirmar a deleção, retorna 204 NO_CONTENT, senão 404 NOT_FOUND
        if (livroService.deletarLivro(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}