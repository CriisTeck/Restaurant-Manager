package exceptions;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {
        super("Producto no encontrado");
    }
}
