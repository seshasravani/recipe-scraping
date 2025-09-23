package RecipeTests;

import java.util.List;

import Utilities.ExcelReader;
import Utilities.jdbc;

public class LFVAdd {
	public static List<String> getAddList() {
        try {
           
            List<String> AddList = ExcelReader.readColumn(
                "src/test/resources/Excel_Data/Ingredientdata.xlsx", 
                "Final list for LFV Elimination ", 
                "Add".trim()
                //
            );
            System.out.println("=== LFV ADD LIST ===");
            for (String item : AddList) {
                System.out.println(item);
            }
            System.out.println("Total items to Add: " + AddList.size());
            return AddList;
        } catch (Exception e) {
            System.out.println("Error reading Add list: " + e.getMessage());
            return null;
        }
 }
        public static boolean shouldAddRecipe(String ingredients) {
            List<String> AddList = getAddList();
            if (AddList == null) return false;
            
            String ingredientsLower = ingredients.toLowerCase();
            for (String Add : AddList) {
                if (ingredientsLower.contains(Add.toLowerCase())) {
                    System.out.println("RecipeIngredient is in LFVAddList: " + Add);
                    return true;
                }
            }
            return false;
        }
        public static void processAndSaveRecipe(String listId, String name, String ingredients, String prepTime, 
                String cookTime, String servings, List<String> tags, String url) {
 if (shouldAddRecipe(ingredients)) {
  System.out.println("This recipe should be Added");

//Save added recipe to database with scraped recipe ID
    Integer servingCount = null;
        try {
       servingCount = Integer.parseInt(servings.replaceAll("[^0-9]", ""));
            } catch (Exception e) {}

//Store scraped recipe ID in recipe_category field for now
    jdbc.insertLFVAddRecipe(name, listId, "Healthy", ingredients, prepTime, cookTime, 
              String.join(", ", tags), servingCount, null, null, 
                                   null, null, url);
                } else {
    System.out.println("This recipe is NOTSAFE to Add");
                }}

	    }
	    

	 
