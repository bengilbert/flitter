package nz.ben.flitter.cucumber.steps;

import cucumber.api.java.Before;
import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.message.PostOffice;
import nz.ben.flitter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by bengilbert on 2/05/15.
 */
@ContextConfiguration(classes = FlitterConfig.class)
public class Reset {

    @Autowired
    private UserService userService;

    @Autowired
    private PostOffice postOffice;


    @Before(order = -10000)
    public void reset() {
        userService.reset();
        postOffice.reset();
    }
}
