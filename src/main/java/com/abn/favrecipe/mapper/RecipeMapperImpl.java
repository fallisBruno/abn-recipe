package com.abn.favrecipe.mapper;

import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Ingredient;
import com.abn.favrecipe.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RecipeMapperImpl {

    public static Recipe toEntity(RecipeDTO recipeDTO) {
        if ( recipeDTO == null ) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId( recipeDTO.getId() );
        recipe.setName( recipeDTO.getName() );
        recipe.setIsVegetarian( recipeDTO.getIsVegetarian() );
        recipe.setNumberOfServings( recipeDTO.getNumberOfServings() );
        recipe.setInstructions( recipeDTO.getInstructions() );

        List<Ingredient> ingredients = recipeDTO.getIngredients().stream()
                .map(name -> new Ingredient(name))
                .collect(Collectors.toList());

        recipe.setIngredients(ingredients);

        return recipe;
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setId( recipe.getId() );
        recipeDTO.setName( recipe.getName() );
        recipeDTO.setIsVegetarian( recipe.getIsVegetarian() );
        recipeDTO.setNumberOfServings( recipe.getNumberOfServings() );
        recipeDTO.setInstructions( recipe.getInstructions() );

        List<String> ingredients = recipe.getIngredients().stream()
                .map(ingredient -> ingredient.getName())
                .collect(Collectors.toList());

        recipeDTO.setIngredients(ingredients);

        return recipeDTO;
    }
}

