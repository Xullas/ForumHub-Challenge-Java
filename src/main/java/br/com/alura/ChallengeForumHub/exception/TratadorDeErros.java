package br.com.alura.ChallengeForumHub.exception;

import br.com.alura.ChallengeForumHub.exception.dto.DadosErroRecursoNaoEncontrado;
import br.com.alura.ChallengeForumHub.exception.dto.DadosErroValidacao;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DadosErroRecursoNaoEncontrado tratarErroRecursoNaoEncontrado(ResourceNotFoundException ex) {
        return new DadosErroRecursoNaoEncontrado(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<DadosErroValidacao> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
       List<FieldError> erros = ex.getFieldErrors();

        return erros.stream()
                .map(erro -> new DadosErroValidacao(erro.getField(), erro.getDefaultMessage()))
                .toList();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public DadosErroRecursoNaoEncontrado tratarErroRecursoNaoEncontrado(SQLException ex) {
        return new DadosErroRecursoNaoEncontrado(ex.getMessage());
    }
}
