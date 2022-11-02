package com.abn.favrecipe.controller;

import com.abn.favrecipe.dto.FilterRecipeDTO;
import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.dto.View;
import com.abn.favrecipe.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/recipe")
public class RecipeController {

    private RecipeService recipeService;

    @ApiOperation("Create a Recipe and Ingredients")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody @JsonView(value = View.UserView.POST.class) @Valid RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.createRecipe(recipeDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Delete a Recipe and Ingredients")
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") @Valid @NotNull Long id){
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Get a Recipe providing a specific ID")
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id") @Valid @NotNull Long id){
        return new ResponseEntity<>(recipeService.getReceipt(id), HttpStatus.OK);
    }

    @ApiOperation("Update a Recipe and it's Ingredients")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody @Valid RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.updateRecipe(recipeDTO),HttpStatus.OK);
    }

    @ApiOperation("Filter Recipes")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<List<RecipeDTO>> filterRecipes(@RequestBody @Valid FilterRecipeDTO filterRecipeDTO){
        filterRecipeDTO.validateParameters();
        List<RecipeDTO> recipes = recipeService.getRecipesByFilter(filterRecipeDTO);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

}
