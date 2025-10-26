package org.serratec.exer_biblio.controler;

import org.serratec.exer_biblio.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService autorService;

//1. CREATE (POST) - /autores
	@PostMapping
	public ResponseEntity<AutorResponseDTO> inserir(@Valid @RequestBody AutorRequestDTO autorDTO) {
		AutorResponseDTO novoAutor = autorService.inserir(autorDTO);
		// Retorna 201 Created
		return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
	}

// 2. READ (GET) - /autores/{id}
	@GetMapping("/{id}")
	public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id) {
		AutorResponseDTO autor = autorService.buscarPorId(id);
		// Retorna 200 OK
		return ResponseEntity.ok(autor);
	}

// 3. UPDATE (PUT) - /autores/{id}
	@PutMapping("/{id}")
	public ResponseEntity<AutorResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody AutorRequestDTO autorDTO) {
		AutorResponseDTO autorAtualizado = autorService.atualizar(id, autorDTO);
		// Retorna 200 OK
		return ResponseEntity.ok(autorAtualizado);
	}

// 4. DELETE (DELETE) - /autores/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		autorService.deletar(id);

		return ResponseEntity.noContent().build();
	}
}