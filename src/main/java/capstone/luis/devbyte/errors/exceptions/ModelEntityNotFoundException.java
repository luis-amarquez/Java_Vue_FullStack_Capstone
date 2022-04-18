package capstone.luis.devbyte.errors.exceptions;

public class ModelEntityNotFoundException extends RuntimeException{
    public ModelEntityNotFoundException() {
        super();
    }

    public ModelEntityNotFoundException(String message) {
        super(message);
    }

    public ModelEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
