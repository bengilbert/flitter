package nz.ben.flitter.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by bengilbert on 2/05/15.
 */
@Service
public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    public Optional<User> findByName(final String name) {

        if (name == null || name.isEmpty() || !users.containsKey(name.toLowerCase())) {
            return Optional.empty();
        }

        return Optional.of(users.get(name.toLowerCase()));
    }


    public User createUser(final String name) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("invalid user name " + name);
        }

        if (users.containsKey(name.toLowerCase())) {
            throw new RuntimeException("Cannot create user " + name + " as user already exists");
        }

        User user = new User(name);
        users.put(name.toLowerCase(), user);
        return user;
    }

    public void reset() {
        users.clear();
    }


}
