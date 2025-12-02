package use_case.similar_recipes;

import java.util.List;

/**
 * The output data for the Similar Recipes Use Case.
 */
public class SimilarRecipesOutputData {
    private final List<Integer> similarRecipes;

    public SimilarRecipesOutputData(List<Integer> similarRecipes) {
        this.similarRecipes = similarRecipes;
    }

    public List<Integer> getSimilarRecipes() {
        return similarRecipes;
    }
}
