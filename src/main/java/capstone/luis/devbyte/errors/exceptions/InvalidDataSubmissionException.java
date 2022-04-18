package capstone.luis.devbyte.errors.exceptions;

public class InvalidDataSubmissionException extends RuntimeException {
    public InvalidDataSubmissionException() {
        super();
    }

    public InvalidDataSubmissionException(String message) {
        super(message);
    }

    public InvalidDataSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataSubmissionException(Throwable cause) {
        super(cause);
    }
}
