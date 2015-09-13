package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.quantity.Quantity;
import cfa.vo.speclib.generic.VOModel;

/**
 * Created by olaurino on 9/9/15.
 */
public interface FluxAxis {
    @VOModel(
            utype="Spectrum.Data.FluxAxis.Value",
            contentType=Double.class)
    Quantity<Double> getMeasurement();
}
