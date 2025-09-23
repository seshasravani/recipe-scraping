package RecipeTests;

import java.util.List;
import Utilities.ExcelReader;
import Utilities.jdbc;

public class LchfAdd {

    // 1. Read Add list from Excel
    public static List<String> getAddList() {
        try {
            List<String> addList = ExcelReader.readColumn(
                "src/test/resources/Excel_Data/Ingredientdata.xlsx",
                "Final list for LCHFElimination ", // same sheet as Eliminate
                "Add".trim()
            );

            System.out.println("=== LCHF ADD LIST ===");
            for (String item : addList) {
                System.out.println(item);
            }
            System.out.println("Total items to ADD (LCHF): " + addList.size());

            return addList;
        } catch (Exception e) {
            System.out.println("Error reading LCHF Add list: " + e.getMessage());
            return null;
        }
    }

    // 2. Check if recipe qualifies for ADD
    public static boolean shouldAddRecipe(String ingredients) {
        List<String> addList = getAddList();
        if (addList == null) return false;

        String ingredientsLower = ingredients.toLowerCase();
        for (String add : addList) {
            if (ingredientsLower.contains(add.toLowerCase())) {
                System.out.println("Recipe qualifies for ADD (LCHF): " + add);
                return true;
            }
        }
        return false;
    }

    // 3. Save recipe to DB if it's ADD
    public static void processAndSaveRecipe(String listId, String name, String ingredients,
                                            String prepTime, String cookTime, String servings,
                                            List<String> tags, String url) {
        if (shouldAddRecipe(ingredients)) {
            System.out.println("This recipe will be ADDED to LCHF");

            // Convert servings to Integer
            Integer servingCount = null;
            try {
                servingCount = Integer.parseInt(servings.replaceAll("[^0-9]", ""));
            } catch (Exception e) {}

            // Insert into DB (new table `lchf_add_recipes`)
            jdbc.insertLCHFAddRecipe(
                name, listId, "LCHF", ingredients, prepTime, cookTime,
                String.join(", ", tags), servingCount, null, null,
                null, null, url
            );
        } else {
            System.out.println("This recipe does NOT qualify for LCHF Add");
        }
    }
}
