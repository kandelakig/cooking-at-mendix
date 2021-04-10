package com.kandela.mendix.cooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kandela.mendix.cooking.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
