package org.serratec.exer_biblio.controler;

import java.util.List;

import org.serratec.exer_biblio.dto.LivroDTO;
import org.serratec.exer_biblio.dto.LivroInserirDTO;
import org.serratec.exer_biblio.dto.LivroRelatorioDTO;
import org.serratec.exer_biblio.model.StatusLivro;
import org.serratec.exer_biblio.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
    
    @Autowired
    private LivroService livroService;
    
 // POST /livros - Cadastrar livro
    @PostMapping(produces = "application/json")
    public ResponseEntity<LivroDTO> inserir(@Valid @RequestBody LivroInserirDTO livroDTO) {
        LivroDTO novoLivro = livroService.inserir(livroDTO);
        return ResponseEntity.ok(novoLivro);
    }
    
    
    // GET /livros - Listar todos com paginação
    @GetMapping
    public ResponseEntity<?> listar(
        @PageableDefault(sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<LivroDTO> page = livroService.listar(pageable);
        return ResponseEntity.ok(page.getContent()); 
    }

    
    
    // GET /livros/autor/{id} - Buscar por autor
    @GetMapping("/autor/{id}")
    public ResponseEntity<Page<LivroDTO>> buscarPorAutor(
        @PathVariable Long id, Pageable pageable) {
        Page<LivroDTO> livros = livroService.buscarPorAutor(id, pageable);
        return ResponseEntity.ok(livros);
    }
    
    // GET /livros/status/{status} - Filtrar por status
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<LivroDTO>> buscarPorStatus(
        @PathVariable StatusLivro status, Pageable pageable) {
        Page<LivroDTO> livros = livroService.buscarPorStatus(status, pageable);
        return ResponseEntity.ok(livros);
    }
    
    // GET /livros/ano - Filtrar por ano de publicação
    @GetMapping("/ano")
    public ResponseEntity<Page<LivroDTO>> buscarPorAno(
        @RequestParam Integer anoInicio,
        @RequestParam Integer anoFim,
        Pageable pageable) {
        Page<LivroDTO> livros = livroService.buscarPorAno(anoInicio, anoFim, pageable);
        return ResponseEntity.ok(livros);
    }
    
    // GET /livros/disponiveis - Livros disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<LivroDTO>> buscarDisponiveis() {
        List<LivroDTO> livros = livroService.buscarDisponiveis();
        return ResponseEntity.ok(livros);
    }
    
    // GET /livros/antigos - Livros mais antigos
    @GetMapping("/antigos")
    public ResponseEntity<List<LivroDTO>> buscarMaisAntigos() {
        List<LivroDTO> livros = livroService.buscarMaisAntigos();
        return ResponseEntity.ok(livros);
    }
    
    // GET /livros/relatorio - Relatório por status
    @GetMapping("/relatorio")
    public ResponseEntity<List<LivroRelatorioDTO>> gerarRelatorio() {
        List<LivroRelatorioDTO> relatorio = livroService.gerarRelatorio();
        return ResponseEntity.ok(relatorio);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LivroInserirDTO livroDTO) {

        LivroDTO atualizado = livroService.atualizar(id, livroDTO);
        return ResponseEntity.ok(atualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
