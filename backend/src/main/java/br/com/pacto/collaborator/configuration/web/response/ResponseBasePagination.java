package br.com.pacto.collaborator.configuration.web.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;

import java.util.List;

// @ApiModel(description = "Model responsável por encapsular todos responses paginados da api.")
@JsonPropertyOrder({ "success", "message", "data", "page" })
public class ResponseBasePagination<T> extends ResponseBase<T> {

  private static String MESSAGE_SUCCESS = "Operation performed successfully";

  private String message;

  private boolean success = true;

  private T data;

  private PaginationBase page;

  public static <T> ResponseBasePagination<List<T>> of(final Page<T> data) {
    final var response = new ResponseBasePagination<List<T>>();

    response.setData(data.getContent());
    response.setSuccess(true);
    response.setMessage(MESSAGE_SUCCESS);

    final var page = new PaginationBase(data);
    response.setPage(page);

    return response;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public ResponseBasePagination<T> setMessage(final String message) {
    this.message = message;
    return this;
  }

  @Override
  public boolean isSuccess() {
    return this.success;
  }

  @Override
  public ResponseBasePagination<T> setSuccess(final boolean success) {
    this.success = success;
    return this;
  }

  @Override
  public T getData() {
    return this.data;
  }

  @Override
  public ResponseBasePagination<T> setData(final T entidade) {
    this.data = entidade;
    return this;
  }

  public PaginationBase getPage() {
      return page;
  }

  public ResponseBasePagination<T> setPage(final PaginationBase page) {
    this.page = page;
    return this;
  }

  public <U> ResponseBasePagination<T> setPaginacao(final Page<U> data) {
    this.page = new PaginationBase(data);
    return this;
  }

}
