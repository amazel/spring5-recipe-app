package com.hlezama.demo.controllers;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.services.ImageService;
import com.hlezama.demo.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

	ImageController controller;

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	MockMvc mockMvc;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetImageForm() throws Exception {
		//Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		//When
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));

		//Then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testHandleImagePost() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plan", "Spring Framework Guru".getBytes());

		mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		// verify(imageService,times(1)).saveImageFile(anyLong(), any());

	}
}