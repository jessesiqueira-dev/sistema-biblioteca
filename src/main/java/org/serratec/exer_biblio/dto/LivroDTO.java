package org.serratec.exer_biblio.dto;

import java.time.LocalDateTime;

import org.serratec.exer_biblio.model.Livro;
import org.serratec.exer_biblio.model.StatusLivro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class LivroDTO {

    private Long id;
    private String titulo;
    private String isbn;
    private Integer numeroPaginas;
    private Integer anoPublicacao;
    private StatusLivro status;
    private LocalDateTime dataCadastro;
    private String statusDescricao;
    private Integer idadeLivro;

    // ✅ Construtor que converte o model em DTO
    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.isbn = livro.getIsbn();
        this.numeroPaginas = livro.getNumeroPaginas();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.status = livro.getStatus();
        this.dataCadastro = livro.getDataCadastro();
        this.statusDescricao = obterDescricaoStatus(livro.getStatus());
        this.idadeLivro = calcularIdadeLivro(livro.getAnoPublicacao());
    }

    private Integer calcularIdadeLivro(Integer anoPublicacao) {
        return LocalDateTime.now().getYear() - anoPublicacao;
    }

    private String obterDescricaoStatus(StatusLivro status) {
        return switch (status) {
            case DISPONIVEL -> "Disponível para empréstimo";
            case EMPRESTADO -> "Atualmente emprestado";
            case RESERVADO -> "Reservado por usuário";
            case MANUTENCAO -> "Em manutenção";
        };
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatusDescricao() {
		return statusDescricao;
	}

	public void setStatusDescricao(String statusDescricao) {
		this.statusDescricao = statusDescricao;
	}

	public Integer getIdadeLivro() {
		return idadeLivro;
	}

	public void setIdadeLivro(Integer idadeLivro) {
		this.idadeLivro = idadeLivro;
	}
}
