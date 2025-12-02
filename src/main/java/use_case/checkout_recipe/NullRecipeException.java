package use_case.checkout_recipe;

import java.io.IOException;

/**
 * An Exception for the Checkout Recipe Use Case. Thrown when the recipe bundled into InputData is null.
 */
public class NullRecipeException extends IOException {
    private final String message;

    public NullRecipeException() {
        message = "Null Recipe - no data can be extracted.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
