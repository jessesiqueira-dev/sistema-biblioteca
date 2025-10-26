package org.serratec.exer_biblio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<String> handleAutorNaoEncontrado(AutorNaoEncontradoException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(IsbnDuplicadoException.class)
	public ResponseEntity<String> handleIsbnDuplicado(IsbnDuplicadoException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<String> handleLivroNaoEncontrado(LivroNaoEncontradoException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	public ResponseEntity<String> handleLivroEmprestado(LivroEmprestadoException e)	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
