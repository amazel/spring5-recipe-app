package com.hlezama.demo.controllers;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.services.ImageService;
import com.hlezama.demo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {

	ImageService imageService;
	RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("recipe/{recipeId}/image")
	public String getImage(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "/recipe/imageuploadform";
	}

	@PostMapping("recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(recipeId, file);
		return "redirect:/recipe/" + recipeId + "/show";
	}

	@GetMapping("recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {
		log.debug("ImageController.renderImageFromDB - recipeID: "+recipeId);
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(ArrayUtils.toPrimitive(recipeCommand.getImage()));
		IOUtils.copy(is,response.getOutputStream());
	}
}
