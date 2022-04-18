package capstone.luis.devbyte.errors;

import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.errors.exceptions.ModelEntityNotFoundException;
import capstone.luis.devbyte.errors.exceptions.UnauthorizedRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Could not write JSON";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    // exceptions here
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ModelEntityNotFoundException.class)
    protected ResponseEntity<Object> handleCommentNotFound(
            ModelEntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(String.valueOf(ex.getCause()));
        return buildResponseEntity(apiError);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedRequestException.class)
    protected  ResponseEntity<Object> handleUnauthorizedRequest(UnauthorizedRequestException ex){
        ApiError apiError = new ApiError(FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointer(NullPointerException e) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("You are trying to access data that does not exist.");
        apiError.setDebugMessage(String.valueOf(e.getCause()));
        return buildResponseEntity(apiError);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataSubmissionException.class)
    protected ResponseEntity<Object> handleInvalidDataSubmission(InvalidDataSubmissionException e){
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
