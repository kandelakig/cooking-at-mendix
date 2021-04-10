package com.kandela.mendix.cooking;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kandela.mendix.cooking.model.Category;
import com.kandela.mendix.cooking.repo.CategoryRepository;
import com.kandela.mendix.cooking.repo.RecipeRepository;

@WebMvcTest
public class CookingAtMendixApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryRepository categoryRepo;

  @MockBean
  private RecipeRepository recipeRepo;

  private final List<Category> categories;

  CookingAtMendixApplicationTests() {
    categories = Stream.of(new String[] { "Main dish", "Cake", "Veggie" })
        .map(Category::new)
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
  }
}
