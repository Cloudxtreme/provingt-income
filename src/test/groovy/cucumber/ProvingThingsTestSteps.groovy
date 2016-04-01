package cucumber

import cucumber.api.DataTable
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import net.thucydides.core.annotations.Managed
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.WordUtils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

class PTTestSteps {
    @Managed
    public WebDriver driver;

    private int delay = 500

    def String toCamelCase(String s) {
        String allUpper = StringUtils.remove(WordUtils.capitalizeFully(s), " ")
        String camelCase = allUpper[0].toLowerCase() + allUpper.substring(1)
        camelCase
    }

    def parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        Date date = sdf.parse(dateString)
        date
    }

    def sendKeys(WebElement element, String v) {
        element.clear();
        if (v != null && v.length() != 0) {
            element.sendKeys(v);
        }
    }

    // ---------------------------------------
    // Income Proving Service Case Worker Tool
    // ---------------------------------------

    @Given("^Caseworker is using the Income Proving Service Case Worker Tool\$")
    public void robert_is_using_the_ips_generic_tool() {
        driver.get("http://localhost:8081/income-proving-tool.html");
    }

    @When("^Robert submits a query:\$")
    public void robert_submits_a_query(DataTable arg1) {

        Map<String, String> entries = arg1.asMap(String.class, String.class)

        println driver.currentUrl

        List<String, String> entriesList = arg1.asList(String.class)

        entries.each { k, v ->
            String key = toCamelCase(k)

            if (key.endsWith("Date")) {
                if (v != null && v.length() != 0) {
                    Date parsedDate = parseDate(v)
                    sendKeys(driver.findElement(By.id(key + "Day")), String.valueOf(parsedDate.getAt(Calendar.DAY_OF_MONTH)))
                    sendKeys(driver.findElement(By.id(key + "Month")), String.valueOf(parsedDate.getAt(Calendar.MONTH) + 1))
                    sendKeys(driver.findElement(By.id(key + "Year")), String.valueOf(parsedDate.getAt(Calendar.YEAR)))
                } else {
                    driver.findElement(By.id(key + "Day")).clear()
                    driver.findElement(By.id(key + "Month")).clear()
                    driver.findElement(By.id(key + "Year")).clear()
                }
            } else {
                sendKeys(driver.findElement(By.id(key)), v)
            }
        }
        driver.findElement(By.className("button")).click();

    }

    @When("^the NINO is NOT entered:\$")
    public void the_nino_is_not_entered(DataTable arg1) {

        driver.sleep(delay)

        Map<String, String> entries = arg1.asMap(String.class, String.class)

        String applicationReceivedDate = entries.get("Application Received Date")

        String[] date = applicationReceivedDate.split("/")

        driver.findElement(By.id("nino")).sendKeys(entries.get("NINO"))

        driver.findElement(By.id("fromDateDay")).clear()
        driver.findElement(By.id("fromDateDay")).sendKeys(date[0])

        driver.findElement(By.id("fromDateMonth")).clear()
        driver.findElement(By.id("fromDateMonth")).sendKeys(date[1])

        driver.findElement(By.id("fromDateYear")).clear()
        driver.findElement(By.id("fromDateYear")).sendKeys(date[2])

        driver.findElement(By.className("button")).click();
    }

    @When("^an incorrect NINO is entered:\$")
    public void an_incorrect_nino_is_eneterd(DataTable arg1) {

        driver.sleep(delay)

        Map<String, String> entries = arg1.asMap(String.class, String.class)

        String applicationReceivedDate = entries.get("Application Received Date")

        String[] date = applicationReceivedDate.split("/")

        driver.findElement(By.id("nino")).sendKeys(entries.get("NINO"))

        driver.findElement(By.id("fromDateDay")).clear()
        driver.findElement(By.id("fromDateDay")).sendKeys(date[0])

        driver.findElement(By.id("fromDateMonth")).clear()
        driver.findElement(By.id("fromDateMonth")).sendKeys(date[1])

        driver.findElement(By.id("fromDateYear")).clear()
        driver.findElement(By.id("fromDateYear")).sendKeys(date[2])

        driver.findElement(By.className("button")).click();
    }

    @When("^Application Received Date is not entered:\$")
    public void application_received_date_not_entered(DataTable arg1) {

        driver.sleep(delay)

        Map<String, String> entries = arg1.asMap(String.class, String.class)

        String applicationReceivedDate = entries.get("Application Received Date")

        String[] date = applicationReceivedDate.split("/")

        driver.findElement(By.id("nino")).sendKeys(entries.get("NINO"))

        driver.findElement(By.id("fromDateDay")).clear()
        driver.findElement(By.id("fromDateDay")).sendKeys(date[0])

        driver.findElement(By.id("fromDateMonth")).clear()
        driver.findElement(By.id("fromDateMonth")).sendKeys(date[1])

        driver.findElement(By.id("fromDateYear")).clear()
        driver.findElement(By.id("fromDateYear")).sendKeys(date[2])

        driver.findElement(By.className("button")).click();
    }

    @When("^an incorrect Application Received Date is entered:\$")
    public void an_incorrent_received_date_is_entered(DataTable arg1) {

        driver.sleep(delay)

        Map<String, String> entries = arg1.asMap(String.class, String.class)

        String applicationReceivedDate = entries.get("Application Received Date")

        String[] date = applicationReceivedDate.split("/")

        driver.findElement(By.id("nino")).sendKeys(entries.get("NINO"))

        driver.findElement(By.id("fromDateDay")).clear()
        driver.findElement(By.id("fromDateDay")).sendKeys(date[0])

        driver.findElement(By.id("fromDateMonth")).clear()
        driver.findElement(By.id("fromDateMonth")).sendKeys(date[1])

        driver.findElement(By.id("fromDateYear")).clear()
        driver.findElement(By.id("fromDateYear")).sendKeys(date[2])

        driver.findElement(By.className("button")).click();
    }

    @Then("^The service provides the following result:\$")
    public void the_service_provides_the_following_results(DataTable expectedResult) {

        for (int i = 0; i < expectedResult.raw().size() - 1; i++) {
            String row = expectedResult.raw.get(i)
            String[] column_data = row.split(",")

            driver.sleep(delay)

            def dateXpath = '//*[@id="page2"]/table/tbody[1]/tr[' + (i + 1) + ']/td[1]'

            assert column_data[0].contains(driver.findElement(By.xpath(dateXpath)).getText())

            def amountXpath = '//*[@id="page2"]/table/tbody[1]/tr[' + (i + 1) + ']/td[3]'

            assert column_data[2].contains(driver.findElement(By.xpath(amountXpath)).getText())
        }

        // Result row
        String result_row = expectedResult.raw.get(expectedResult.raw().size() - 1)
        String[] result_columns = result_row.split(",")

        assert result_columns[0].contains(driver.findElement(By.xpath('//*[@id="page2"]/table/tbody[2]/tr/td[1]/span')).getText())
        assert result_columns[2].contains(driver.findElement(By.xpath('//*[@id="page2"]/table/tbody[2]/tr/td[3]/span')).getText())

    }

    @Then("^The service displays the following message:\$")
    public void the_service_displays_the_following_message(DataTable arg1) {

        if (driver.currentUrl == "http://localhost:8000/income-proving-tool.html") {
            Map<String, String> entries = arg1.asMap(String.class, String.class)

            driver.sleep(delay)

            assert driver.findElement(By.id(entries.get("Error Field"))).getText() == entries.get("Error Message")
        }

        if (driver.currentUrl == "http://localhost:8000/") {
            Map<String, String> entries = arg1.asMap(String.class, String.class)

            driver.sleep(delay)

            assert driver.findElement(By.id(entries.get("Error Field"))).getText() == entries.get("Error Message")
        }
    }

    // ------------------------------
    // IPS Family TM Case Worker Tool
    // ------------------------------
    @Given("^Caseworker is using the IPS Family TM Case Worker Tool\$")
    public void case_worker_using_ips_family_tm_case_worker_tool() {
        driver.get("http://localhost:8000/");
    }

    @When("^Robert submits a query to IPS Family TM Case Worker Tool:\$")
    public void robert_submits_a_query_to_ips_family_tm_case_worker_tool(DataTable arg1) {
        Map<String, String> entries = arg1.asMap(String.class, String.class)

        driver.sleep(delay)

        String applicationReceivedDate = entries.get("Application received date")

        String[] date = applicationReceivedDate.split("/")

        driver.findElement(By.id("nino")).sendKeys(entries.get("NINO"))

        driver.findElement(By.id("fromDateDay")).clear()
        driver.findElement(By.id("fromDateDay")).sendKeys(date[0])

        driver.findElement(By.id("fromDateMonth")).clear()
        driver.findElement(By.id("fromDateMonth")).sendKeys(date[1])

        driver.findElement(By.id("fromDateYear")).clear()
        driver.findElement(By.id("fromDateYear")).sendKeys(date[2])

        entries.get("NINO") + "_" + applicationReceivedDate.replace('/', '-')

        driver.findElement(By.className("button")).click();
    }

    @Then("^The IPS Family TM Case Worker Tool provides the following result:\$")
    public void the_ips_family_tm_case_worker_tool_provides_the_following_results(DataTable expectedResult) {
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

        Map<String, String> entries = expectedResult.asMap(String.class, String.class)
        String[] outcome = entries.keySet()

        // To manually take a screenshot
        // net.serenitybdd.core.Serenity.takeScreenshot()

        for (String s : outcome) {
            if (s != "Outcome") {
                String elementId = toCamelCase(s)
                WebElement element = driver.findElement(By.id(elementId))
                String elementText = element.getText()
                String compareTo = entries.get(s)
                assert element.getText().contains(entries.get(s))
            } else {
                String elementId = toCamelCase(s)
                WebElement element = driver.findElement(By.id(elementId))
                String cssValue = element.getAttribute("class")
                assert cssValue.contains("panel-fail") == false
            }
        }
    }

}