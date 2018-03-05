package com.hlezama.demo.services;

import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
  public void getRecipes() {

    Set<Recipe> recipes = recipeService.getRecipes();
    assertEquals(recipes.size(), 1);
  }
}
