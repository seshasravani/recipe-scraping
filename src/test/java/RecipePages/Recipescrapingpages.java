package RecipePages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.ConfigReader;

import java.time.Duration;
import java.util.List;

public class Recipescrapingpages {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public Recipescrapingpages(WebDriver driver) {
    this.driver = driver;
    this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
  }

  private static final By TILE_LINK =
      By.cssSelector("a[href*='/recipes-for-']");                
  //By FIRST_TILE = By.xpath("//a[@href='/recipes-for-Vitamin-B12-Cobalamin-Rich-Foods-1152']"); only if B12 is always the first tile


  private static final By CARD_TITLE_LINK =
      By.cssSelector("span.rcc_recipename a[href]");   
  //By FIRST_RECIPE_CARD = By.xpath("//a[@href='/paneer-masala-2404r']"); only works if paneer masala is always the first recipe card


  private static final By CARD_ALT_LINK =
      By.cssSelector("div.recipe-description a[href]");     
  //if in the recipe card title is not working or absent, we can click more

  private static final By FIRST_RECIPE_ID_ON_PAGE =
      By.xpath("(//*[contains(text(),'Recipe#')])[1]"); //1 means we are selecting the first recipe#
  //By.xpath("//p[@class='recipe-count' and normalize-space()='Recipe# 2690']");


  private static final By DETAIL_RECIPE_NAME =
		    By.cssSelector("h1.rec-heading span");
  //By.xpath("//h1[@class='rec-heading']/span[contains(text(),'paneer masala recipe')]");
  
  private static final By PREP_TIME =
		    By.xpath("//h6[normalize-space()='Preparation Time']/following-sibling::p[1]");

  private static final By COOK_TIME =
		    By.xpath("//h6[normalize-space()='Cooking Time']/following-sibling::p[1]");

  private static final By SERVINGS =
		    By.xpath("//h6[normalize-space()='Makes']/following-sibling::p[1]");

  private static final By TAG_LINKS =
		    By.xpath("//h5[normalize-space()='Tags']/following::div[1]//a");
 
  private static final By INGREDIENTS = 
		  By.xpath("//div[@class='rcp_ing']//li | //span[@itemprop='recipeIngredient'] | //div[contains(@class,'ingredient')]//span");

  public void openHome() {
    driver.get(ConfigReader.getProperty("url").trim());
    pageReady(); // waits + cleans ad overlays
  }

  public void openHealthyCategoryDirect() {
    if (!driver.getCurrentUrl().contains("/category/Healthy-Indian-Recipes")) {
      driver.navigate().to(HEALTHY_URL);
    }
    pageReady();
    wait.until(ExpectedConditions.or(
        ExpectedConditions.urlContains("/category/Healthy-Indian-Recipes"),
        ExpectedConditions.presenceOfElementLocated(TILE_LINK),
        ExpectedConditions.presenceOfElementLocated(CARD_TITLE_LINK),
        ExpectedConditions.presenceOfElementLocated(CARD_ALT_LINK)
    ));
  }

  public void openFirstTileFromHealthyCategory() {
    boolean onList = driver.getCurrentUrl().contains("/recipes-for-")
        || !driver.findElements(CARD_TITLE_LINK).isEmpty()
        || !driver.findElements(CARD_ALT_LINK).isEmpty();

    if (!onList) {
      WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(TILE_LINK));
      driver.navigate().to(absolutize(link.getAttribute("href")));
      pageReady();
    }

