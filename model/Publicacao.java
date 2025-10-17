import org.springframework.cglib.core.Local;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class Publicacao {

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 25, message = "O nome do autor deve ter no máximo 25 caracteres")
    private String autor;

    @NotNull(message = "A data de publicação é obrigatória")    
    private Local dataPublicacao;
    @NotBlank(message = "A editora é obrigatória")
    private String editora;
    public Publicacao(
            @NotBlank(message = "O autor é obrigatório") @Size(max = 25, message = "O nome do autor deve ter no máximo 25 caracteres") String autor,
            @NotNull(message = "A data de publicação é obrigatória") Local dataPublicacao,
            @NotBlank(message = "A editora é obrigatória") String editora) {
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
    }
    
}