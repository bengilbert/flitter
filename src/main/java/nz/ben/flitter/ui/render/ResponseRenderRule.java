package nz.ben.flitter.ui.render;

/**
 * Created by bengilbert on 3/05/15.
 * <p>
 * Wrapper object containing required functions to render a relative datetime string.
 */
public class ResponseRenderRule {

    private DateDeltaFunction dateDeltaFunction = null;
    private IntComparatorFunction comparator = null;
    private RelativeDateRenderFunction renderer;


    public ResponseRenderRule(DateDeltaFunction dateDelta, IntComparatorFunction comparator, RelativeDateRenderFunction renderer) {
        this.dateDeltaFunction = dateDelta;
        this.renderer = renderer;
        this.comparator = comparator;
    }

    public IntComparatorFunction ruleApplies() {
        return this.comparator;
    }

    public RelativeDateRenderFunction render() {
        return this.renderer;
    }

    public DateDeltaFunction delta() {
        return this.dateDeltaFunction;
    }
}
