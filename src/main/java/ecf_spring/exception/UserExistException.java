package ecf_spring.exception;

public class UserExistException extends Exception{
    public UserExistException() {
        super("User Exist");
    }
}
