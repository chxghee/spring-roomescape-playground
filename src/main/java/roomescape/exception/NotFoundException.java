package roomescape.exception;

public class NotFoundException extends RuntimeException {

    private final String title;

    public NotFoundException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
