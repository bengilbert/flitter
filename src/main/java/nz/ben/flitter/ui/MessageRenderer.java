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
    private static final Function<Integer, Boolean> validWhenZero = s -> s == 0;
    private static final Function<Integer, Function<Integer, Boolean>> validWhenLessThan = x -> y -> y < x;

    private static final Function<String, Function<Integer, String>> agoRenderer = u -> x -> x + " " + (x == 1 ? u : u + "s") + " ago";
    private static final Function<Integer, String> justNowRenderer = i -> "just now";

    private static final List<MessageRenderRule> renderRules = new ArrayList<>();

    static {
        // rules executed in order that they are defined until one is found that is valid or the last rule is run
        renderRules.add(new MessageRenderRule(deltaSeconds, validWhenZero, justNowRenderer));
        renderRules.add(new MessageRenderRule(deltaSeconds, validWhenLessThan.apply(60), agoRenderer.apply("second")));
        renderRules.add(new MessageRenderRule(deltaMinutes, validWhenLessThan.apply(60), agoRenderer.apply("minute")));
        renderRules.add(new MessageRenderRule(deltaHours, validWhenLessThan.apply(24), agoRenderer.apply("hour")));
        renderRules.add(new MessageRenderRule(deltaDays, alwaysValid, agoRenderer.apply("day")));
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
        
        for (MessageRenderRule r : renderRules) {
            if (r.passes(now, past)) {
                return message.getMessage() + " (" + r.getRenderer().apply(r.getDelta(now, past)) + ")";
            }
        }

        throw new RuntimeException("Last rule must always be valid.  Please check rule definitions.");
    }

}
