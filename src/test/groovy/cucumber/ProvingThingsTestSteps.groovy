package cucumber

import cucumber.api.DataTable
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
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
    def boolean outcome

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

    // ------------------------------
    // IPS Family TM Case Worker Tool
    // ------------------------------
    @When("^Client submits a query to IPS Family TM Case Worker Tool:\$")
    public void robert_submits_a_query_to_ips_family_tm_case_worker_tool(DataTable arg1) {
        Map<String, String> entries = arg1.asMap(String.class, String.class)

        String applicationReceivedDate = entries.get("Application received date")

        entries.get("NINO") + "_" + applicationReceivedDate.replace('/', '-')

        def client = new RESTClient('http://localhost:9080/')
        client.setContentType(ContentType.JSON)
        def resp = client.get(
                path: 'application',
                queryString: 'nino=' + entries.get("NINO") + "&applicationReceivedDate=" + applicationReceivedDate);

        assert resp.status == 200
        String result = resp.getData()
        outcome = result.contains("true")
    }

    @Then("^The IPS API provides the following result:\$")
    public void the_ips_family_tm_case_worker_tool_provides_the_following_results(DataTable expectedResult) {
        assert outcome
    }
}