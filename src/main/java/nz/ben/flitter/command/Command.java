package nz.ben.flitter.command;

import nz.ben.flitter.user.User;
import nz.ben.flitter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Optional;

/**
 * Created by bengilbert on 25/04/15.
 */
@Configurable
public class Command {

    @Autowired
    private UserRepository userRepository;

    private String userName;
    private CommandType commandType;
    private String commandDetail;

    public Command(final String userName, final CommandType type, final String commandDetail) {
        this.userName = userName;
        this.commandType = type;
        this.commandDetail = commandDetail;
    }

    public User getUser() {
        //TODO there is probably a better way to implement this
        Optional<User> user = userRepository.findByName(userName);
        if (!user.isPresent()) {
            user = Optional.of(userRepository.createUser(userName));
        }
        return user.get();
    }

    public Optional<User> getOtherUser() {
        return userRepository.findByName(commandDetail);
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public String getCommandDetail() {
        return this.commandDetail;
    }

    public CommandResponse execute() {
        CommandResponse commandResponse = new CommandResponse(commandType);
        switch (getCommandType()) {
            case POST:
                getUser().postMessage(getCommandDetail());
                break;
            case VIEW_WALL:
                commandResponse.setMessages(getUser().wall());
                break;
            case VIEW_TIMELINE:
                commandResponse.setMessages(getUser().timeline());
                break;
            case FOLLOW:
                User otherUser = getOtherUser().orElseThrow(IllegalStateException::new);
                getUser().follow(otherUser);
            default:
                break;
        }


        return commandResponse;
    }


}
