package nz.ben.flitter.ui.render;

import org.joda.time.DateTime;

import java.util.function.BiFunction;

/**
 * Created by bengilbert on 3/05/15.
 */
public interface DateDeltaFunction extends BiFunction<DateTime, DateTime, Integer> {
}
