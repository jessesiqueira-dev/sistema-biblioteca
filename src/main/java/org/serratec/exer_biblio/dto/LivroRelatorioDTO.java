package org.serratec.exer_biblio.dto;



	public interface LivroRelatorioDTO {
	    String getStatus();
	    Long getTotalLivros();
	    Integer getAnoMedio();
	    Integer getPaginasMedias();
	    String getLivroMaisAntigo();
	    String getLivroMaisNovo();
	}

