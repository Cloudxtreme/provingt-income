import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = {"specs/income-proving/family-tm-caseworker-tool/"},
    tags = {}
)
public class SerenityCucumberRunner {
}
