package cfa.vo.speclib.domain;

import cfa.vo.speclib.UTYPE;

/**
 * Created by olaurino on 9/9/15.
 */
public interface FluxAxis {
    @UTYPE("Spectrum.Data.FluxAxis.Value")
    double[] getValue();
    void setValue(double[] value);
}
