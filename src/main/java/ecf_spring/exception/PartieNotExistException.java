package ecf_spring.exception;

public class PartieNotExistException extends Exception {
    public PartieNotExistException() {
        super("La partie n'existe pas");
    }
}
