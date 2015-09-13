package cfa.vo.speclib.generic.quantity;

/**
 * Created by Omar on 9/12/2015.
 */
public class ColumnQuantity<T> implements Quantity<T> {
    private ValuedColumnInfo info;

    public ColumnQuantity(ValuedColumnInfo info) {
        this.info = info;
    }

    @Override
    public Class<T> getType() {
        return info.getContentClass();
    }

    @Override
    public String getUCD() {
        return info.getUCD();
    }

    @Override
    public String getUnit() {
        return info.getUnitString();
    }

    @Override
    public T getValue() {
        return (T) info.getValue();
    }

    @Override
    public void setValue(T value) {
        info.setValue(value);
    }
}
