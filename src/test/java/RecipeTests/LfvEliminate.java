package RecipeTests;

import java.util.List;
import Utilities.ExcelReader;

public class LfvEliminate {
    
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
}
