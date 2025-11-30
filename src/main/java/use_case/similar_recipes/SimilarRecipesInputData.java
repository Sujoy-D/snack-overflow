package use_case.similar_recipes;

/**
 * Input data for the Similar Recipes Use Case.
 */
public class SimilarRecipesInputData {
    private final int recipeID;

    public SimilarRecipesInputData(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getRecipeID() {
        return recipeID;
    }
}
