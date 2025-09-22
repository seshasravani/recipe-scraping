package RecipeTests;

import java.util.List;
import Utilities.ExcelReader;
import Utilities.jdbc;

public class LfvEliminate {
    
    private static int nonEliminatedCount = 0;
    
    public static List<String> getEliminateList() {
        try {
           
            List<String> eliminateList = ExcelReader.readColumn(
                "src/test/resources/Excel_Data/Ingredientdata.xlsx", 
                "Final list for LFV Elimination ", 
                "Eliminate".trim()
                //
            );
            
            System.out.println("=== LFV ELIMINATE LIST ===");
            for (String item : eliminateList) {
                System.out.println(item);
            }
            System.out.println("Total items to eliminate: " + eliminateList.size());
            
            return eliminateList;
        } catch (Exception e) {
            System.out.println("Error reading Eliminate list: " + e.getMessage());
            return null;
        }
    }
    
    public static boolean shouldEliminateRecipe(String ingredients) {
        List<String> eliminateList = getEliminateList();
        if (eliminateList == null) return false;
        
        String ingredientsLower = ingredients.toLowerCase();
        for (String eliminate : eliminateList) {
            if (ingredientsLower.contains(eliminate.toLowerCase())) {
                System.out.println("Recipe eliminated due to: " + eliminate);
                return true;
            }
        }
        return false;
    }
    
    public static void processAndSaveRecipe(String listId, String name, String ingredients, String prepTime, 
                                          String cookTime, String servings, List<String> tags, String url) {
        if (shouldEliminateRecipe(ingredients)) {
            System.out.println("This recipe should be ELIMINATED");
            
            // Save eliminated recipe to database with scraped recipe ID
            Integer servingCount = null;
            try {
                servingCount = Integer.parseInt(servings.replaceAll("[^0-9]", ""));
            } catch (Exception e) {}
            
            // Store scraped recipe ID in recipe_category field for now
            jdbc.insertRecipe(name, listId, "Healthy", ingredients, prepTime, cookTime, 
                             String.join(", ", tags), servingCount, null, null, 
                             null, null, url);
        } else {
            System.out.println("This recipe is SAFE to include");
            
            // Save non-eliminated recipe to lfv_noneliminated_recipes table
            Integer servingCount = null;
            try {
                servingCount = Integer.parseInt(servings.replaceAll("[^0-9]", ""));
            } catch (Exception e) {}
            
            // Store scraped recipe ID in recipe_category field for non-eliminated recipes
            jdbc.insertNonEliminatedRecipe(name, listId, "Healthy", ingredients, prepTime, cookTime, 
                                         String.join(", ", tags), servingCount, null, null, 
                                         null, null, url);
            nonEliminatedCount++;
            System.out.println("Recipe inserted into lfv_noneliminated_recipes table: " + name);
        }
    }
    
    public static void printNonEliminatedSummary() {
        if (nonEliminatedCount == 0) {
            System.out.println("No recipes inserted into lfv_noneliminated_recipes table");
        } else {
            System.out.println(nonEliminatedCount + " recipes inserted into lfv_noneliminated_recipes table");
        }
    }
}
