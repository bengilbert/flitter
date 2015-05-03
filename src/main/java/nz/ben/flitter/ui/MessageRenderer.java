package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bengilbert on 2/05/15.
 */
@Component
public class MessageRenderer {

    private static final BiFunction<DateTime, DateTime, Integer> deltaSeconds = (d1, d2) -> Seconds.secondsBetween(d1, d2).getSeconds();
    private static final BiFunction<DateTime, DateTime, Integer> deltaMinutes = (d1, d2) -> Minutes.minutesBetween(d1, d2).getMinutes();
    private static final BiFunction<DateTime, DateTime, Integer> deltaHours = (d1, d2) -> Hours.hoursBetween(d1, d2).getHours();
    private static final BiFunction<DateTime, DateTime, Integer> deltaDays = (d1, d2) -> Days.daysBetween(d1, d2).getDays();

    private static final Function<Integer, Boolean> alwaysValid = x -> true;
    private static final Function<Integer, Boolean> validWhenLessThanSixty = s -> s < 60;
    private static final Function<Integer, Boolean> validWhenZero = s -> s == 0;
    private static final Function<Integer, Boolean> validWhenLessThan24 = s -> s < 24;

    private static final List<MessageRenderRule> renderRules = new ArrayList<>();

    static {
        // rules executed in order that they are defined until one is found that validate or the last rule is run
        renderRules.add(new MessageRenderRule(deltaSeconds, validWhenZero, "second"));
        renderRules.add(new MessageRenderRule(deltaSeconds, validWhenLessThanSixty, "second"));
        renderRules.add(new MessageRenderRule(deltaMinutes, validWhenLessThanSixty, "minute"));
        renderRules.add(new MessageRenderRule(deltaHours, validWhenLessThan24, "hour"));
        renderRules.add(new MessageRenderRule(deltaDays, alwaysValid, "day"));
    }

    public String render(Collection<Message> messages) {
        String response = "";
        if (!messages.isEmpty()) {
            response = messages.stream().map(m -> render(m)).collect(Collectors.joining("\n"));
        }
        return response;
    }

    private String render(Message message) {
        DateTime now = DateTime.now();
        DateTime past = message.getDateTime();

        // loop through rules
        for (MessageRenderRule r : renderRules) {
            if (r.passes(now, past)) {
                return message.getMessage() + " (" + renderRelativeDateTimeString(r.getDelta(now, past), r.getSingletonDisplayUnit()) + ")";
            }
        }

        throw new RuntimeException("Last rule must always be valid.  Please check rule definition.");
    }

    private String renderRelativeDateTimeString(int unitValue, String singularUnit) {
        return unitValue + " " + (unitValue == 1 ? singularUnit : singularUnit + "s") + " ago";
    }
}
