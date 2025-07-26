package ExceptionHandling;

public class LoginCancelledException extends RuntimeException{
    public LoginCancelledException(String message)
    {
        super(message);
    }
}
