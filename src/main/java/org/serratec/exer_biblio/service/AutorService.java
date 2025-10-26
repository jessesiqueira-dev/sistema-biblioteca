
package org.serratec.exer_biblio.service;

import org.serratec.exer_biblio.controler.AutorRequestDTO;
import org.serratec.exer_biblio.controler.AutorResponseDTO;
import org.serratec.exer_biblio.exception.AutorNaoEncontradoException;
import org.serratec.exer_biblio.model.Autor;
import org.serratec.exer_biblio.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autorRepository;

	public AutorResponseDTO inserir(AutorRequestDTO autorDTO) {
	    Autor autor = new Autor();

	    autor.setNome(autorDTO.getNome());
	    autor.setNacionalidade(autorDTO.getNacionalidade());
	    autor.setDataNascimento(autorDTO.getDataNascimento());
	    autor.setBiografia(autorDTO.getBiografia());

	    autor = autorRepository.save(autor);

	    AutorResponseDTO responseDTO = new AutorResponseDTO();
	    responseDTO.setId(autor.getId());
	    responseDTO.setNome(autor.getNome());
	    responseDTO.setNacionalidade(autor.getNacionalidade());
	    responseDTO.setDataNascimento(autor.getDataNascimento());
	    responseDTO.setBiografia(autor.getBiografia());

	    return responseDTO;
	}

	

	public Autor buscar(Long id) {
		return autorRepository.findById(id)
				.orElseThrow(() -> new AutorNaoEncontradoException("Autor não encontrado com ID: " + id));
	}
	public AutorResponseDTO atualizar(Long id, AutorRequestDTO autorDTO) throws AutorNaoEncontradoException {
        Autor autor = buscar(id); // Verifica se existe
        
        // Mapeia os dados do DTO para a entidade existente, mantendo o ID
        autor = toEntity(autorDTO, autor);
        
        autor = autorRepository.save(autor);
        return toResponseDTO(autor);
}

	// DELETE (D)
    public void deletar(Long id) throws AutorNaoEncontradoException {
        // O método "buscar" verifica se o autor existe antes de tentar deletar
        // Se a busca falhar, lança a exceção.
        buscar(id); 
        autorRepository.deleteById(id);
    }

    // --- MAPPERS INTERNOS (Para converter entre DTO e Entity) ---

    // Converte Entity para ResponseDTO
    private AutorResponseDTO toResponseDTO(Autor autor) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        dto.setNacionalidade(autor.getNacionalidade());
        dto.setDataNascimento(autor.getDataNascimento());
        dto.setBiografia(autor.getBiografia());
        return dto;
    }

    // Converte RequestDTO para Entity (útil para Inserir e Atualizar)
    private Autor toEntity(AutorRequestDTO dto, Autor autor) {
        autor.setNome(dto.getNome());
        autor.setNacionalidade(dto.getNacionalidade());
        autor.setDataNascimento(dto.getDataNascimento());
        autor.setBiografia(dto.getBiografia());
        return autor;
    }

    public AutorResponseDTO buscarPorId(Long id) {
        Autor autor = buscar(id); // reutiliza o método de busca
        return toResponseDTO(autor);
	}
}
	
	
	
	
	
	
