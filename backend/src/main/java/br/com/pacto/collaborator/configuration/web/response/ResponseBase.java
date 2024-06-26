package br.com.pacto.collaborator.configuration.web.response;

public class ResponseBase<T> {

  private String message;

  private boolean success = true;

  private T data;

  private static String MESSAGE_SUCCESS = "Operation performed successfully";

  public static ResponseBase<Void> empty() {
    return new ResponseBase<Void>()
        .setData(null)
        .setSuccess(true);
  }

  public static <T> ResponseBase<T> of(final T data) {
    return new ResponseBase<T>()
        .setData(data)
        .setMessage(MESSAGE_SUCCESS)
        .setSuccess(true);
  }

  public static <T> ResponseBase<T> of(
      final T data,
      final String message,
      final boolean success) {
    return new ResponseBase<T>()
        .setData(data)
        .setMessage(message)
        .setSuccess(success);
  }

  public static <T> ResponseBase<T> success() {
    return new ResponseBase<T>()
        .setMessage(MESSAGE_SUCCESS)
        .setSuccess(true);
  }

  public String getMessage() {
    return this.message;
  }

  public ResponseBase<T> setMessage(final String message) {
    this.message = message;
    return this;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public ResponseBase<T> setSuccess(final boolean success) {
    this.success = success;
    return this;
  }

  public T getData() {
    return this.data;
  }

  public ResponseBase<T> setData(final T entidade) {
    this.data = entidade;
    return this;
  }

}
