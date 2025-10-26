package org.serratec.exer_biblio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.exer_biblio.dto.LivroDTO;
import org.serratec.exer_biblio.dto.LivroInserirDTO;
import org.serratec.exer_biblio.dto.LivroRelatorioDTO;
import org.serratec.exer_biblio.exception.AutorNaoEncontradoException;
import org.serratec.exer_biblio.exception.IsbnDuplicadoException;
import org.serratec.exer_biblio.model.Autor;
import org.serratec.exer_biblio.model.Livro;
import org.serratec.exer_biblio.model.StatusLivro;
import org.serratec.exer_biblio.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
@Transactional
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AutorService autorService;
    
    // Inserir livro
    public LivroDTO inserir(@Valid LivroInserirDTO livroDTO) {
        // Validar ISBN único
        if (livroRepository.existsByIsbn(livroDTO.getIsbn())) {
            throw new IsbnDuplicadoException("ISBN já cadastrado: " + livroDTO.getIsbn());
        }
        
        // Buscar autores
        Set<Autor> autores = livroDTO.getAutorIds().stream()
            .map(autorService::buscar) // lança AutorNaoEncontradoException se não existir
            .collect(Collectors.toSet());

        // Criar livro
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setNumeroPaginas(livroDTO.getNumeroPaginas());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setStatus(livroDTO.getStatus());
        livro.setDataCadastro(LocalDateTime.now());
        livro.setAutores(autores);
        
        livro = livroRepository.save(livro);
        return new LivroDTO(livro);
    }
    
    // Listar todos os livros com paginação
    public Page<LivroDTO> listar(Pageable pageable) {
        return livroRepository.findAll(pageable)
            .map(LivroDTO::new);
    }

    // Buscar por autor
    public Page<LivroDTO> buscarPorAutor(Long autorId, Pageable pageable) {
        return livroRepository.findByAutores_Id(autorId, pageable)
            .map(LivroDTO::new);
    }

    // Buscar por status
    public Page<LivroDTO> buscarPorStatus(StatusLivro status, Pageable pageable) {
        return livroRepository.findByStatus(status, pageable)
            .map(LivroDTO::new);
    }

    // Buscar por faixa de ano
    public Page<LivroDTO> buscarPorAno(Integer anoInicio, Integer anoFim, Pageable pageable) {
        return livroRepository.findByAnoPublicacaoBetween(anoInicio, anoFim, pageable)
            .map(LivroDTO::new);
    }

    // Buscar apenas disponíveis
    public List<LivroDTO> buscarDisponiveis() {
        return livroRepository.findByStatus(StatusLivro.DISPONIVEL)
            .stream()
            .map(LivroDTO::new)
            .collect(Collectors.toList());
    }

    // Buscar livros mais antigos (top 5)
    public List<LivroDTO> buscarMaisAntigos() {
        return livroRepository.findTop5ByOrderByAnoPublicacaoAsc()
            .stream()
            .map(LivroDTO::new)
            .collect(Collectors.toList());
    }

    // Gerar relatório por status (nativo → converte para DTO)
    public List<LivroRelatorioDTO> gerarRelatorio() {
        return livroRepository.gerarRelatorioPorStatus();
    }

    public LivroDTO atualizar(Long id, @Valid LivroInserirDTO livroDTO) {
        // Verifica se o livro existe
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));

        // Atualiza os campos
        livroExistente.setTitulo(livroDTO.getTitulo());
        livroExistente.setIsbn(livroDTO.getIsbn());
        livroExistente.setNumeroPaginas(livroDTO.getNumeroPaginas());
        livroExistente.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livroExistente.setStatus(livroDTO.getStatus());

        // Atualiza autores (busca os autores pelos IDs)
        Set<Autor> autores = livroDTO.getAutorIds().stream()
                .map(autorService::buscar)
                .collect(Collectors.toSet());
        livroExistente.setAutores(autores);

        // Salva e retorna atualizado
        livroRepository.save(livroExistente);
        return new LivroDTO(livroExistente);
    }

    public void deletar(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));

        livroRepository.delete(livro);
    }

	}

	


