package roomescape.exception;

public class NotFoundReservationException extends RuntimeException {

    private final String title;

    public NotFoundReservationException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
