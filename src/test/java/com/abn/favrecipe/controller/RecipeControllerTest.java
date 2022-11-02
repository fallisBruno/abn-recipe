package com.abn.favrecipe.controller;

import com.abn.favrecipe.TestUtils;
import com.abn.favrecipe.dto.FilterRecipeDTO;
import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Recipe;
import com.abn.favrecipe.mapper.RecipeMapperImpl;
import com.abn.favrecipe.repository.IngredientRepository;
import com.abn.favrecipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController testClass;

    @Test
    public void shouldCreateARecipeAndReturnCREATED() {
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();

        Recipe recipe = RecipeMapperImpl.toEntity(recipeDTO);
        recipe.setId(1L);

        when(recipeService.createRecipe(recipeDTO)).thenReturn(RecipeMapperImpl.toDTO(recipe));


        ResponseEntity<RecipeDTO> response = testClass.createRecipe(recipeDTO);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getId(), 1L);
    }

    @Test
    public void shouldDeleteRecipeAndReturnOK() {
        ResponseEntity<String> responseEntity = testClass.deleteRecipe(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnAReceipGivenAnID() {
        Long requestedID = 1L;

        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();
        recipeDTO.setId(requestedID);

        when(recipeService.getReceipt(requestedID)).thenReturn(recipeDTO);

        ResponseEntity<RecipeDTO> response = testClass.getRecipeById(requestedID);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), requestedID);
    }

    @Test
    public void shouldUpdateAndReturnSucessfully() {
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();

        when(recipeService.updateRecipe(recipeDTO)).thenReturn(recipeDTO);

        ResponseEntity<RecipeDTO> response = testClass.updateRecipe(recipeDTO);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnAnEmptyList() {
        FilterRecipeDTO filter = new FilterRecipeDTO();
        filter.setIngredientsExclude(new ArrayList<>());
        filter.setIngredientsInclude(new ArrayList<>());

        when(recipeService.getRecipesByFilter(filter)).thenReturn(Arrays.asList());

        ResponseEntity<List<RecipeDTO>> response = testClass.filterRecipes(filter);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 0);
        assertNotNull(response.getBody());
    }



}