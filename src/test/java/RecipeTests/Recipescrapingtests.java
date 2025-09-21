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

    System.out.println("Recipe_ID: " + recipeId);
    System.out.println("Recipe_Name: " + name);
    System.out.println("Preparation_Time: " + prepTime);
    System.out.println("Cooking_Time: " + cookTime);
    System.out.println("Servings: " + servings);
    System.out.println("Tags: " + String.join(", ", tags));
    System.out.println("Recipe_URL: " + recipeUrl);
  }
}


