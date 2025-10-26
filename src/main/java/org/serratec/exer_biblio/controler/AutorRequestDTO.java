package org.serratec.exer_biblio.controler;

import java.time.LocalDate;

public class AutorRequestDTO {
    
    // Campos que o usuário deve enviar no JSON
    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;
    private String biografia;

    // Construtores, Getters e Setters
    // (O Spring/Jackson usa os Setters para preencher o objeto a partir do JSON)

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    // ... e assim por diante para todos os outros campos
	public String getBiografia() {
		// TODO Auto-generated method stub
		return biografia;
	}
	public LocalDate getDataNascimento() {
		// TODO Auto-generated method stub
		return dataNascimento;
	}
	public String getNacionalidade() {
		// TODO Auto-generated method stub
		return nacionalidade;
	}
}
