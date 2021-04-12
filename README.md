# Cooking at Mendix

Demo application for technical assessment at Mendix.

* `mvn clean test` - To run tests
* `mvn clean spring-boot:run` - To start application (runs on port 8080)

## Endpoints

* List Categories: `GEL` - `http://localhost:8080/categories`
* List Recipes: `GEL` - `http://localhost:8080/recipes?category={category-id}&q={search-text}`
  * `category` and `q` parameters are both optional
* Add new Recipe: `POST` - `http://localhost:8080/recipes`
  * Send data in JSON format in request body

## Payload

Sample Recipe payload:
```JSON
{
  "id": 8,
  "title": "30 Minute Chili",
  "categories": [{
    "id": 1,
    "name": "Main dish"
  }, {
    "id": 2,
    "name": "Chili"
  }],
  "yield": 6,
  "ingredients": [{
    "item": "Ground chuck or lean ground; beef",
    "quantity": "1",
    "unit": "pound"
  }, {
    "item": "Onion; large, chopped",
    "quantity": "1",
    "unit": null
  }, {
    "item": "Chili powder; (or to taste)",
    "quantity": "1",
    "unit": "tablespoon"
  }, {
    "item": "Hot pepper sauce; to taste",
    "quantity": null,
    "unit": null
  }],
  "steps": ["Brown the meat in a little butter and cook until the meat is brown. . ."],
  "timeNeeded": "30 min."
}```
