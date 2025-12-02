package use_case.similar_recipes;

/**
 * The output boundary for the Similar Recipes Use Case.
 */
public interface SimilarRecipesOutputBoundary {

    /**
     * Prepares the success view of the Presenter - no exceptions were thrown/handled.
     * @param outputData the processed data from the Similar Recipes Use Case Interactor.
     */
    void prepareSuccessView(SimilarRecipesOutputData outputData);

    /**
     * Prepares the fail view of the Presenter - an exception had to be handled.
     * @param exceptionMessage processed error message of the exception that was handled.
     */
    void prepareFailView(String exceptionMessage);
}
