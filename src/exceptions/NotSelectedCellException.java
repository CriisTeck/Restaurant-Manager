package exceptions;

public class NotSelectedCellException extends Exception {

    public NotSelectedCellException() {
        super("No ha seleccionado ningun producto");
    }
}
