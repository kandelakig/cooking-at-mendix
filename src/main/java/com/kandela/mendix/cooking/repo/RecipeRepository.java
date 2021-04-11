package com.kandela.mendix.cooking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kandela.mendix.cooking.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

  @Query("select r from Recipe r join r.categories c where c.id = :categoryId")
  public List<Recipe> findByCategory(@Param("categoryId") Long categoryId);

}
