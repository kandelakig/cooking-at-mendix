package com.kandela.mendix.cooking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kandela.mendix.cooking.model.Category;
import com.kandela.mendix.cooking.model.Recipe;
import com.kandela.mendix.cooking.model.RecipeIngredient;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RepositoryTests {

  private final List<Category> categories;
  private final String bigString = "Sprinkle 1 cup almonds into bottom of a well-greased and floured 10\n" +
      "tube pan; set aside. Combine cake mix,     pudding mix, eggs, oil, water,\n" +
      "amaretto, and almond        extract in a mixing bowl; beat on low speed of\n" +
      "an electric mixer til dry ingredients are moistened.          Increase\n" +
      "speed to medium, and beat 4 minutes. Stir in      remaining 1/2 cup\n" +
      "almonds. Pour batter into prepared       tube pan. Bake at 325~ for 1 hour\n" +
      "or til a wooden pick inserted in center comes out clean. Cool cake in pan\n" +
      "10-15 minutes; remove from pan, and cool completely.       Combine sugar,\n" +
      "water, and butter in a small saucepan;      bring to a boil. Reduce heat to\n" +
      "medium and gently boil     4-5 minutes, stirring occasionally, until sugar\n" +
      "dissolves. Remove from heat, and cool 15 minutes. Stir\n" +
      "in amaretto and almond extract. Punch holes in top of      cake with a\n" +
      "wooden pick; slowly spoon glaze on top of cake, allowing glaze to absorb in\n" +
      "cake.\n" +
      "Posted to MC-Recipe Digest V1 #263\n" +
      "Date: Sun, 27 Oct 1996 20:04:57 +0000\n" +
      "From: Cheryl Gimenez &lt;clgimenez@earthlink.net&gt;\n";

  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private RecipeRepository recipeRepo;

  RepositoryTests() {
    String[] categoriesRaw = { "Main dish", "Cake", "Veggie" };
    categories = LongStream.range(1L, 4L)
        .mapToObj(i -> new Category(i, categoriesRaw[(int) (i - 1)]))
        .collect(Collectors.toList());
  }

  @Test
  @Order(1)
  void testInitialCategories() throws Exception {
    String[] categoriesNames = { "Main dish", "Chili", "Liquor", "Cakes", "Cake mixes", "Microwave", "Vegetables" };

    List<Category> categories = categoryRepo.findAll();

    assertThat(categories).isNotNull();
    assertThat(categories).hasSize(7);
    assertThat(categories.stream().map(cat -> cat.getName())).containsExactlyInAnyOrder(categoriesNames);
  }

  @Test
  @Order(2)
  void testInitialRecipes() throws Exception {
    String[] recipeTitles = { "30 Minute Chili", "Amaretto Cake", "Another Zucchini Dish" };

    List<Recipe> recipes = recipeRepo.findAll();

    assertThat(recipes).isNotNull();
    assertThat(recipes).hasSize(3);
    assertThat(recipes.stream().map(rcp -> rcp.getTitle())).containsExactlyInAnyOrder(recipeTitles);
  }

  @Test
  @Order(3)
  void testRecipesCategoryFilter() throws Exception {
    assertThat(recipeRepo.findByCategory(1L)).isNotNull().hasSize(1);
    assertThat(recipeRepo.findByCategory(0L)).isNotNull().hasSize(0);
  }

  @Test
  @Order(3)
  void testRecipesTextSearch() throws Exception {
    assertThat(recipeRepo.findBySearchString("%Sprinkle%")).isNotNull().hasSize(2);
    assertThat(recipeRepo.findBySearchString("%MiNUte chili%")).isNotNull().hasSize(1);
    assertThat(recipeRepo.findBySearchString("%some strange search%")).isNotNull().hasSize(0);
  }

  @Test
  @Order(3)
  void testRecipesCombinedFilter() throws Exception {
    assertThat(recipeRepo.findByCombinedFilter(6L, "%Sprinkle%")).isNotNull().hasSize(1);
  }

  @Test
  @Order(300)
  void testAddRecipe() throws Exception {
    Recipe newRecipe = new Recipe("New Recipe", categories, 8,
        Arrays.asList(new RecipeIngredient("Secret Ingredient", null, null)),
        Arrays.asList("Small string", bigString), "1 hr.");

    Recipe createdRecipe = recipeRepo.save(newRecipe);

    assertThat(createdRecipe).isNotNull();
    assertThat(createdRecipe.getId()).isNotNull();

    Recipe retrievedRecipe = recipeRepo.findById(createdRecipe.getId()).orElse(null);

    assertThat(retrievedRecipe).isNotNull();
    assertThat(retrievedRecipe.getTitle()).isSameAs("New Recipe");
  }
}
