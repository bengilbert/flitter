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
            response = messages.stream().map(m -> render(m)).collect(Collectors.joining("\n"));
        }
        return response;
    }

    private String render(Message message) {
        DateTime now = DateTime.now();
        DateTime past = message.getDateTime();
        int seconds = Seconds.secondsBetween(past, now).getSeconds();
        int minutes = Minutes.minutesBetween(past, now).getMinutes();
        int hours = Hours.hoursBetween(past, now).getHours();
        int days = Days.daysBetween(past, now).getDays();

        int count = 0;
        String unit;

        if (seconds == 0) {
            return message.getMessage() + " (just now)";
        } else if (seconds < 60) {
            count = seconds;
            unit = "second";
        } else if (minutes < 60) {
            count = minutes;
            unit = "minute";
        } else if (hours < 24) {
            count = hours;
            unit = "minute";
        } else {
            count = days;
            unit = "day";
        }

        return message.getMessage() + " (" + renderRelativeDateTimeString(count, unit) + ")";
    }

    private String renderRelativeDateTimeString(int unitValue, String singularUnit) {
        return unitValue + " " + (unitValue == 1 ? singularUnit : singularUnit + "s") + " ago";
    }
}
