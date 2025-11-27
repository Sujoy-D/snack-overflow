package interface_adapter.similar_recipes;

import java.util.ArrayList;
import java.util.List;

public class SimilarRecipesState {

    private List<Integer> similarRecipes = new ArrayList<>();

    private String errorMessage = null;

    public List<Integer> getSimilarRecipes() {
        return similarRecipes;
    }

    public void setSimilarRecipes(List<Integer> similarRecipes) {
        this.similarRecipes = similarRecipes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
