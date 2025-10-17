import jakarta.annotation.Generated;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Livro {

    @Id
    @Generated(strategy = jakarta.annotation.GeneratedValue.Strategy.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 40, message = "O título deve ter no máximo 40 caracteres")
    private String titulo;

    @NotNull(message = "A quantidade de páginas é obrigatória")
    @Min(value = 1, message = "A quantidade de páginas deve ser maior que zero")
    private Integer qtdPaginas;

    @Embedded
    @Valid
    @NotNull(message = "As informações de  publicação são obrigatórias")
    private Publicacao publicacao;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getQtdPaginas() {
        return qtdPaginas;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setQtdPaginas(Integer qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

}