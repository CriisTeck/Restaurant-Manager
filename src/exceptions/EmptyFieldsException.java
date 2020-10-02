package exceptions;

public class EmptyFieldsException extends Exception {
    public EmptyFieldsException() {
        super("Hay campos vacios, por favor llenelos o cancele la operacion.");
    }
}
