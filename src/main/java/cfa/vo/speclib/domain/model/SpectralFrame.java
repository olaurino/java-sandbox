package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.quantity.Quantity;
import cfa.vo.speclib.generic.VOModel;

/**
 * Created by Omar on 9/10/2015.
 */
public interface SpectralFrame {
    @VOModel(utype="Spectrum.CoordSys.SpectralFrame.Redshift",
             contentType=Double.class)
    Quantity<Double> getRedshift();
}
