package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.Transient;
import cfa.vo.speclib.generic.UTYPE;

/**
 * Created by Omar on 9/10/2015.
 */
public interface SpectrumPoint extends SpectrumMeta {
    Data getData();

    @Transient
    Spectrum getSpectrum();
    void setSpectrum(Spectrum spectrum);

    @UTYPE("Foo")
    Double getFoo();
    void setFoo(Double foo);
}
