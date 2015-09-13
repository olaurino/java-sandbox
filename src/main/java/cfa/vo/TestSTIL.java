package cfa.vo;

import cfa.vo.speclib.domain.SpectralFactory;
import cfa.vo.speclib.domain.model.Curation;
import cfa.vo.speclib.domain.model.Sed;
import cfa.vo.speclib.domain.model.Spectrum;
import cfa.vo.speclib.domain.model.SpectrumPoint;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.votable.VOTableWriter;

import java.io.File;
import java.net.URL;

/**
 * Created by olaurino on 9/4/15.
 */
public class TestSTIL {

    public static void main(String[] args) throws Exception {
        URL url = TestSTIL.class.getResource("/data/asdcMulti.xml");
        File f = new File(url.toURI());
        Sed sed = SpectralFactory.getSed(f, "spec");

        Spectrum spectrum = sed.getSpectra().get(0);
        SpectrumPoint point = spectrum.getPoints().get(0);
        Curation pointCuration = point.getSpectrum().getCuration();
        String pointCurationPublisher = pointCuration.getPublisher().getValue();

        assert "ASDC".equals(pointCurationPublisher);
        assert "ASDC".equals(spectrum.getCuration().getPublisher().getValue());

        assert 5.037134225007662E-11 == point.getData().getSpectralAxis().getMeasurement().getValue();

        assert 0.0f == point.getSpectrum().getCoordSys().getSpectralFrame().getRedshift().getValue();

        StarTable table = SpectralFactory.getStarTable(spectrum);
        assert table != null;

        assert 187.25 == spectrum.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 2.17 == spectrum.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        url = TestSTIL.class.getResource("/data/simple.xml");
        f = new File(url.toURI());
        spectrum = SpectralFactory.getSpectra(f, "spec").get(0);

        assert 2 == spectrum.getPoints().size();

        point = spectrum.getPoints().get(0);

        assert null == point.getSpectrum().getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue();

        assert 1.1 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 1.2 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        point = spectrum.getPoints().get(1);
        assert 2.1 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 2.2 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        spectrum.getCuration().getPublisher().setValue("Foo");
        assert "Foo" == spectrum.getCuration().getPublisher().getValue();

        point.getData().getSpectralAxis().getMeasurement().setValue(11111d);
        assert 11111d == point.getData().getSpectralAxis().getMeasurement().getValue();

        assert null == point.getFoo().getValue();
        point.getFoo().setValue(1.0);
        assert 1.0 == point.getFoo().getValue();

        assert 2 == spectrum.getPoints().size();
        assert 2 == SpectralFactory.getStarTable(spectrum).getRowCount();

        point = SpectralFactory.appendPoint(spectrum);
        assert null == point.getFoo().getValue();
        point.getFoo().setValue(2.0);
        assert 2.0 == point.getFoo().getValue();

        assert 3 == spectrum.getPoints().size();
        assert 3 == SpectralFactory.getStarTable(spectrum).getRowCount();

        assert point.getCuration().getPublisher() == point.getCuration().getPublisher();
        assert point.getFoo() == point.getFoo();

        assert null == spectrum.getBar().getValue();
        spectrum.getBar().setValue("Baz");
        assert "Baz" == spectrum.getBar().getValue();

        new VOTableWriter().writeStarTable(SpectralFactory.getStarTable(spectrum), System.out);
    }
}