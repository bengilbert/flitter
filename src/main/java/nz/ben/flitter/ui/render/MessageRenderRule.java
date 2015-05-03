package nz.ben.flitter.ui.render;

import org.joda.time.DateTime;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by bengilbert on 3/05/15.
 * <p>
 * Wrapper object containing required functions to render a relative datetime string.
 */
public class MessageRenderRule {

    private BiFunction<DateTime, DateTime, Integer> dateDeltaFunction = null;
    private Function<Integer, Boolean> comparator = null;
    private Function<Integer, String> renderer;


    public MessageRenderRule(BiFunction<DateTime, DateTime, Integer> dateDelta, Function<Integer, Boolean> comparator, Function<Integer, String> renderer) {
        this.dateDeltaFunction = dateDelta;
        this.renderer = renderer;
        this.comparator = comparator;
    }

    public Function<Integer, Boolean> compare() {
        return this.comparator;
    }

    public Function<Integer, String> render() {
        return this.renderer;
    }

    public BiFunction<DateTime, DateTime, Integer> delta() {
        return this.dateDeltaFunction;
    }
}
