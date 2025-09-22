package RecipeTests;

import java.util.List;

import Utilities.ExcelReader;

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
	                    System.out.println("Recipe Added: " + Add);
	                    return true;
	                }
	            }
	            return false;
	        }
	    }
	    

	 
