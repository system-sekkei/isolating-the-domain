package example.api.view.error;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    ErrorType type;
    List<String> messages;


    public ErrorResponse(ErrorType type) {
        this.type = type;
        this.messages = new ArrayList<>();
    }

    public void add(String message) {
        messages.add(message);
    }

    public ResponseEntity<ErrorResponse> toEntity() {
        return ResponseEntity.status(type.httpStatus).body(this);
    }
}