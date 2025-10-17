import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

package com.biblioteca.sistema_biblioteca.service;

import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
    
    // CORREÇÃO: "public" minúsculo
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }
    
    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }
    
    public Optional<Livro> atualizar(Long id, Livro livroDetalhes) {
        return livroRepository.findById(id)
            .map(livroExistente -> {
                livroExistente.setTitulo(livroDetalhes.getTitulo());
                livroExistente.setQtdPaginas(livroDetalhes.getQtdPaginas());
                livroExistente.setPublicacao(livroDetalhes.getPublicacao());
                return livroRepository.save(livroExistente);
            }); // CORREÇÃO: Ponto e vírgula e parêntese de fechamento aqui
    }
    
    public boolean deletar(Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}