package com.kandela.mendix.cooking;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kandela.mendix.cooking.model.Category;
import com.kandela.mendix.cooking.model.Recipe;
import com.kandela.mendix.cooking.model.RecipeIngredient;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;
import com.kandela.mendix.cooking.xml.Head;
import com.kandela.mendix.cooking.xml.RecipeML;

@Configuration
public class Initializer {

  @Bean
  CommandLineRunner initData(RecipeRepository recipeRepo, CategoryRepository categoryRepo) throws IOException {

    return args -> {
      RecipeML xml = new XmlMapper().readValue(ResourceUtils.getFile("classpath:initialData.xml"), RecipeML.class);

      Map<String, Category> categoriesMap = xml.getRecipe().stream()
          .map(rcp -> rcp.getHead().getCategories().getCat())
          .flatMap(List::stream).distinct()
          .map(str -> new Category(str))
          .map(categoryRepo::save)
          .collect(Collectors.toMap(cat -> cat.getName(), cat -> cat));

      xml.getRecipe().stream()
          .map(rcp -> {
            Head head = rcp.getHead();

            List<Category> categories = head.getCategories().getCat().stream()
                .map(cat -> categoriesMap.get(cat)).collect(Collectors.toList());
            List<RecipeIngredient> ingredients = rcp.getIngredients().getIng().stream()
                .map(ing -> new RecipeIngredient(ing.getItem(), ing.getAmt().getQty(), ing.getAmt().getUnit())).collect(Collectors.toList());
            List<String> steps = rcp.getDirections().getStep().stream().collect(Collectors.toList());

            return new Recipe(head.getTitle(), categories, head.getYield(), ingredients, steps, head.getTimeNeeded());
          })
          .forEach(recipeRepo::save);
    };
  }
}
