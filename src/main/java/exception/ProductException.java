/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author Nibal
 */
public class ProductException extends RuntimeException {

    /**
     * Creates a new instance of <code>ProductException</code> without detail
     * message.
     */
    public ProductException() {
    }

    /**
     * Constructs an instance of <code>ProductException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ProductException(String msg) {
        super(msg);
    }
}
