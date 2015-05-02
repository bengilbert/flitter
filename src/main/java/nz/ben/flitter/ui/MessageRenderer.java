package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by bengilbert on 2/05/15.
 */
@Component
public class MessageRenderer {

    public String render(Collection<Message> messages) {
        String response = "";
        if (!messages.isEmpty()) {
            response = messages
                    .stream()
                    .map(m -> m.getMessage() + " (" + toRelativeDateString(m.getDateTime()) + ")")
                    .collect(Collectors.joining("\n"));
        }
        return response;
    }

    private String toRelativeDateString(DateTime dateTime) {
        DateTime now = DateTime.now();
        int seconds = Seconds.secondsBetween(dateTime, now).getSeconds();
        if (seconds == 0) {
            return "just now";
        }

        if (seconds < 60) {
            return singularOrPlural(seconds, "second");
        }

        int minutes = Minutes.minutesBetween(dateTime, now).getMinutes();
        if (minutes < 60) {
            return singularOrPlural(minutes, "minute");
        }

        int hours = Hours.hoursBetween(dateTime, now).getHours();
        if (hours < 24) {
            return singularOrPlural(hours, "hour");
        }

        int days = Days.daysBetween(dateTime, now).getDays();
        return singularOrPlural(days, "day");
    }

    private String singularOrPlural(int value, String singular) {
        return value + " " + (value == 1 ? singular : singular + "s") + " ago";
    }
}
