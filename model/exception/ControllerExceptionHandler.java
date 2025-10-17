import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationExceptions(MethodArgumentNotValidException ex) {
         List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String field = error.getField();
                    String message = error.getDefaultMessage();
                    if (field.contains("publicacao.")) {
                        field = field.replace("publicacao.", "publicacao_");
                    }
                    return field + ": " + message;
                })
                .collect(Collectors.toList());
              
        ErroResposta erroResposta = new ErroResposta(
            HttpStatus.BAD_REQUEST.value(),
            "Falha na validação dos campos. Preencha os dados obrigatórios ou de forma correta.",
            erros
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
    }

}
