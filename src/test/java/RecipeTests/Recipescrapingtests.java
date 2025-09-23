package RecipeTests;

import Utilities.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import RecipePages.Recipescrapingpages;

public class Recipescrapingtests {
 
  private WebDriver driver;

  @BeforeMethod
  public void setUp() {
  new ConfigReader().init_prop();
    driver = new ChromeDriver();
  }

  @AfterMethod
  public void tearDown() {
    if (driver != null) driver.quit();
  }

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
      
      
      LfvEliminate.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);
      
      LchfEliminate.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);
      
      LFVAdd.processAndSaveRecipe(listId, name, ingredients, prepTime, cookTime, servings, tags, url);

      
      driver.navigate().back();
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
    
    LfvEliminate.printNonEliminatedSummary();
  }
  }



