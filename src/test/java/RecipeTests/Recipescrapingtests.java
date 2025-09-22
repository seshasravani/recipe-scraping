package RecipeTests;

import Utilities.ConfigReader;
import org.testng.Assert;

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

    String ingredients = recipesPage.getIngredients();

    System.out.println("Recipe_ID: " + recipeId);
    System.out.println("Recipe_Name: " + name);
    System.out.println("Ingredients: " + ingredients);
    
    // Check if recipe should be eliminated
    if (LfvEliminate.shouldEliminateRecipe(ingredients)) {
      System.out.println("This recipe should be ELIMINATED");
    } else {
      System.out.println("This recipe is SAFE to include");
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

      String name = recipesPage.getRecipeName();
      String ingredients = recipesPage.getIngredients();

      System.out.println("Recipe_ID: " + listId);
      System.out.println("Recipe_Name: " + name);
      System.out.println("Ingredients: " + ingredients);
      
      if (LfvEliminate.shouldEliminateRecipe(ingredients)) {
        System.out.println("This recipe should be ELIMINATED");
      } else {
        System.out.println("This recipe is SAFE to include");
      }
      
      driver.navigate().back();
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
  }
  }



