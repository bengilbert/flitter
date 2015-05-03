package nz.ben.flitter.ui.render;

import nz.ben.flitter.command.CommandResponse;
import nz.ben.flitter.message.Message;
import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private final List<ResponseRenderRule> renderRules = new ArrayList<>();

    public ResponseRenderer() {
        // rules executed in order that they are defined until one is found that is valid or the last rule is run
        renderRules.add(new ResponseRenderRule(deltaSeconds, validWhenZero, justNowRenderer));
        renderRules.add(new ResponseRenderRule(deltaSeconds, validWhenLessThan.apply(60), agoRenderer.apply("second")));
        renderRules.add(new ResponseRenderRule(deltaMinutes, validWhenLessThan.apply(60), agoRenderer.apply("minute")));
        renderRules.add(new ResponseRenderRule(deltaHours, validWhenLessThan.apply(24), agoRenderer.apply("hour")));
        renderRules.add(new ResponseRenderRule(deltaDays, alwaysValid, agoRenderer.apply("day")));
    }

    public String render(CommandResponse commandResponse) {
        String response = "";
        if (!commandResponse.getMessages().isEmpty()) {
            response = commandResponse.getMessages()
                    .stream()
                    .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                    .map(m -> render(commandResponse.hasMessagesForOtherUsers(), m))
                    .collect(Collectors.joining("\n"));
        }
        return response;
    }

    /* package */ String render(boolean renderUserName, Message message) {
        DateTime now = DateTime.now();
        DateTime past = message.getDateTime();

        for (ResponseRenderRule r : renderRules) {
            int delta = r.delta().apply(past, now);

            if (r.ruleApplies().apply(delta)) {
                String response = "";
                if (renderUserName) {
                    response = response + message.getUser().getUserName() + " - ";
                }
                response = response + message.getMessage() + " (" + r.render().apply(delta) + ")";
                return response;
            }
        }

        throw new RuntimeException("Last rule must always be valid.  Please check rule definitions.");
    }

}
