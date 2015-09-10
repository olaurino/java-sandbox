package cfa.vo;

import cfa.vo.speclib.domain.SpectralFactory;
import cfa.vo.speclib.domain.model.Curation;
import cfa.vo.speclib.domain.model.Point;
import cfa.vo.speclib.domain.model.Spectrum;
import uk.ac.starlink.table.StarTable;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by olaurino on 9/4/15.
 */
public class TestSTIL {

    public static void main(String[] args) throws Exception {
        URL url = TestSTIL.class.getResource("/data/asdcMulti.xml");
        File f = new File(url.toURI());
        List<Spectrum> spectra = SpectralFactory.getSpectra(f, "spec");

        Spectrum spectrum = spectra.get(0);
        Point point = spectrum.getPoints().get(0);
        Curation pointCuration = point.getCuration();
        String pointCurationPublisher = pointCuration.getPublisher();

        assert "ASDC".equals(pointCurationPublisher);
        assert "ASDC".equals(spectrum.getCuration().getPublisher());

        // these should be the same object as they are cached by the implementation of StilDynamicProxy
        assert spectrum.getCuration() == pointCuration;
        assert pointCuration == spectrum.getPoints().get(1).getCuration();

        assert 5.037134225007662E-11 == point.getData().getSpectralAxis().getValue();

        assert 0.0f == point.getCoordSys().getSpectralFrame().getRedshift();

        StarTable table = SpectralFactory.getStarTable(spectrum);
        assert table != null;
    }
}