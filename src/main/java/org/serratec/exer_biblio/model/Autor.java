package org.serratec.exer_biblio.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Autor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String nacionalidade;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(length = 1000)
	private String biografia;
	
	@ManyToMany(mappedBy = "autores")
	private Set<Livro> livros = new HashSet<>();
	
	
	/**
	 * @param id
	 * @param nome
	 * @param nacionalidade
	 * @param dataNascimento
	 * @param biografia
	 * @param livros
	 */
	public Autor(Long id, String nome, String nacionalidade, LocalDate dataNascimento, String biografia,
			Set<Livro> livros) {
		super();
		this.id = id;
		this.nome = nome;
		this.nacionalidade = nacionalidade;
		this.dataNascimento = dataNascimento;
		this.biografia = biografia;
		this.livros = livros;
		
		
	}
	


	/**
	 * 
	 */
	public Autor() {

	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getNacionalidade() {
		return nacionalidade;
	}


	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}


	public LocalDate getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public String getBiografia() {
		return biografia;
	}


	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}


	public Set<Livro> getLivros() {
		return livros;
	}


	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
	}
