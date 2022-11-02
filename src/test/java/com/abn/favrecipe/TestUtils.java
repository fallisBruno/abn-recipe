package com.abn.favrecipe;

import com.abn.favrecipe.dto.RecipeDTO;

import java.util.Arrays;

public abstract class TestUtils {

    public static RecipeDTO createRecipeDTO() {
        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setIngredients(Arrays.asList("garlic", "cooking oil", "salt"));
        recipeDTO.setName("fried garlic");
        recipeDTO.setIsVegetarian(true);
        recipeDTO.setNumberOfServings(1);
        recipeDTO.setInstructions("Oil with salt and garlic");
        return recipeDTO;
    }

}
