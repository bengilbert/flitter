package nz.ben.flitter.ui.render;

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

    private final BiFunction<DateTime, DateTime, Integer> deltaSeconds = (d1, d2) -> Seconds.secondsBetween(d1, d2).getSeconds();
    private final BiFunction<DateTime, DateTime, Integer> deltaMinutes = (d1, d2) -> Minutes.minutesBetween(d1, d2).getMinutes();
    private final BiFunction<DateTime, DateTime, Integer> deltaHours = (d1, d2) -> Hours.hoursBetween(d1, d2).getHours();
    private final BiFunction<DateTime, DateTime, Integer> deltaDays = (d1, d2) -> Days.daysBetween(d1, d2).getDays();

    private final Function<Integer, Boolean> alwaysValid = x -> true;
    private final Function<Integer, Boolean> validWhenZero = s -> s == 0;
    private final Function<Integer, Function<Integer, Boolean>> validWhenLessThan = x -> y -> y < x;

    private final Function<String, Function<Integer, String>> agoRenderer = u -> x -> x + " " + (x == 1 ? u : u + "s") + " ago";
    private final Function<Integer, String> justNowRenderer = i -> "just now";

    private final List<MessageRenderRule> renderRules = new ArrayList<>();

    public MessageRenderer() {
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
            int delta = r.delta().apply(now, past);

            if (r.compare().apply(delta)) {
                return message.getMessage() + " (" + r.render().apply(delta) + ")";
            }
        }

        throw new RuntimeException("Last rule must always be valid.  Please check rule definitions.");
    }

}
