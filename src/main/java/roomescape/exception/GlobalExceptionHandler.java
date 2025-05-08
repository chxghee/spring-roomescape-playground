package roomescape.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundReservationException.class)
    public ErrorResult notFoundReservationExceptionHandler(NotFoundReservationException e, HttpServletRequest request) {
        return new ErrorResult(e.getTitle(), 400, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ErrorResult dateTimeFormatExceptionHandler(DateTimeParseException e, HttpServletRequest request) {
        return new ErrorResult("시간 포맷 오류", 400, "시간은 yyyy-MM-dd / HH:mm 형식이어야 합니다.", request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyValueException.class)
    public ErrorResult emptyValueException(EmptyValueException e, HttpServletRequest request) {
        return new ErrorResult(e.getTitle(), 400, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResult runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        return new ErrorResult("알 수 없는 오류", 400, "관리자에게 문의 하세요", request.getRequestURI());
    }
}
