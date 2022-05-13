import Pages.GlobalHelpers;
import Pages.PoplarPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static Driver.Setup.*;
import static Pages.GlobalHelpers.driver;

public class test extends base {

    @BeforeMethod
    public void setupClass() throws IOException, InterruptedException {
        getDriver(getBrowser());
        helpers = new GlobalHelpers();
        poplarPage = PageFactory.initElements(driver, PoplarPage.class);
    }

    @AfterMethod
    public void after() {
        killDriver();
    }

    @Test(dataProvider = "NegativeTesting")
    public void TC1(String value, String multiplier, String creditScore) {
        String price = poplarPage.getcurrentPrice();
        String income = "";
        poplarPage.findIfQualified.click();
        if(multiplier.equals("x"))
        {
            multiplier = null;
        }
        try
        {
            if(multiplier != null && !multiplier.isEmpty())
            {
                income = String.valueOf(Double.parseDouble(multiplier) * Double.parseDouble(price));
            }

        }
        catch (NumberFormatException e)
        {

        }

        poplarPage.submitForm(value, income, creditScore);
    }

    /*"Included Scenarios:" +
    "Just Me"
    "Verify if user is prequalified if income is more than 2.5x of the monthly rent and credit score is above 650.
    "Verify if user is prequalified if income is 2.5x of the monthly rent and credit score is 650.
    "Verify if user is not prequalified if income is 2.5x of the monthly rent and credit score is below 650.
    "Verify if user is not prequalified if income is not more than 2.5x of the monthly rent and credit score is 650.
    "Verify if user is not prequalified if income is not more than 2.5x of the monthly rent and credit score is below 650."*/
    @Test(dataProvider = "JustMeData")
    public void TC2(String value, String multiplier, String creditScore, String qualified) {
        String price = poplarPage.getcurrentPrice();
        String income = String.valueOf(Double.parseDouble(multiplier) * Double.parseDouble(price));
        poplarPage.findIfQualified.click();
        poplarPage.submitForm(value, income, creditScore);
        Assert.assertEquals(String.valueOf(poplarPage.validatePrequalification()), qualified);
    }

    /*With Others
    8. Verify if user is prequalified if total income is more than 2.5x of the monthly rent and average credit score is 650 or above.
    9. Verify if user is prequalified if total income is more than 2.5x of the monthly rent and average credit score is 650 or above.
    (Primary member income is less than additional member income AND credit score Primary is less than addition member)
    10. Verify if user is prequalified if total income is more than 2.5x of the monthly rent and average credit score is 650 or above.
    (More than 1 additional member)
    9. Verify if user is not prequalified if total income is 2.5x of the monthly rent and average credit score is below 650.
    10. Verify if user is not prequalified if total income is not more than 2.5x of the monthly rent and average credit score is 650.
    11. Verify if user is not prequalified if total income is not more than 2.5x of the monthly rent and average credit score is below 650.*/
    @Test(dataProvider = "WithOthersData")
    public void TC3(String value, String multiplier, String creditScore, String qualified) throws Exception {
        String[] arrMult = multiplier.split("\\|");
        String[] arrCS = creditScore.split("\\|");
        if (arrMult.length != arrCS.length) {
            throw new Exception("Multiplier and Credit score entries should be equal");
        }
        poplarPage.submitForm(value, arrMult, arrCS);
        Assert.assertEquals(String.valueOf(poplarPage.validatePrequalification()), qualified);
    }


}
