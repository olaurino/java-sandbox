package cfa.vo.speclib.generic.quantity;

/**
 * Created by Omar on 9/12/2015.
 */
public interface Quantity<T> {
    Class<T> getType();

    String getUCD();

    String getUnit();

    T getValue();

    void setValue(T value);
}
