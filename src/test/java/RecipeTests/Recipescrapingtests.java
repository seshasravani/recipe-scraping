package RecipeTests;

import Utilities.ConfigReader;
import org.testng.Assert;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import RecipePages.Recipescrapingpages;

public class Recipescrapingtests {
 
  private WebDriver driver;

  //runs before each test
  @BeforeMethod
  public void setUp() {
  new ConfigReader().init_prop();
    driver = new ChromeDriver();
  }

  @AfterMethod
  public void tearDown() {
    if (driver != null) driver.quit();
  }

  /*
  @Test
  public void openFirstRecipeCard() {
    Recipescrapingpages recipesPage = new Recipescrapingpages(driver);

    recipesPage.openHome();
    recipesPage.openHealthyCategoryDirect();
    recipesPage.openFirstTileFromHealthyCategory();

    String listId = recipesPage.getFirstListRecipeId();   
    recipesPage.openFirstRecipeFromList();

    String name     = recipesPage.getRecipeName();
    Assert.assertTrue(name != null && !name.isBlank(), "Recipe_Name should not be null/blank");
       
    String recipeId = listId;
    Assert.assertTrue(recipeId != null && !recipeId.isBlank(), "Recipe_ID should not be null/blank");
    
    String prepTime = recipesPage.getPreparationTime();
    Assert.assertTrue(prepTime != null && !prepTime.isBlank(), "Preparation_Time should not be null/blank");
    
    String cookTime = recipesPage.getCookingTime();
    Assert.assertTrue(cookTime != null && !cookTime.isBlank(), "Cooking_Time should not be null/blank");
    
    String servings = recipesPage.getServingsText();
    Assert.assertTrue(servings != null && !servings.isBlank(), "Servings should not be null/blank");
    
    List<String> tags = recipesPage.getTags();
    Assert.assertTrue(!tags.isEmpty(), "Tags should not be empty");
    
    String recipeUrl = recipesPage.getRecipeUrl();
    Assert.assertTrue(recipeUrl != null && !recipeUrl.isBlank(), "Recipe_URL should not be null/blank");

    String ingredients = recipesPage.getIngredients();

    System.out.println("Recipe_ID: " + recipeId);
    System.out.println("Recipe_Name: " + name);
    
    System.out.println("Preparation_Time: " + prepTime);
    System.out.println("Cooking_Time: " + cookTime);
    System.out.println("Servings: " + servings);
    System.out.println("Tags: " + String.join(", ", tags));
    System.out.println("Recipe_URL: " + recipeUrl);

    System.out.println("Ingredients: " + ingredients);
    
    // Check if recipe should be eliminated
    if (LfvEliminate.shouldEliminateRecipe(ingredients)) {
      System.out.println("This recipe should be ELIMINATED");
    } else {
      System.out.println("This recipe is SAFE to include");
    }
    
 // Check if recipe should be Added
    if (LFVAdd.shouldAddRecipe(ingredients)) {
        System.out.println("This recipe should be ADDED");
      } else {
        System.out.println("This recipe is NOTSAFE to include");
      }
    
    
       // Keep browser open for 5 minutes
     try {
         Thread.sleep(300000);
    } catch (InterruptedException e) {}
   }
   */

  @Test
  public void processAllRecipeCards() {
    Recipescrapingpages recipesPage = new Recipescrapingpages(driver);

    recipesPage.openHome();
    recipesPage.openHealthyCategoryDirect();
    recipesPage.openFirstTileFromHealthyCategory();

    int totalCards = recipesPage.getTotalCardCount();
    System.out.println("Found " + totalCards + " recipe cards to process");
    
    for (int i = 0; i < totalCards; i++) {
      System.out.println("\n--- Processing Card " + (i + 1) + " of " + totalCards + " ---");
      
      String listId = recipesPage.getRecipeIdAtIndex(i);
      recipesPage.openRecipeAtIndex(i);

      String url = recipesPage.getRecipeUrl();
      String name = recipesPage.getRecipeName();
      String prepTime = recipesPage.getPreparationTime();
      String cookTime = recipesPage.getCookingTime();
      String servings = recipesPage.getServingsText();
      java.util.List<String> tags = recipesPage.getTags();
      String ingredients = recipesPage.getIngredients();

      System.out.println("Recipe_ID: " + listId);
      System.out.println("Recipe_URL: " + url);
      System.out.println("Recipe_Name: " + name);
      System.out.println("Prep_Time: " + prepTime);
      System.out.println("Cook_Time: " + cookTime);
      System.out.println("Servings: " + servings);
      System.out.println("Tags: " + tags);
      System.out.println("Ingredients: " + ingredients);
      
      // Process recipe and save if eliminated
      LfvEliminate.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);
      
      LchfEliminate.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);
      
      LFVAdd.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);
      
      LchfAdd.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);


      
      driver.navigate().back();
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
    
    // Print summary of non-eliminated recipes
    LfvEliminate.printNonEliminatedSummary();
  }
  }



