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

    System.out.println("Recipe_ID: " + recipeId);
    System.out.println("Recipe_Name: " + name);
  }
}


