package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.UTYPE;

/**
 * Created by Omar on 9/10/2015.
 */
public interface Location {
    @UTYPE("Spectrum.Char.SpatialAxis.Coverage.Location.Value")
    double[] getValue();
    void setValue(double[] value);
}
