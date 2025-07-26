package ExceptionHandling;

public class UpdateCancelledException extends RuntimeException{
    public UpdateCancelledException(String message)
    {
        super(message);
    }
}
