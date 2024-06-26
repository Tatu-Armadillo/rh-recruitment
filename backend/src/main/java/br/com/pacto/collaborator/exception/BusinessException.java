package br.com.pacto.collaborator.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(final String mensagem) {
        super(mensagem);
    }
}