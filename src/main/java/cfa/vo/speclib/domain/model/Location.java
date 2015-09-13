package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.quantity.Quantity;
import cfa.vo.speclib.generic.VOModel;

/**
 * Created by Omar on 9/10/2015.
 */
public interface Location {
    @VOModel(
            utype="Spectrum.Char.SpatialAxis.Coverage.Location.Value",
            contentType=double[].class,
            shape={2})
    Quantity<double[]> getMiddlePoint();
}
