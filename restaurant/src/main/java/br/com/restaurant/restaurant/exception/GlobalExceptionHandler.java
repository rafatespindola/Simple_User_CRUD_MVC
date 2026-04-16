package br.com.restaurant.restaurant.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail EmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(409);

        problem.setTitle("E-mail já existente na base");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(404);

        problem.setTitle("Recurso não encontrado");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex) {
        logger.warn("Erro de regra de negócio: {}", ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatus(400);

        problem.setTitle("Erro na regra de negócio");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(EmailFormatException.class)
    public ProblemDetail handleEmailFormat(EmailFormatException ex) {
        logger.warn("Erro no formato do email: {}", ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatus(422);

        problem.setTitle("Email mal formatado");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(CreateResourceException.class)
    public ProblemDetail handleCreateResource(CreateResourceException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(400);

        problem.setTitle("Erro ao criar usuário");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        ex.printStackTrace();

        ProblemDetail problem = ProblemDetail.forStatus(500);

        problem.setTitle("Erro interno");
        problem.setDetail("Ocorreu um erro inesperado");
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(400);

        problem.setTitle("Erro de validação");

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        problem.setProperty("errors", errors);

        return problem;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {

        ex.printStackTrace();

        ProblemDetail problem = ProblemDetail.forStatus(400);

        problem.setTitle("Parâmetro(s) inválidos(s)");

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String field = violation.getPropertyPath().toString();

                    // remove prefixo tipo "getAllAppUsers."
                    if (field.contains(".")) {
                        field = field.substring(field.lastIndexOf(".") + 1);
                    }

                    return field + ": " + violation.getMessage();
                })
                .toList();

        problem.setProperty("errors", errors);

        return problem;
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ex.printStackTrace();

        ProblemDetail problem = ProblemDetail.forStatus(400);

        problem.setTitle("Parâmetro faltante");
        problem.setDetail("O parâmetro '" + ex.getParameterName() + "' é obrigatório");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("type", "/errors/missing-parameter");

        return problem;
    }

}
