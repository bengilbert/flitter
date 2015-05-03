package nz.ben.flitter.ui.render;

import nz.ben.flitter.message.Message;
import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bengilbert on 2/05/15.
 */
@Component
public class ResponseRenderer {

    private final DateDeltaFunction deltaSeconds = (d1, d2) -> Seconds.secondsBetween(d1, d2).getSeconds();
    private final DateDeltaFunction deltaMinutes = (d1, d2) -> Minutes.minutesBetween(d1, d2).getMinutes();
    private final DateDeltaFunction deltaHours = (d1, d2) -> Hours.hoursBetween(d1, d2).getHours();
    private final DateDeltaFunction deltaDays = (d1, d2) -> Days.daysBetween(d1, d2).getDays();

    private final IntComparatorFunction alwaysValid = x -> true;
    private final IntComparatorFunction validWhenZero = s -> s == 0;
    private final Function<Integer, IntComparatorFunction> validWhenLessThan = x -> y -> y < x;

    private final Function<String, RelativeDateRenderFunction> agoRenderer = u -> x -> x + " " + (x == 1 ? u : u + "s") + " ago";
    private final RelativeDateRenderFunction justNowRenderer = i -> "just now";

    private final List<MessageRenderRule> renderRules = new ArrayList<>();

    public ResponseRenderer() {
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
            response = messages
                    .stream()
                    .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                    .map(m -> render(m))
                    .collect(Collectors.joining("\n"));
        }
        return response;
    }

    public String render(Message message) {
        DateTime now = DateTime.now();
        DateTime past = message.getDateTime();

        for (MessageRenderRule r : renderRules) {
            int delta = r.delta().apply(past, now);

            if (r.compare().apply(delta)) {
                return message.getMessage() + " (" + r.render().apply(delta) + ")";
            }
        }

        throw new RuntimeException("Last rule must always be valid.  Please check rule definitions.");
    }

}
