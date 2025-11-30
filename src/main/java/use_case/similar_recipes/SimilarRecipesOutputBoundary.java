package use_case.similar_recipes;

public interface SimilarRecipesOutputBoundary {

    void prepareSuccessView(SimilarRecipesOutputData outputData);

    void prepareFailView(String exceptionMessage);
}
