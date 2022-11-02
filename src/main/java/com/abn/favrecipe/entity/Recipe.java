package com.abn.favrecipe.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(name = "is_vegetarian")
    private Boolean isVegetarian;

    @NotNull
    @Column(name = "number_of_servings")
    private Integer numberOfServings;

    @NotEmpty
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Ingredient> ingredients;

    @NotNull
    private String instructions;

}
