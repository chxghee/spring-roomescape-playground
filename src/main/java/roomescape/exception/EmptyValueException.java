package roomescape.exception;

public class EmptyValueException extends RuntimeException {

    private final String title;

    public EmptyValueException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
