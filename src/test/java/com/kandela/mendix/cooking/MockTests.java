package com.kandela.mendix.cooking;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kandela.mendix.cooking.model.Category;
import com.kandela.mendix.cooking.model.Recipe;
import com.kandela.mendix.cooking.model.RecipeIngredient;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@WebMvcTest
public class MockTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryRepository categoryRepo;

  @MockBean
  private RecipeRepository recipeRepo;

  private final List<Category> categories;
  private final List<Recipe> recipes;
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

  MockTests() {
    String[] categoriesRaw = { "Main dish", "Cake", "Veggie" };

    String[][] ingredientsRaw = {
        { "Zucchini; cubed 1/2", "1", "pound" },
        { "Butter or margarine", "3", "tablespoons" },
        { "Cheddar cheese; shredded", "1 1/2", "cup" },
        { "Onion; large, chopped", "1", null },
        { "Hot pepper sauce; to taste", null, null },
        { "Toasted Almonds; chopped", "1/2", "cups" }
    };

    String[] steps = { "First Step", "", bigString };

    String[] recipesRaw = { "Good Recipe", "Bad Recipe", "Ugly Recipe" };

    categories = LongStream.range(1L, 4L)
        .mapToObj(i -> new Category(i, categoriesRaw[(int) (i - 1)]))
        .collect(Collectors.toList());

    recipes = IntStream.range(0, 3)
        .mapToObj(i -> new Recipe(100L + i,
            recipesRaw[i],
            categories.stream().filter(cat -> cat.getId() != 3L - i).collect(Collectors.toList()),
            2 * (i + 1),
            Stream.of(ingredientsRaw).map(raw -> new RecipeIngredient(raw[0], raw[1], raw[2])).skip(i).limit(4).collect(Collectors.toList()),
            Arrays.asList(Arrays.copyOf(steps, i + 1)),
            "1 hr."))
        .collect(Collectors.toList());
  }

  @Test
  void testListCategories() throws Exception {
    Mockito.when(categoryRepo.findAll()).thenReturn(categories);

    mockMvc.perform(get("/categories"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(categories.size())))
        .andExpect(jsonPath("$[0].name", is(categories.get(0).getName())))
        .andExpect(jsonPath("$[1].name", is(categories.get(1).getName())))
        .andExpect(jsonPath("$[2].name", is(categories.get(2).getName())));

    Mockito.verify(categoryRepo, times(1)).findAll();
  }

  @Test
  void testListRecipes() throws Exception {
    Mockito.when(recipeRepo.findAll()).thenReturn(recipes);

    mockMvc.perform(get("/recipes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(recipes.size())))
        .andExpect(jsonPath("$[0].id", is(100)))
        .andExpect(jsonPath("$[1].id", is(101)))
        .andExpect(jsonPath("$[2].id", is(102)))
        .andExpect(jsonPath("$[0].title", is("Good Recipe")))
        .andExpect(jsonPath("$[1].title", is("Bad Recipe")))
        .andExpect(jsonPath("$[2].title", is("Ugly Recipe")))
        .andExpect(jsonPath("$[0].yield", is(2)))
        .andExpect(jsonPath("$[1].yield", is(4)))
        .andExpect(jsonPath("$[2].yield", is(6)))
        .andExpect(jsonPath("$[0].categories", hasSize(2)))
        .andExpect(jsonPath("$[1].categories", hasSize(2)))
        .andExpect(jsonPath("$[2].categories", hasSize(2)))
        .andExpect(jsonPath("$[0].categories[*].name", containsInAnyOrder("Main dish", "Cake")))
        .andExpect(jsonPath("$[1].categories[*].name", containsInAnyOrder("Main dish", "Veggie")))
        .andExpect(jsonPath("$[2].categories[*].name", containsInAnyOrder("Cake", "Veggie")))
        .andExpect(jsonPath("$[0].ingredients", hasSize(4)))
        .andExpect(jsonPath("$[1].ingredients", hasSize(4)))
        .andExpect(jsonPath("$[2].ingredients", hasSize(4)))
        .andExpect(jsonPath("$[0].ingredients[0].item", is("Zucchini; cubed 1/2")))
        .andExpect(jsonPath("$[1].ingredients[0].item", is("Butter or margarine")))
        .andExpect(jsonPath("$[2].ingredients[0].item", is("Cheddar cheese; shredded")))
        .andExpect(jsonPath("$[0].steps", hasSize(1)))
        .andExpect(jsonPath("$[1].steps", hasSize(2)))
        .andExpect(jsonPath("$[2].steps", hasSize(3)))
        .andExpect(jsonPath("$[0].steps[0]", is("First Step")))
        .andExpect(jsonPath("$[1].steps[1]", emptyString()))
        .andExpect(jsonPath("$[2].steps[2]", hasLength(bigString.length())))
        .andExpect(jsonPath("$[0].timeNeeded", is("1 hr.")))
        .andExpect(jsonPath("$[1].timeNeeded", is("1 hr.")))
        .andExpect(jsonPath("$[2].timeNeeded", is("1 hr.")));

    Mockito.verify(recipeRepo, times(1)).findAll();
    Mockito.verify(recipeRepo, never()).findByCategory(any());
    Mockito.verify(recipeRepo, never()).findBySearchString(any());
    Mockito.verify(recipeRepo, never()).findByCombinedFilter(any(), any());
  }

  @Test
  void testListRecipesWithCategoryFilter() throws Exception {
    List<Recipe> filteredRecipes = recipes.stream()
        .filter(rcp -> rcp.getCategories().stream().anyMatch(cat -> cat.getId() == 1L))
        .collect(Collectors.toList());

    Mockito.when(recipeRepo.findByCategory(1L)).thenReturn(filteredRecipes);

    mockMvc.perform(get("/recipes?category=1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(filteredRecipes.size())))
        .andExpect(jsonPath("$[*].categories[*].id", hasItem(1)));

    Mockito.verify(recipeRepo, never()).findAll();
    Mockito.verify(recipeRepo, times(1)).findByCategory(1L);
    Mockito.verify(recipeRepo, never()).findBySearchString(any());
    Mockito.verify(recipeRepo, never()).findByCombinedFilter(any(), any());
  }

  @Test
  void testListRecipesWithSearchString() throws Exception {
    List<Recipe> filteredRecipes = Arrays.asList(recipes.get(2));

    Mockito.when(recipeRepo.findBySearchString("%UgLy%")).thenReturn(filteredRecipes);

    mockMvc.perform(get("/recipes?q=UgLy"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(filteredRecipes.size())))
        .andExpect(jsonPath("$[0].title", is("Ugly Recipe")));

    Mockito.verify(recipeRepo, never()).findAll();
    Mockito.verify(recipeRepo, never()).findByCategory(any());
    Mockito.verify(recipeRepo, times(1)).findBySearchString("%UgLy%");
    Mockito.verify(recipeRepo, never()).findByCombinedFilter(any(), any());
  }

  @Test
  void testListRecipesWithCombinedFilter() throws Exception {
    List<Recipe> filteredRecipes = Arrays.asList(recipes.get(2));

    Mockito.when(recipeRepo.findByCombinedFilter(3L, "%UgLy%")).thenReturn(filteredRecipes);

    mockMvc.perform(get("/recipes?category=3&q=UgLy"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(filteredRecipes.size())))
        .andExpect(jsonPath("$[0].title", is("Ugly Recipe")));

    Mockito.verify(recipeRepo, never()).findAll();
    Mockito.verify(recipeRepo, never()).findByCategory(any());
    Mockito.verify(recipeRepo, never()).findBySearchString(any());
    Mockito.verify(recipeRepo, times(1)).findByCombinedFilter(3L, "%UgLy%");
  }

  @Test
  void testListRecipesWithInvalidFilter() throws Exception {
    mockMvc.perform(get("/recipes?category=somethingstupid"))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Mockito.verify(recipeRepo, never()).findByCategory(any());
    Mockito.verify(recipeRepo, never()).findAll();
  }

  @Test
  void testAddRecipe() throws Exception {
    Recipe newRecipe = new Recipe("New Recipe", categories, 8,
        Arrays.asList(new RecipeIngredient("Secret Ingredient", null, null)),
        Arrays.asList("Small string", bigString),
        "1 hr.");

    Mockito.when(recipeRepo.save(any())).thenReturn(new Recipe(999L, newRecipe.getTitle(), newRecipe.getCategories(),
        newRecipe.getYield(), newRecipe.getIngredients(), newRecipe.getSteps(), "1 hr."));

    mockMvc.perform(post("/recipes")
        .content(new ObjectMapper().writeValueAsString(newRecipe))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id", is(999)));

    Mockito.verify(recipeRepo, times(1)).save(any());
  }
}
