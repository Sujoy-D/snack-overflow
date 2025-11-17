package entity;

import java.util.List;
import java.util.ArrayList;

/**
 * Factory for creating Recipe entities.
 */
public class RecipeFactory {

    public Recipe create(
            Integer recipeId,
            String title,
            List<String> ingredients,
            String instructions,
            String cuisine,
            Integer cookingTime,
            String mealType,
            Integer servingSize,
            List<Tag> tags
    ) {
        List<Tag> tempTags = tags != null ? tags : new ArrayList<>();
        return new Recipe(recipeId, title, ingredients, instructions, cuisine, cookingTime, mealType, servingSize, tempTags);
    }

    /*
     * This is for recipe without any tag.
     */
    public Recipe create(Integer recipeId,
                         String title,
                         List<String> ingredients,
                         String instructions,
                         String cuisine,
                         Integer cookingTime,
                         String mealType,
                         Integer servingSize) {

        return create(recipeId, title, ingredients, instructions, cuisine, cookingTime, mealType, servingSize, new ArrayList<>());
    }
}
