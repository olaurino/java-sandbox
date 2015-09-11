package cfa.vo.speclib.domain;

import cfa.vo.speclib.domain.model.Sed;
import cfa.vo.speclib.domain.model.Spectrum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 9/11/2015.
 */
public class SedImpl implements Sed {
    private List<Spectrum> spectra = new ArrayList<Spectrum>();

    @Override
    public List<Spectrum> getSpectra() {
        return spectra;
    }
}
