package ExceptionHandling;

public class LoginCancelledException extends RuntimeException{
    LoginCancelledException(String message)
    {
        super(message);
    }
}
