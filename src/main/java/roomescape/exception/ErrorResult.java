package roomescape.exception;

public class ErrorResult {

    private final String title;
    private final int status;
    private final String detail;
    private final String instance;

    public ErrorResult(String title, int status, String detail, String instance) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }
}
