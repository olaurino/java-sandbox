package cfa.vo.speclib.generic.quantity;

import uk.ac.starlink.table.DescribedValue;

/**
 * Created by Omar on 9/12/2015.
 */
public class ParamQuantity<T> implements Quantity<T> {
    private DescribedValue value;

    public ParamQuantity(DescribedValue value) {
        this.value = value;
    }

    @Override
    public Class<T> getType() {
        return value.getInfo().getContentClass();
    }

    @Override
    public String getUCD() {
        return value.getInfo().getUCD();
    }

    @Override
    public String getUnit() {
        return value.getInfo().getUnitString();
    }

    @Override
    public T getValue() {
        return (T) value.getValue();
    }

    @Override
    public void setValue(T value) {
        this.value.setValue(value);
    }
}
