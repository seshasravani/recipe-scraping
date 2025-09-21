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

  public String getRecipeName() {
    try { return driver.findElement(DETAIL_RECIPE_NAME).getText().trim(); }
    catch (Exception e) { return ""; }
  }


  private WebElement firstCardLink() {
    List<WebElement> titleLinks = driver.findElements(CARD_TITLE_LINK);
    if (!titleLinks.isEmpty()) return titleLinks.get(0);
    return wait.until(ExpectedConditions.presenceOfElementLocated(CARD_ALT_LINK));
  }

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
}




//package RecipePages;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import Utilities.ConfigReader;
//
//import java.time.Duration;
//import java.util.List;
//
//public class Recipescrapingpages {
//
//  private static final String HEALTHY_URL =
//      "https://www.tarladalal.com/category/Healthy-Indian-Recipes/";
//
//  private final WebDriver driver;
//  private final WebDriverWait wait;
//
//  public Recipescrapingpages(WebDriver driver) {
//    this.driver = driver;
//    this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
//  }
//
//  // ---- basic flow ----
//  public void openHome() {
//    //driver.get("https://www.tarladalal.com/");
//	String start = ConfigReader.getProperty("url");   
//	driver.get(start.trim());
//    pageReady();
//    handleAdsIfPresent();
//  }
//
//  public void openHealthyCategoryDirect() {
//    driver.navigate().to(HEALTHY_URL);
//    pageReady();
//    handleAdsIfPresent();
//    wait.until(ExpectedConditions.or(
//        ExpectedConditions.urlContains("/category/Healthy-Indian-Recipes"),
//        ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/recipes-for-']")),
//        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.recipe-count"))
//    ));
//  }
//
//  /** If still on the tile grid, hop into the first sub-list. */
//  public void openFirstTileFromHealthyCategory() {
//    boolean onList = driver.getCurrentUrl().contains("/recipes-for-")
//        || !driver.findElements(By.cssSelector("p.recipe-count")).isEmpty()
//        || !driver.findElements(By.cssSelector("span.rcc_recipename a")).isEmpty();
//
//    if (!onList) {
//      WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(
//          By.cssSelector("a[href*='/recipes-for-']")));
//      driver.navigate().to(absolutize(link.getAttribute("href")));
//      pageReady();
//      handleAdsIfPresent();
//    }
//
//    wait.until(ExpectedConditions.or(
//        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.recipe-count")),
//        ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.rcc_recipename a"))
//    ));
//  }
//
//  // ---- the two things you want to share ----
//  public String getFirstListRecipeId() {
//    handleAdsIfPresent();
//    WebElement idEl = wait.until(ExpectedConditions.presenceOfElementLocated(
//        By.cssSelector("p.recipe-count")));
//    String raw = idEl.getText();
//    return raw == null ? "" : raw.replaceAll("\\D+", "");
//  }
//
//  public void openFirstRecipeFromList() {
//    handleAdsIfPresent();
//
//    // Prefer the card title link; fallback to the “More..” link.
//    List<WebElement> titleLinks =
//        driver.findElements(By.cssSelector("span.rcc_recipename a[href]"));
//    WebElement link = titleLinks.isEmpty() ? null : titleLinks.get(0);
//
//    if (link == null) {
//      List<WebElement> moreLinks =
//          driver.findElements(By.cssSelector("div.recipe-description a[href]"));
//      link = moreLinks.isEmpty() ? null : moreLinks.get(0);
//    }
//    if (link == null) {
//      link = wait.until(ExpectedConditions.presenceOfElementLocated(
//          By.cssSelector("a[href*='/recipes/']")));
//    }
//
//    driver.navigate().to(absolutize(link.getAttribute("href")));
//    pageReady();
//    handleAdsIfPresent();
//    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1, h2")));
//  }
//
//  public String getRecipeName() {
//    try { return driver.findElement(By.cssSelector("h1, h2")).getText().trim(); }
//    catch (Exception e) { return ""; }
//  }
//
//  public String getRecipeIdDetail() {
//    try {
//      WebElement id = driver.findElement(By.xpath(
//          "//*[contains(normalize-space(),'Recipe#') or contains(normalize-space(),'Recipe #')]"));
//      String raw = id.getText();
//      if (raw != null && !raw.isBlank()) return raw.replaceAll("\\D+","");
//    } catch (Exception ignored) {}
//    try {
//      Object v = ((JavascriptExecutor)driver).executeScript(
//          "var m=(document.body.innerText||'').match(/Recipe#\\s*(\\d+)/i); return m?m[1]:'';");
//      return String.valueOf(v).replaceAll("\\D+","");
//    } catch (Exception ignored) {}
//    return "";
//  }
//
//  // ---- helpers ----
//  private void pageReady() {
//    try {
//      new WebDriverWait(driver, Duration.ofSeconds(10))
//          .until(d -> "complete".equals(((JavascriptExecutor)d)
//              .executeScript("return document.readyState")));
//    } catch (Exception ignore) {}
//  }
//
//  private String absolutize(String href) {
//    if (href == null) return "";
//    if (href.startsWith("http")) return href;
//    String origin = "https://www.tarladalal.com";
//    try { origin = String.valueOf(((JavascriptExecutor) driver).executeScript("return location.origin;")); }
//    catch (Exception ignore) {}
//    return href.startsWith("/") ? origin + href : origin + "/" + href;
//  }
//
//  private void handleAdsIfPresent() {
//    try {
//      ((JavascriptExecutor)driver).executeScript(
//        "(()=>{const H=s=>document.querySelectorAll(s).forEach(e=>{e.style.display='none';e.style.visibility='hidden';e.style.pointerEvents='none';});" +
//        "H(\"iframe[id*='aswift' i],iframe[id*='google' i],iframe[src*='ads' i],iframe[src*='doubleclick' i]\");" +
//        "H(\"div[id^='google_ads_iframe'],div[class*='adsbygoogle' i]\");" +
//        "if(/google[-_]vignette/i.test(location.hash)) location.replace(location.href.replace(/#.*$/,''));" +
//        "})()"
//      );
//    } catch (Exception ignored) {}
//  }
//}
//
//
//
//
//
//
//
//
//
//
//
