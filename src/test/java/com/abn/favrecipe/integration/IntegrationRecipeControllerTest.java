package com.abn.favrecipe.integration;

import com.abn.favrecipe.TestUtils;
import com.abn.favrecipe.dto.RecipeDTO;
import com.abn.favrecipe.entity.Ingredient;
import com.abn.favrecipe.entity.Recipe;
import com.abn.favrecipe.repository.IngredientRepository;
import com.abn.favrecipe.repository.RecipeRepository;
import com.abn.favrecipe.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationRecipeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = createObjectMapper();

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void before() {
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    public void shouldCreateRecipeSucessfully() throws Exception {
        RecipeDTO recipeDTO = TestUtils.createRecipeDTO();

        MvcResult result = doPost("/recipe", recipeDTO)
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = readJsonPath(result, "$.id");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(Long.valueOf(id));
        assertTrue(optionalRecipe.isPresent());
        assertEquals(optionalRecipe.get().getName(), recipeDTO.getName());
    }

    @Test
    public void shouldGetRecipeSucessfully() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(null);
        recipe.setName("fried garlic");
        recipe.setNumberOfServings(5);
        recipe.setInstructions("someInstruction");
        recipe.setIsVegetarian(true);
        recipe.setIngredients(Arrays.asList( new Ingredient("garlic") ));

        Recipe savedRecipe = recipeRepository.save(recipe);

        doGet("/recipe/" + savedRecipe.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(savedRecipe.getName()))
                .andExpect(jsonPath("$.instructions").value(savedRecipe.getInstructions()))
                .andExpect(jsonPath("$.numberOfServings").value(savedRecipe.getNumberOfServings()));
    }

    protected ResultActions doPut(String path, Object request) throws Exception {
        return mockMvc.perform(put(path)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions doGet(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    protected <T> T readJsonPath(MvcResult result, String jsonPath) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), jsonPath);
    }

    protected ResultActions doPost(String path, Object request) throws Exception {
        return mockMvc.perform(post(path)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

}
