package cfa.vo.speclib.domain.model;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Data {
    SpectralAxis getSpectralAxis();
    void setSpectralAxis(SpectralAxis axis);
    FluxAxis getFluxAxis();
    void setFluxAxis(FluxAxis axis);
}