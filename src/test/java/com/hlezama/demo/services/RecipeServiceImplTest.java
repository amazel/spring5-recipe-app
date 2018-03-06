package com.hlezama.demo.services;

import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock RecipeRepository recipeRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    Recipe recipe = new Recipe();
    HashSet recipesSet = new HashSet();
    recipesSet.add(recipe);

    when(recipeRepository.findAll()).thenReturn(recipesSet);

    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  public void getRecipeByIdTest() throws Exception{
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    Optional<Recipe> recipeOptional = Optional.of(recipe);
    when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById(1L);
    assertNotNull("Null recipe returned",recipeReturned);
    verify(recipeRepository,times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }


  @Test
  public void getRecipes() {

    Set<Recipe> recipes = recipeService.getRecipes();
    assertEquals(recipes.size(), 1);
  }
}
