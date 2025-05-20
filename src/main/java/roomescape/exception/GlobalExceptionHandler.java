package roomescape.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> notFoundException(NotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(e.getTitle(), 400, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResult> dateTimeFormatExceptionHandler(DateTimeParseException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult("시간 포맷 오류", 400, "시간은 yyyy-MM-dd / HH:mm 형식이어야 합니다.", request.getRequestURI()));
    }

    @ExceptionHandler(EmptyValueException.class)
    public ResponseEntity<ErrorResult> emptyValueException(EmptyValueException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(e.getTitle(), 400, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult> runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        System.out.println("잡힌 예외 타입: " + e.getClass().getName());  // 로그
        System.out.println("메세지: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResult("알 수 없는 오류", 500, "관리자에게 문의 하세요", request.getRequestURI()));
    }
}
