package nz.ben.flitter.cucumber.steps;

import cucumber.api.java.Before;
import nz.ben.flitter.config.FlitterConfig;
import nz.ben.flitter.message.PostOffice;
import nz.ben.flitter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by bengilbert on 2/05/15.
 */
@ContextConfiguration(classes = FlitterConfig.class)
public class Reset {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostOffice postOffice;


    @Before(order = -10000)
    public void reset() {
        userRepository.reset();
        postOffice.reset();
    }
}
