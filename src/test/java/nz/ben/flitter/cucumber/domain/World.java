package nz.ben.flitter.cucumber.domain;

import org.springframework.stereotype.Service;

/**
 * Created by bengilbert on 2/05/15.
 */

@Service
public class World {

    private ShadowUser user;
    private ShadowUser otherUser;

    public ShadowUser getShadowUser() {
        if (user == null) {
            user = new ShadowUser("Default-user-name");
        }
        return user;
    }

    public ShadowUser getOtherShadowUser() {
        if (otherUser == null) {
            otherUser = new ShadowUser("Default-other-user-name");
        }
        return otherUser;
    }

    public void setShadowUser(final ShadowUser user) {
        this.user = user;
    }

    public void setOtherShadowUser(final ShadowUser otherUser) {
        this.otherUser = otherUser;
    }
}
