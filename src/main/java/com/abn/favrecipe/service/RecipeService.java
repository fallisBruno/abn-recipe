package com.abn.favrecipe.service;

import com.abn.favrecipe.dto.FilterRecipeDTO;
import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Ingredient;
import com.abn.favrecipe.entity.Recipe;
import com.abn.favrecipe.mapper.RecipeMapperImpl;
import com.abn.favrecipe.repository.IngredientRepository;
import com.abn.favrecipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapperImpl.toEntity(recipeDTO);
        Recipe createdRecipe = recipeRepository.save(recipe);
        for (Ingredient ingredient : recipe.getIngredients()){
            ingredient.setRecipe(createdRecipe);
            ingredientRepository.save(ingredient);
        }
        return RecipeMapperImpl.toDTO(createdRecipe);
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        ingredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    public RecipeDTO getReceipt(Long id) {
        Recipe entity = recipeRepository.getReferenceById(id);
        return RecipeMapperImpl.toDTO(entity);
    }

    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapperImpl.toEntity(recipeDTO);
        Recipe recipeDB = recipeRepository.findById( recipe.getId() ).get();
        clearIngredients(recipe, recipeDB);
        recipeDB.setIngredients( recipe.getIngredients() );
        recipeDB.setInstructions( recipe.getInstructions() );
        recipeDB.setName( recipe.getName() );
        recipeDB.setIsVegetarian( recipe.getIsVegetarian() );
        recipeDB.setNumberOfServings( recipe.getNumberOfServings() );
        return RecipeMapperImpl.toDTO( recipeRepository.save( recipeDB ) );
    }

    private void clearIngredients(Recipe recipe, Recipe recipeDB) {
        for(Ingredient ingredient : recipe.getIngredients())
            ingredient.setRecipe(recipe);
        for(Ingredient ingredient : recipeDB.getIngredients())
            ingredientRepository.deleteById(ingredient.getId());
    }

    public List<RecipeDTO> getRecipesByFilter(FilterRecipeDTO filterRecipeDTO) {
        List<RecipeDTO> all = recipeRepository.findAllByFilter(
                filterRecipeDTO.getName(),
                filterRecipeDTO.getIsVegetarian(),
                filterRecipeDTO.getInstructions(),
                filterRecipeDTO.getNumberOfServings(),
                filterRecipeDTO.getIngredientsExclude(),
                filterRecipeDTO.getIngredientsInclude());
        return all;
    }
}
