package com.abn.favrecipe.service;

import com.abn.favrecipe.TestUtils;
import com.abn.favrecipe.dto.FilterRecipeDTO;
import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Ingredient;
import com.abn.favrecipe.entity.Recipe;
import com.abn.favrecipe.mapper.RecipeMapperImpl;
import com.abn.favrecipe.repository.IngredientRepository;
import com.abn.favrecipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService testClass;

    @Test
    void shouldCreateARecipeEntity(){
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();
        recipeDTO.setId(1L);

        when(recipeRepository.save(any())).thenReturn(RecipeMapperImpl.toEntity(recipeDTO));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(new Ingredient());

        RecipeDTO recipe = testClass.createRecipe(recipeDTO);

        assertEquals(recipe.getName(), recipeDTO.getName());
    }

    @Test
    void shouldDeleteRecipeEntity(){
        testClass.deleteRecipeById(1L);
        verify(recipeRepository, atLeast(1)).deleteById(1L);
    }

    @Test
    void shouldReturnRecipeEntity(){
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();
        recipeDTO.setId(1L);

        when(recipeRepository.getReferenceById(1L)).thenReturn(RecipeMapperImpl.toEntity(recipeDTO));

        RecipeDTO response = testClass.getReceipt(1L);

        assertNotNull(response);
        assertEquals(recipeDTO.getId(), response.getId());
    }

    @Test
    void shouldUpdateRecipeEntity(){
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();
        recipeDTO.setId(1L);

        when(recipeRepository.findById(recipeDTO.getId()))
                .thenReturn(Optional.ofNullable(RecipeMapperImpl.toEntity(recipeDTO)));

        doNothing().when(ingredientRepository)
                .deleteById(any());

        when(recipeRepository.save(any(Recipe.class)))
                .thenReturn(RecipeMapperImpl.toEntity(recipeDTO));

        RecipeDTO result = testClass.updateRecipe(recipeDTO);

        assertNotNull(result);
    }

    @Test
    public void shouldReturnRecipeEmptyList(){
        FilterRecipeDTO filter = new FilterRecipeDTO();
        filter.setIngredientsExclude(new ArrayList<>());
        filter.setIngredientsInclude(new ArrayList<>());

        when(recipeRepository.findAllByFilter(any(), any(), any(), any(), any(), any()))
                .thenReturn(Arrays.asList());

        List<RecipeDTO> result = testClass.getRecipesByFilter(filter);

        assertNotNull(result);
        assertEquals(result.size(), 0);
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