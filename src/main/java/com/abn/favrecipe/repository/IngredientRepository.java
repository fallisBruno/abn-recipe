package com.abn.favrecipe.repository;

import com.abn.favrecipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    void deleteByRecipeId(Long id);
}
