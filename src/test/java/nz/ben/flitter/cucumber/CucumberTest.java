package nz.ben.flitter.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * Created by bengilbert on 22/04/15.
 */

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"}, features = {"classpath:features"}, glue = {"classpath:nz.ben.flitter.cucumber.steps"})
public class CucumberTest {
}


