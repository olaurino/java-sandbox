package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.UTYPE;

/**
 * Created by Omar on 9/10/2015.
 */
public interface SpectralFrame {
    @UTYPE("Spectrum.CoordSys.SpectralFrame.Redshift")
    Double getRedshift();
    void setRedshift(Double redshift);
}
