package com.hlezama.demo.services;

import com.hlezama.demo.converters.RecipeCommandToRecipe;
import com.hlezama.demo.converters.RecipeToRecipeCommand;
import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Recipe recipe = new Recipe();
        HashSet recipesSet = new HashSet();
        recipesSet.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesSet);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);
        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void getRecipesTest() {

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
    }

    @Test
    public void testDeleteById(){
        //given
        Long idToDelete = 2L;

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
}
