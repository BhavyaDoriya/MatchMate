package ExceptionHandling;

public class DeletionCancelledException extends RuntimeException {
    public DeletionCancelledException(String message)
    {
        super(message);
    }
}
