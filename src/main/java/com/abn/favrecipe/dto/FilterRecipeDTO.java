package com.abn.favrecipe.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FilterRecipeDTO {

    private String name;
    private Boolean isVegetarian;
    private Integer numberOfServings;
    private String instructions;
    private List<String> ingredientsInclude;
    private List<String> ingredientsExclude;

    public void validateParameters(){
        if(StringUtils.isBlank(this.name)){
            this.name = null;
        }
        if(StringUtils.isBlank(this.instructions)){
            this.instructions = null;
        }
        boolean hasIncludedIngredients = ingredientsInclude.stream()
                .filter(include -> !include.isEmpty()).collect(Collectors.toList()).size() > 0;
        if(CollectionUtils.isEmpty(ingredientsInclude) || !hasIncludedIngredients){
            this.ingredientsInclude = null;
        }
        boolean hasExcludeIngredients = ingredientsExclude.stream()
                .filter(exclude -> !exclude.isEmpty()).collect(Collectors.toList()).size() > 0;
        if(CollectionUtils.isEmpty(ingredientsExclude) || !hasExcludeIngredients){
            this.ingredientsExclude = null;
        }
    }

}
