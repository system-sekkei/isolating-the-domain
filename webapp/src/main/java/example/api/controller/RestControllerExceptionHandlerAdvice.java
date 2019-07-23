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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 例外の型に応じたエラーレスポンスを作成するAdvice
 * <p>
 * {code @ExceptionHandler} で処理する例外に対応する情報を
 * src/main/resources/errorResponse.properties から取得し、
 * ErrorResponse を構築します。
 */
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandlerAdvice {
    static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandlerAdvice.class);

    ResourceBundle resource;

    public RestControllerExceptionHandlerAdvice() {
        resource = ResourceBundle.getBundle("errorResponse", Locale.ROOT);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // 例外オブジェクトからバリデーションに失敗したフィールド名を取得してメッセージに詰める
        // TODO このメッセージの表示方法だとユーザにはちょっとつらい気がする
        String fieldNames = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .distinct()
                .collect(Collectors.joining(", "));
        return errorResponse(exception, fieldNames);
    }


    ResponseEntity<ErrorResponse> errorResponse(Throwable exception, Object... args) {
        return errorResponse(exception.getClass(), args);
    }

    ResponseEntity<ErrorResponse> errorResponse(Class<?> clz, Object... args) {
        return errorResponse(clz.getName(), args);
    }

    ResponseEntity<ErrorResponse> errorResponse(String exceptionClassName, Object... args) {
        String type = resource.getString(exceptionClassName + ".type");
        String code = resource.getString(exceptionClassName + ".code");
        String message = resource.getString(exceptionClassName + ".message");
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorType.valueOf(type), code,
                MessageFormat.format(message, args)
        );
        return errorResponse.toEntity();
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleOtherException(Exception exception) {
        logger.error("予期せぬエラー", exception);
        // 可能な限り例外が発生しない方法でエラーレスポンスを構築する
        return new ErrorResponse(ErrorType.システムエラー, "E99999", "予期せぬエラーが発生しました。").toEntity();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // BeanValidationのrejectedValueをbindingするためにdirectFieldAccessを有効にする
        binder.initDirectFieldAccess();
    }
}
