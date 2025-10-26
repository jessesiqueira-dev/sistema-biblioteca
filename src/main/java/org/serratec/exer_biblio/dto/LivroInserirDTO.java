package org.serratec.exer_biblio.dto;

import java.util.Set;

import org.serratec.exer_biblio.model.StatusLivro;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LivroInserirDTO {

	@NotBlank(message = "Título é obrigatório")
	@Size(min = 2, max = 200, message = "Título deve ter entre 2 e 200 caracteres")
	private String titulo;
	
	@NotBlank(message = "ISBN é obrigatório")
	private String isbn;
	
	@Min(value = 1, message = "número de páginas deve ser maior que zero")
	private Integer numeroPaginas;
	
	@Min(value = 1000, message = "ano deve ser maior que 1000")
	@Max(value = 2025, message = "ano não pode ser maior que o atual")
	private Integer anoPublicacao;
	
	@NotNull
	private StatusLivro status;
	
	
	@NotEmpty(message = " é obrigatório a inserção do autor")
	private Set<Long> autorIds;


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}


	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}


	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}


	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}


	public StatusLivro getStatus() {
		return status;
	}


	public void setStatus(StatusLivro status) {
		this.status = status;
	}


	public Set<Long> getAutorIds() {
		return autorIds;
	}


	public void setAutorIds(Set<Long> autorIds) {
		this.autorIds = autorIds;
	}
	
	
}


