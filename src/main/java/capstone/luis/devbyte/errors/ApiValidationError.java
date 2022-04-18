package capstone.luis.devbyte.errors;

import java.util.Objects;

public class ApiValidationError extends ApiSubError {
    private String object;
    private String fields;
    private Object rejectedValue;
    private String message;

    public ApiValidationError() {
    }

    public ApiValidationError(String object, String fields, Object rejectedValue, String message) {
        this.object = object;
        this.fields = fields;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiValidationError)) return false;
        ApiValidationError that = (ApiValidationError) o;
        return Objects.equals(object, that.object) && Objects.equals(fields, that.fields) && Objects.equals(rejectedValue, that.rejectedValue) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, fields, rejectedValue, message);
    }

    @Override
    public String toString() {
        return "ApiValidationError{" +
                "object='" + object + '\'' +
                ", fields='" + fields + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", message='" + message + '\'' +
                '}';
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
