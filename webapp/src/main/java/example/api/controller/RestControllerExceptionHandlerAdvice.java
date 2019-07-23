package example.api.controller;

import example.api.view.error.ErrorResponse;
import example.api.view.error.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandlerAdvice {
    static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandlerAdvice.class);

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // BindingResultの情報を元にエラーレスポンスを構築する
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.入力エラー);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorResponse.add(fieldError.getDefaultMessage());
        }
        return errorResponse.toEntity();
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleOtherException(Exception exception) {
        logger.error("予期せぬエラー", exception);
        // 可能な限り例外が発生しない方法でエラーレスポンスを構築する
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.システムエラー);
        errorResponse.add("予期せぬエラーが発生しました。");
        return errorResponse.toEntity();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // BeanValidationのrejectedValueをbindingするためにdirectFieldAccessを有効にする
        binder.initDirectFieldAccess();
    }
}
