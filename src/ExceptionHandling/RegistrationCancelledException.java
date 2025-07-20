package ExceptionHandling;
public class RegistrationCancelledException extends RuntimeException {
    public RegistrationCancelledException(String message) {
        super(message);
    }
}

