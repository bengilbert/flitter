package nz.ben.flitter.ui;

import org.joda.time.DateTime;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by bengilbert on 3/05/15.
 */
public class MessageRenderRule {

    private BiFunction<DateTime, DateTime, Integer> dateDeltaFunction = null;
    private Function<Integer, Boolean> comparator = null;
    private String singletonDisplayUnit = null;


    public MessageRenderRule(BiFunction<DateTime, DateTime, Integer> dateDelta, Function<Integer, Boolean> comparator, String singletonDisplayUnit) {
        this.dateDeltaFunction = dateDelta;
        this.singletonDisplayUnit = singletonDisplayUnit;
        this.comparator = comparator;
    }

    public boolean passes(final DateTime now, final DateTime then) {
        int delta = dateDeltaFunction.apply(then, now);

        return comparator.apply(delta);
    }

    public String getSingletonDisplayUnit() {
        return this.singletonDisplayUnit;
    }

    public int getDelta(final DateTime now, final DateTime then) {
        return dateDeltaFunction.apply(then, now);
    }
}
