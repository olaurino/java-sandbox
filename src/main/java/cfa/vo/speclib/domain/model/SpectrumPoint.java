package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.Transient;

/**
 * Created by Omar on 9/10/2015.
 */
public interface SpectrumPoint extends Spectrum {
    Data getData();

    @Transient
    Spectrum getSpectrum();
    void setSpectrum(Spectrum spectrum);
}
