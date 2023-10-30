package Mathematics.Groups;

public class GroupException extends Exception {

    private final Errors.ErrorCode errorCode;

    public GroupException(String message, Errors.ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GroupException(Errors.ErrorCode errorCode) {
        super(Errors.errorString(errorCode));
        this.errorCode = errorCode;
    }
    public GroupException(Errors.ErrorCode errorCode, Throwable cause) {
        super(Errors.errorString(errorCode), cause);
        this.errorCode = errorCode;
    }

    public Errors.ErrorCode getCode() {
        return this.errorCode;
    }

    public String toString() {
        return super.getMessage() + " Error code: " + this.errorCode;
    }

}