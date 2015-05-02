package nz.ben.flitter.user;

import nz.ben.flitter.config.FlitterConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by bengilbert on 2/05/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlitterConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        userService.reset();
    }

    @Test
    public void testFindByName_nameDoesntExist_returnsEmptyOptional() throws Exception {

        Optional<User> user = userService.findByName("unknown");
        assertThat(user.isPresent(), is(false));
    }

    @Test
    public void testFindByName_nameExist_returnsPopulatedOptional() throws Exception {
        userService.createUser("alice");

        Optional<User> user = userService.findByName("alice");
        assertThat(user.isPresent(), is(true));
    }

    @Test
    public void testCreateUser_nameDoesntExist_createsUser() throws Exception {
        User user = userService.createUser("alice");
        assertThat(user, is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateUser_nameExist_doesntCreateUser() throws Exception {
        userService.createUser("alice");
        userService.createUser("alice");
    }
}