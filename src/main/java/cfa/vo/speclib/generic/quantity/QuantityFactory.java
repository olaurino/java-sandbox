package cfa.vo.speclib.generic.quantity;

import uk.ac.starlink.table.DescribedValue;

/**
 * Created by Omar on 9/12/2015.
 */
public class QuantityFactory {
    public static Quantity makeQuantity(DescribedValue param) {
        return new ParamQuantity(param);
    }

    public static Quantity makeQuantity(ValuedColumnInfo colInfo) {
        return new ColumnQuantity(colInfo);
    }
}
