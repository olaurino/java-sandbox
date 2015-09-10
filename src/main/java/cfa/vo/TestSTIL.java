package cfa.vo;

import cfa.vo.speclib.domain.Curation;
import cfa.vo.speclib.domain.Point;
import cfa.vo.speclib.domain.SpectralFactory;
import cfa.vo.speclib.domain.Spectrum;
import uk.ac.starlink.table.StarTable;

import java.io.File;
import java.util.List;

/**
 * Created by olaurino on 9/4/15.
 */
public class TestSTIL {

    public static void main(String[] args) throws Exception {
        File f = new File("C:/Users/Omar/asdcMulti.vot.xml");
        List<Spectrum> spectra = SpectralFactory.getSpectra(f, "");

        Spectrum spectrum = spectra.get(0);
        Point point = spectrum.getPoints().get(0);
        Curation pointCuration = point.getCuration();
        String pointCurationPublisher = pointCuration.getPublisher();

        assert "ASDC".equals(pointCurationPublisher);
        assert "ASDC".equals(spectrum.getCuration().getPublisher());

        // these should be the same object as they are cached by the implementation of StilDynamicProxy
        assert spectrum.getCuration() == pointCuration;
        assert pointCuration == spectrum.getPoints().get(1).getCuration();

        double spectral = point.getData().getSpectralAxis().getValue();
        System.out.println(spectral);

        StarTable table = SpectralFactory.getStarTable(spectrum);
        assert table != null;
    }
}