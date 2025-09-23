package Utilities;

import java.sql.*;

public class jdbc {
	private static final String URL = "jdbc:postgresql://localhost:5432/RecipeScraping";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Connect@1406";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void insertRecipe(String name, String recipeCategory, String foodCategory, 
                                   String ingredients, String prepTime, String cookTime, 
                                   String tag, Integer servings, String cuisine, 
                                   String description, String method, String nutrients, String url) {
        String sql = "INSERT INTO eliminated_recipes (recipe_name, recipe_tid, food_category, " +
                    "ingredients, preparation_time, cooking_time, tag, no_of_servings, " +
                    "cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name != null && !name.isEmpty() ? name : null);
            pstmt.setString(2, recipeCategory != null && !recipeCategory.isEmpty() ? recipeCategory : null);
            pstmt.setString(3, foodCategory != null && !foodCategory.isEmpty() ? foodCategory : null);
            pstmt.setString(4, ingredients != null && !ingredients.isEmpty() ? ingredients : null);
            pstmt.setString(5, prepTime != null && !prepTime.isEmpty() ? prepTime : null);
            pstmt.setString(6, cookTime != null && !cookTime.isEmpty() ? cookTime : null);
            pstmt.setString(7, tag != null && !tag.isEmpty() ? tag : null);
            
            if (servings != null) {
                pstmt.setInt(8, servings);
            } else {
                pstmt.setNull(8, java.sql.Types.INTEGER);
            }
            
            pstmt.setString(9, cuisine != null && !cuisine.isEmpty() ? cuisine : null);
            pstmt.setString(10, description != null && !description.isEmpty() ? description : null);
            pstmt.setString(11, method != null && !method.isEmpty() ? method : null);
            pstmt.setString(12, nutrients != null && !nutrients.isEmpty() ? nutrients : null);
            pstmt.setString(13, url != null && !url.isEmpty() ? url : null);
            
            pstmt.executeUpdate();
            System.out.println("Recipe inserted successfully: " + name);
            
        } catch (SQLException e) {
            System.err.println("Error inserting recipe: " + e.getMessage());
        }
    }
    
    // Method to insert non-eliminated recipes into lfv_noneliminated_recipes table
    public static void insertNonEliminatedRecipe(String name, String recipeCategory, String foodCategory, 
                                               String ingredients, String prepTime, String cookTime, 
                                               String tag, Integer servings, String cuisine, 
                                               String description, String method, String nutrients, String url) {
        String sql = "INSERT INTO lfv_noneliminated_recipes (recipe_name, recipe_tid, food_category, " +
                    "ingredients, preparation_time, cooking_time, tag, no_of_servings, " +
                    "cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name != null && !name.isEmpty() ? name : null);
            pstmt.setString(2, recipeCategory != null && !recipeCategory.isEmpty() ? recipeCategory : null);
            pstmt.setString(3, foodCategory != null && !foodCategory.isEmpty() ? foodCategory : null);
            pstmt.setString(4, ingredients != null && !ingredients.isEmpty() ? ingredients : null);
            pstmt.setString(5, prepTime != null && !prepTime.isEmpty() ? prepTime : null);
            pstmt.setString(6, cookTime != null && !cookTime.isEmpty() ? cookTime : null);
            pstmt.setString(7, tag != null && !tag.isEmpty() ? tag : null);
            
            if (servings != null) {
                pstmt.setInt(8, servings);
            } else {
                pstmt.setNull(8, java.sql.Types.INTEGER);
            }
            
            pstmt.setString(9, cuisine != null && !cuisine.isEmpty() ? cuisine : null);
            pstmt.setString(10, description != null && !description.isEmpty() ? description : null);
            pstmt.setString(11, method != null && !method.isEmpty() ? method : null);
            pstmt.setString(12, nutrients != null && !nutrients.isEmpty() ? nutrients : null);
            pstmt.setString(13, url != null && !url.isEmpty() ? url : null);
            
            pstmt.executeUpdate();
            System.out.println("Non-eliminated recipe inserted successfully: " + name);
            
        } catch (SQLException e) {
            System.err.println("Error inserting non-eliminated recipe: " + e.getMessage());
        }
    }
    
    public static void insertLFVAddRecipe(String name, String recipeCategory, String foodCategory, 
            String ingredients, String prepTime, String cookTime, 
            String tag, Integer servings, String cuisine, 
            String description, String method, String nutrients, String url) {
                String sql = "INSERT INTO LFVAddRecipe (recipe_name, recipe_tid, food_category, " +
                              "ingredients, preparation_time, cooking_time, tag, no_of_servings, " +
                              "cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

              try (Connection conn = getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {

           pstmt.setString(1, name != null && !name.isEmpty() ? name : null);
           pstmt.setString(2, recipeCategory != null && !recipeCategory.isEmpty() ? recipeCategory : null);
           pstmt.setString(3, foodCategory != null && !foodCategory.isEmpty() ? foodCategory : null);
           pstmt.setString(4, ingredients != null && !ingredients.isEmpty() ? ingredients : null);
           pstmt.setString(5, prepTime != null && !prepTime.isEmpty() ? prepTime : null);
           pstmt.setString(6, cookTime != null && !cookTime.isEmpty() ? cookTime : null);
           pstmt.setString(7, tag != null && !tag.isEmpty() ? tag : null);

          if (servings != null) {
           pstmt.setInt(8, servings);
         } else {
          pstmt.setNull(8, java.sql.Types.INTEGER);
         }

         pstmt.setString(9, cuisine != null && !cuisine.isEmpty() ? cuisine : null);
         pstmt.setString(10, description != null && !description.isEmpty() ? description : null);
         pstmt.setString(11, method != null && !method.isEmpty() ? method : null);
         pstmt.setString(12, nutrients != null && !nutrients.isEmpty() ? nutrients : null);
         pstmt.setString(13, url != null && !url.isEmpty() ? url : null);

           pstmt.executeUpdate();
          System.out.println("LFV ADD recipe inserted successfully: " + name);

         } catch (SQLException e) {
        System.err.println("Error inserting LFV ADD recipe: " + e.getMessage());
       }
      }  
    
    public static void insertLCHFAddRecipe(String name, String recipeCategory, String foodCategory,
            String ingredients, String prepTime, String cookTime,
            String tag, Integer servings, String cuisine,
            String description, String method,
            String nutrients, String url) {
String sql = "INSERT INTO lchf_add_recipes (recipe_name, recipe_tid, food_category, " +
"ingredients, preparation_time, cooking_time, tag, no_of_servings, " +
"cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) " +
"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

try (Connection conn = getConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, name != null && !name.isEmpty() ? name : null);
pstmt.setString(2, recipeCategory != null && !recipeCategory.isEmpty() ? recipeCategory : null);
pstmt.setString(3, foodCategory != null && !foodCategory.isEmpty() ? foodCategory : null);
pstmt.setString(4, ingredients != null && !ingredients.isEmpty() ? ingredients : null);
pstmt.setString(5, prepTime != null && !prepTime.isEmpty() ? prepTime : null);
pstmt.setString(6, cookTime != null && !cookTime.isEmpty() ? cookTime : null);
pstmt.setString(7, tag != null && !tag.isEmpty() ? tag : null);

if (servings != null) {
pstmt.setInt(8, servings);
} else {
pstmt.setNull(8, java.sql.Types.INTEGER);
}

pstmt.setString(9, cuisine != null && !cuisine.isEmpty() ? cuisine : null);
pstmt.setString(10, description != null && !description.isEmpty() ? description : null);
pstmt.setString(11, method != null && !method.isEmpty() ? method : null);
pstmt.setString(12, nutrients != null && !nutrients.isEmpty() ? nutrients : null);
pstmt.setString(13, url != null && !url.isEmpty() ? url : null);

pstmt.executeUpdate();
System.out.println("LCHF ADD recipe inserted successfully: " + name);

} catch (SQLException e) {
System.err.println("Error inserting LCHF ADD recipe: " + e.getMessage());
}
}

    
    
}
