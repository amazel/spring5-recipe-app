package com.hlezama.demo.controllers;

import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

  IndexController indexController;
  @Mock RecipeServiceImpl recipeService;

  @Mock Model model;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    indexController = new IndexController(recipeService);
  }

  @Test
  public void testMockMVC() throws Exception{
      MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
      mockMvc.perform(MockMvcRequestBuilders.get("/"))
              .andExpect(status().isOk())
              .andExpect(view().name("index"));
  }

    @Test
  public void getIndex() {

    //Given
    Set<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());
    when(recipeService.getRecipes()).thenReturn(recipes);
    ArgumentCaptor<Set<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);


    //When
    String retString = indexController.getIndex(model);

    //Then
    assertEquals("index", retString);
    verify(recipeService, times(1)).getRecipes();
    verify(model,times(1)).addAttribute(eq("recipes"),setArgumentCaptor.capture());

    Set<Recipe> setInController= setArgumentCaptor.getValue();

    assertEquals(1,setInController.size());
  }
}
