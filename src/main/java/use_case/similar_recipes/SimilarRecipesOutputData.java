package use_case.similar_recipes;

import java.util.ArrayList;

public class SimilarRecipesOutputData {
    private final ArrayList<Integer> similarRecipes;

    public SimilarRecipesOutputData(ArrayList<Integer> similarRecipes) {
        this.similarRecipes = similarRecipes;
    }

    public ArrayList<Integer> getSimilarRecipes() {
        return similarRecipes;
    }
}
