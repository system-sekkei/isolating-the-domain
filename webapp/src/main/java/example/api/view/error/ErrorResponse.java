package example.api.view.error;

import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public ErrorResponse(ErrorType type, String code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    ErrorType type;
    String code;
    String message;

    public ResponseEntity<ErrorResponse> toEntity() {
        return ResponseEntity.status(type.httpStatus).body(this);
    }
}