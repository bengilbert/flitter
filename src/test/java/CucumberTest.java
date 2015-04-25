import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import nz.ben.config.FlitterConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


/**
 * Created by bengilbert on 22/04/15.
 */

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"}, features = {"classpath:features"}, glue = {"classpath:nz.ben.steps"})
public class CucumberTest {
}


