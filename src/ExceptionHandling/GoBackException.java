package ExceptionHandling;

public class GoBackException extends RuntimeException{
    public GoBackException(String message)
    {
        super(message);
    }
}
