package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static Enum.tabText.JUST_ME;
import static Enum.tabText.WITH_OTHERS;
import static Pages.GlobalHelpers.driver;

public class PoplarPage {

    @FindBy(xpath = "//div[@class='general_details__price_wrapper']//*[@class='current_price']")
    WebElement currentPrice;

    @FindBy(xpath = "(//*[text()=\"Find out if you\'re prequalified\"])[1]")
    public WebElement findIfQualified;

    @FindBy(xpath = "(//*[text()='Just Me'])[1]")
    public WebElement justMe;

    @FindBy(xpath = "(//*[text()='With Others'])[1]")
    public WebElement withOthers;

    @FindBy(xpath = "(//*[@placeholder='Enter Monthly Income'])[1]")
    public WebElement monthlyIncome;

    @FindBy(xpath = "(//*[@placeholder='Enter Credit Score'])[1]")
    public WebElement creditScore;

    @FindBy(xpath = "(//button[text()='Find Out'])[1]")
    public WebElement findOut;

    @FindBy(xpath = "//*[text()='Add Member']")
    public WebElement addMember;

    @FindBy(xpath = "//div[@class='results-info']/p[1]")
    public WebElement incomeResult;

    @FindBy(xpath = "//div[@class='results-info']/p[2]")
    public WebElement creditScoreResult;

    public WebDriverWait wait = new WebDriverWait(driver, 10);


    public String getcurrentPrice() {
        wait.until(ExpectedConditions.visibilityOf(currentPrice));
        String price = currentPrice.getText().replace("$", "");
        return price.replace(",", "");
    }

    public void setMonthlyIncome(String value) {
        monthlyIncome.sendKeys(value);
    }

    public void setcreditScore(String value) {
        creditScore.sendKeys(value);
    }

    public void setIncomeAndCreditScore(String income, String creditScore) {
        setMonthlyIncome(income);
        setcreditScore(creditScore);
    }

    public void justMeOrWithOthers(String val) {
        if (val.equals(JUST_ME.gettext())) {
            justMe.click();
        }
        if (val.equals(WITH_OTHERS.gettext())) {
            withOthers.click();
        }
    }

    public void submitForm(String value, String income, String creditScore) {
        justMeOrWithOthers(value);
        setIncomeAndCreditScore(income, creditScore);
        if(creditScore.isEmpty()||income.isEmpty())
        {
            Assert.assertFalse(findOut.isEnabled());
            return;
        }
        findOut.click();
    }

    public void submitForm(String value, String[] multiplier, String[] creditScore) {
        String price = getcurrentPrice();
        String income;
        findIfQualified.click();
        justMeOrWithOthers(value);
        for (int i = 0; i < multiplier.length; i++) {
            income = String.valueOf(Double.parseDouble(multiplier[i]) * Double.parseDouble(price));
            setIncomeAndCreditScore(income, creditScore[i]);
            if(creditScore[i].isEmpty())
            {
                Assert.assertFalse(findOut.isEnabled());
            }
            if (i != multiplier.length-1) {
                addMember.click();
            } else {
                findOut.click();
            }

        }

    }

    public boolean validatePrequalification() {
        return validateResultIncome() && validateCreditScore();
    }

    public boolean validateResultIncome() {

        double multiplier = Double.parseDouble(incomeResult.getText().replace("x the monthly rent", ""));
        boolean checkMark;
        if (multiplier >= 2.5) {
            wait.until(ExpectedConditions.visibilityOf(incomeResult.findElement(By.xpath("//img[contains(@src,'check-mark')]"))));
            checkMark = incomeResult.findElement(By.xpath("//img[contains(@src,'check-mark')]")).isDisplayed();
        } else {
            checkMark = false;
        }

        return multiplier >= 2.5 && checkMark;
    }

    public boolean validateCreditScore() {

        int score = Integer.parseInt(creditScoreResult.getText().replace(" Credit Score", ""));
        boolean checkMark = creditScoreResult.findElement(By.xpath("//img[contains(@src,'check-mark')]")).isDisplayed();
        return score >= 650 && checkMark;
    }
}

