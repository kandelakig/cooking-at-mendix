package com.kandela.mendix.cooking;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebLayerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void initialCategoriesShouldBeLoaded() throws Exception {
    mockMvc.perform(get("/categories"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(7)))
        .andExpect(content().string(containsString("Main dish")))
        .andExpect(content().string(containsString("Chili")))
        .andExpect(content().string(containsString("Liquor")))
        .andExpect(content().string(containsString("Cakes")))
        .andExpect(content().string(containsString("Cake mixes")))
        .andExpect(content().string(containsString("Microwave")))
        .andExpect(content().string(containsString("Vegetables")));
  }

  @Test
  void initialRecipesShouldBeLoaded() throws Exception {
    mockMvc.perform(get("/recipes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(content().string(containsString("30 Minute Chili")))
        .andExpect(content().string(containsString("Amaretto Cake")))
        .andExpect(content().string(containsString("Another Zucchini Dish")));
  }
}
