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

  @Query("select r from Recipe r join r.steps s where lower(r.title) like lower(:searchString) or lower(s) like lower(:searchString)")
  public List<Recipe> findBySearchString(@Param("searchString") String searchString);

  @Query("select r from Recipe r join r.categories c join r.steps s "
      + "where c.id = :categoryId and (lower(r.title) like lower(:searchString) or lower(s) like lower(:searchString))")
  public List<Recipe> findByCombinedFilter(@Param("categoryId") Long categoryId, @Param("searchString") String searchString);

}
