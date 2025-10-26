
package org.serratec.exer_biblio.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.exer_biblio.dto.LivroRelatorioDTO;
import org.serratec.exer_biblio.model.Livro;
import org.serratec.exer_biblio.model.StatusLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /* -------------------------
       Query Methods (Spring Data)
       ------------------------- */

    // Buscar por título (contendo, case-insensitive) com paginação
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Buscar por ISBN (único)
    Optional<Livro> findByIsbn(String isbn);

    // Buscar por faixa de ano de publicação com paginação
    Page<Livro> findByAnoPublicacaoBetween(Integer anoInicio, Integer anoFim, Pageable pageable);

    // Buscar por status (com paginação)
    Page<Livro> findByStatus(StatusLivro status, Pageable pageable);

    // Buscar por status (lista simples)
    List<Livro> findByStatus(StatusLivro status);

    // Buscar por autor (passando id do autor) com paginação
    Page<Livro> findByAutores_Id(Long autorId, Pageable pageable);

    // Buscar por nome do autor (contendo, case-insensitive)
    List<Livro> findByAutores_NomeContainingIgnoreCase(String nomeAutor);

    // Contar livros por status
    Long countByStatus(StatusLivro status);

    // Verificar existência de ISBN
    boolean existsByIsbn(String isbn);

    // Top 5 mais antigos / mais recentes (por ano de publicação)
    List<Livro> findTop5ByOrderByAnoPublicacaoAsc();
    List<Livro> findTop5ByOrderByAnoPublicacaoDesc();

    /* -------------------------
       JPQL (consultas customizadas)
       ------------------------- */

    /**
     * Buscar livros que possuam *todos* os autores passados em autorIds.
     * Uso: passar a lista de ids e, como segundo parâmetro, autorIds.size() (convertido para Long).
     */
    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.id IN :autorIds GROUP BY l.id HAVING COUNT(DISTINCT a.id) = :qtd")
    List<Livro> buscarPorAutores(@Param("autorIds") List<Long> autorIds, @Param("qtd") Long qtd);

    // Buscar livros com mais de X páginas
    @Query("SELECT l FROM Livro l WHERE l.numeroPaginas > :paginas")
    List<Livro> buscarLivrosExtensos(@Param("paginas") Integer paginas);

    // Buscar livros publicados dentro de uma década (por exemplo 1990 a 1999)
    @Query("SELECT l FROM Livro l WHERE l.anoPublicacao BETWEEN :decadaInicio AND :decadaFim")
    List<Livro> buscarPorDecada(@Param("decadaInicio") Integer inicio, @Param("decadaFim") Integer fim);

    // Buscar por título contendo ou por ISBN exato (paginado)
    @Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR l.isbn = :termo")
    Page<Livro> buscarPorTituloOuIsbn(@Param("termo") String termo, Pageable pageable);

    // Buscar livros de autores de uma nacionalidade específica
    @Query("SELECT DISTINCT l FROM Livro l JOIN l.autores a WHERE a.nacionalidade = :nacionalidade")
    List<Livro> buscarPorNacionalidadeAutor(@Param("nacionalidade") String nacionalidade);

    /* -------------------------
       Queries Nativas (SQL) - para relatórios / agregações
       ------------------------- */

    @Query(value = """
    	    SELECT l.status AS status,
    	           COUNT(*) AS totalLivros,
    	           AVG(l.ano_publicacao) AS anoMedio,
    	           AVG(l.numero_paginas) AS paginasMedias,
    	           MIN(l.titulo) AS livroMaisAntigo,
    	           MAX(l.titulo) AS livroMaisNovo
    	    FROM livro l
    	    GROUP BY l.status
    	    """, nativeQuery = true)
    	List<LivroRelatorioDTO> gerarRelatorioPorStatus();

    // Top 5 livros mais antigos (ajuste nomes de colunas/tabela conforme seu BD)
    @Query(value = "SELECT * FROM livro ORDER BY ano_publicacao ASC LIMIT 5", nativeQuery = true)
    List<Livro> buscarTop5MaisAntigosNative();

    // Livros com mais autores (ordenado desc pelo número de autores)
    @Query(value = "SELECT l.* FROM livro l JOIN livro_autores la ON l.id = la.livro_id GROUP BY l.id ORDER BY COUNT(la.autor_id) DESC", nativeQuery = true)
    List<Livro> buscarLivrosComMaisAutoresNative();

    // Estatísticas por década: retorna linhas [decada_start, total]
    @Query(value = "SELECT ((l.ano_publicacao/10) * 10) AS decada_start, COUNT(*) AS total FROM livro l GROUP BY decada_start ORDER BY decada_start", nativeQuery = true)
    List<Object[]> buscarEstatisticasPorDecadaNative();
}
