package capstone.luis.devbyte.errors.exceptions;

public class JWTVerificationErrorException extends RuntimeException {
    public JWTVerificationErrorException() {
        super();
    }

    public JWTVerificationErrorException(String message) {
        super(message);
    }

    public JWTVerificationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTVerificationErrorException(Throwable cause) {
        super(cause);
    }
}
