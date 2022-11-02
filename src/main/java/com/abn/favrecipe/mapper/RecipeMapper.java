package com.abn.favrecipe.mapper;

import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe toEntity(RecipeDTO recipeDTO);

    RecipeDTO toDTO(Recipe recipe);

}
