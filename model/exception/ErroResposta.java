import java.util.List;

public class ErroResposta {
    private final int status;
    private final String mensagem;
    private final List<String> erros;

    public ErroResposta(int status, String mensagem, List<String> erros) {
        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<String> getErros() {
        return erros;
    }
    
}
