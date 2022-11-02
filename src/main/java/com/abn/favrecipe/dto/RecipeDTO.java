package com.abn.favrecipe.dto;

import com.abn.favrecipe.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    @JsonView(value = View.UserView.PUT.class)
    private Long id;

    @JsonView(value = {View.UserView.POST.class, View.UserView.PUT.class})
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @JsonView(value = {View.UserView.POST.class, View.UserView.PUT.class})
    @NotNull(message = "Is vegetarian or not ?")
    private Boolean isVegetarian;

    @JsonView(value = {View.UserView.POST.class, View.UserView.PUT.class})
    @NotNull(message = "How many people can be served ?")
    private Integer numberOfServings;

    @JsonView(value = {View.UserView.POST.class, View.UserView.PUT.class})
    @NotBlank(message = "Instructions cannot be empty")
    private String instructions;

    @JsonView(value = {View.UserView.POST.class, View.UserView.PUT.class})
    @NotEmpty(message = "What are the ingredients ?")
    private List<String> ingredients;

    public RecipeDTO(Recipe recipe){
        this.id = recipe.getId();
        this.isVegetarian = recipe.getIsVegetarian();
        this.numberOfServings = recipe.getNumberOfServings();
        this.instructions = recipe.getInstructions();
        this.name = recipe.getName();

        List<String> ingredients = recipe.getIngredients().stream()
                .map(ingredient -> ingredient.getName())
                .collect(Collectors.toList());

        this.ingredients = ingredients;
    }

}