    wait.until(ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("span.rcc_recipename a, div.recipe-description a")));
  }

  /*
  public String getFirstListRecipeId() {
    try {
      WebElement idEl = wait.until(
          ExpectedConditions.presenceOfElementLocated(FIRST_RECIPE_ID_ON_PAGE));
      String raw = idEl.getText();
      return raw == null ? "" : raw.replaceAll("\\D+", "");
    } catch (Exception e) {
      return "";
    }
  }

  public void openFirstRecipeFromList() {
    WebElement link = firstCardLink();
    driver.navigate().to(absolutize(link.getAttribute("href")));
    pageReady();
    wait.until(ExpectedConditions.presenceOfElementLocated(DETAIL_RECIPE_NAME));
  }

  */

  
  public String getRecipeUrl() {
	  try {
	    String url = driver.getCurrentUrl();    
	    int hash = url.indexOf('#');
	    return (hash > -1 ? url.substring(0, hash) : url).trim();
	  } catch (Exception e) {
	    return "";
	  }
	}

  public String getRecipeName() {
    try { return driver.findElement(DETAIL_RECIPE_NAME).getText().trim(); }
    catch (Exception e) { return ""; }
  }

  public String getPreparationTime() {
	  try {
	    return wait.until(ExpectedConditions
	            .visibilityOfElementLocated(PREP_TIME))
	        .getText().trim();
	  } catch (Exception e) {
	    return "";
	  }
	}
  
  public String getCookingTime() {
	  try {
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(COOK_TIME))
	              .getText().trim();
	  } catch (Exception e) {
	    return "";
	  }
	}
  
  public String getServingsText() {
	  try {
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(SERVINGS))
	              .getText().trim();      
	  } catch (Exception e) {
	    return "";
	  }
	}
  
  public java.util.List<String> getTags() {
	  java.util.List<WebElement> links = driver.findElements(TAG_LINKS);
	  java.util.List<String> out = new java.util.ArrayList<>();
	  for (WebElement a : links) {
	    String t = a.getText().trim();
	    if (!t.isEmpty()) out.add(t);
	  }
	  return out;
	}
  public String getIngredients() {
    try { 
      List<WebElement> ingredientList = driver.findElements(INGREDIENTS);
      String allIngredients = "";
      for (WebElement ingredient : ingredientList) {
        allIngredients = allIngredients + ingredient.getText() + "; ";
      }
      return allIngredients;
    }
    catch (Exception e) { 
      return ""; 
    }
  }

  public int getTotalCardCount() {
    List<WebElement> titleCards = driver.findElements(CARD_TITLE_LINK);
    List<WebElement> altCards = driver.findElements(CARD_ALT_LINK);
    return Math.max(titleCards.size(), altCards.size());
  }

  public void openRecipeAtIndex(int index) {
    List<WebElement> titleCards = driver.findElements(CARD_TITLE_LINK);
    List<WebElement> altCards = driver.findElements(CARD_ALT_LINK);
    
    WebElement cardToClick = titleCards.size() > index ? titleCards.get(index) : altCards.get(index);
    driver.navigate().to(absolutize(cardToClick.getAttribute("href")));
    pageReady();
  }

  public String getRecipeIdAtIndex(int index) {
    List<WebElement> ids = driver.findElements(By.xpath("//*[contains(text(),'Recipe#')]"));
    if (index < ids.size()) {
      return ids.get(index).getText().replaceAll("\\D+", "");
    }
    return "";
  }


  /*

  private WebElement firstCardLink() {
    List<WebElement> titleLinks = driver.findElements(CARD_TITLE_LINK);
    if (!titleLinks.isEmpty()) return titleLinks.get(0);
    return wait.until(ExpectedConditions.presenceOfElementLocated(CARD_ALT_LINK));
  }
  */

  private void pageReady() {
    try {
      new WebDriverWait(driver, Duration.ofSeconds(10))
          .until(d -> "complete".equals(
              ((JavascriptExecutor)d).executeScript("return document.readyState")));
    } catch (Exception ignore) {}
    handleAdsIfPresent();   
  }

  private String absolutize(String href) {
    if (href == null || href.isBlank()) return "";
    if (href.startsWith("http")) return href;
    if (!href.startsWith("/")) href = "/" + href;
    return "https://www.tarladalal.com" + href;
  }

  private void handleAdsIfPresent() {
    try {
      ((JavascriptExecutor)driver).executeScript(
        "(()=>{const H=s=>document.querySelectorAll(s).forEach(e=>{e.style.display='none';e.style.visibility='hidden';e.style.pointerEvents='none';});" +
        "H(\"iframe[id*='aswift' i],iframe[id*='google' i],iframe[src*='ads' i],iframe[src*='doubleclick' i]\");" +
        "H(\"div[id^='google_ads_iframe'],div[class*='adsbygoogle' i]\");" +
        "if(/google[-_]vignette/i.test(location.hash)) location.replace(location.href.replace(/#.*$/,''));" +
        "})()"
      );
    } catch (Exception ignored) {}
  }

  private static final String HEALTHY_URL =
      "https://www.tarladalal.com/category/Healthy-Indian-Recipes/";
      
  // private static final String HEALTHY_URL =
  //     "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=S&recipetype=Salad";
}




