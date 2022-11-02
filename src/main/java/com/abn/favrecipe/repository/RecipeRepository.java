package com.abn.favrecipe.repository;

import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "select new com.abn.favrecipe.dto.RecipeDTO(r) " +
            " from Recipe r" +
            " where ((:inc) is null or exists (select 1 from Ingredient i where i.recipe = r and i.name in :inc))" +
            " and ((:exc) is null or not exists (select 1 from Ingredient i where i.recipe = r and i.name in :exc))" +
            " and ((:veg) is null or r.isVegetarian = (:veg))" +
            " and ((:name) is null or r.name LIKE CONCAT('%',:name,'%'))" +
            " and ((:instru) is null or r.instructions LIKE CONCAT('%',:instru,'%'))" +
            " and ((:number) is null or r.numberOfServings = (:number))")
    List<RecipeDTO> findAllByFilter(@Param("name") String name,
                                    @Param("veg") Boolean isVegetarian,
                                    @Param("instru") String instructions,
                                    @Param("number") Integer numberOfServings,
                                    @Param("exc") List<String> ingredientsExclude,
                                    @Param("inc") List<String> ingredientsInclude);
}
