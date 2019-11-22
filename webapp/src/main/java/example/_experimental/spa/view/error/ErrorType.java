package example._experimental.spa.view.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    入力エラー(HttpStatus.UNPROCESSABLE_ENTITY),
    業務エラー(HttpStatus.BAD_REQUEST),
    システムエラー(HttpStatus.INTERNAL_SERVER_ERROR);

    HttpStatus httpStatus;

    ErrorType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}