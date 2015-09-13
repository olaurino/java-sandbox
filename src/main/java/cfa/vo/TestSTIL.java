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
        // This is an actual ASDC file with ~400 tables, each with very few points.
        URL url = TestSTIL.class.getResource("/data/asdcMulti.xml");
        File f = new File(url.toURI());
        // Sed is just a dummy model resembling actual Seds, in that it has several segments, but they are all spectra.
        Sed sed = SpectralFactory.getSed(f, "spec");

        // Test some basic access methods
        Spectrum spectrum = sed.getSpectra().get(0);
        SpectrumPoint point = spectrum.getPoints().get(0);

        // Test String access and that points can access spectra
        Curation pointCuration = point.getSpectrum().getCuration();
        String pointCurationPublisher = pointCuration.getPublisher().getValue();

        // This methods tap into the same PARAM.
        assert "ASDC".equals(pointCurationPublisher);
        assert "ASDC".equals(spectrum.getCuration().getPublisher().getValue());

        // Test numeric values, for a column
        // Note that method may have any names, i.e. they do not depend on the UTYPE string.
        assert 2.4200000273883E22 == point.getData().getSpectralAxis().getMeasurement().getValue();

        // and for a PARAM
        assert 0.0f == point.getSpectrum().getCoordSys().getSpectralFrame().getRedshift().getValue();

        // Simplistic test to see that a StarTable is returned. Needs to be updated when implementation is updated.
        StarTable table = SpectralFactory.getStarTable(spectrum);
        assert table != null;

        // Test array cells, in this cae a PARAM.
        assert 187.25 == spectrum.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 2.17 == spectrum.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        // Load a simpler table with some more interesting stuff
        url = TestSTIL.class.getResource("/data/simple.xml");
        f = new File(url.toURI());
        spectrum = SpectralFactory.getSpectra(f, "spec").get(0);

        // We will add some rows and columns. Confirm the baseline number of rows and columns.
        assert 2 == spectrum.getPoints().size();
        table = SpectralFactory.getStarTable(spectrum);
        assert 2 == table.getRowCount();
        assert 6 == table.getColumnCount();

        point = spectrum.getPoints().get(0);

        // This table does not have a PARAM for SpatialAxis..Locaction.Value, but a FIELD
        assert null == point.getSpectrum().getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue();

        // Access the arrays in the column
        assert 1.1 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 1.2 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        point = spectrum.getPoints().get(1);
        assert 2.1 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 2.2 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        // See that we can change the values of the arrays in the columns
        point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().setValue(new double[]{5., 6.});
        assert 5.0 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[0];
        assert 6.0 == point.getCharacterisation().getSpatialAxis().getCoverage().getLocation().getMiddlePoint().getValue()[1];

        // See that we can change the value of a PARAM
        spectrum.getCuration().getPublisher().setValue("Foo");
        assert "Foo" == spectrum.getCuration().getPublisher().getValue();

        // See that we can change a single value in a cell.
        point.getData().getSpectralAxis().getMeasurement().setValue(11111d);
        assert 11111d == point.getData().getSpectralAxis().getMeasurement().getValue();
        assert 11111d == (Double) table.getCell(1, 2);

        // We will now create a column
        assert null == point.getFoo().getValue();
        point.getFoo().setValue(1.0);
        assert 1.0 == point.getFoo().getValue();
        assert 7 == table.getColumnCount();

        // Append a point (add a row).
        // Note that getPoints() returns an unmodifiable list.
        // However, the implementation is somewhat unsafe as only Points backed up by the StarTableInvocationHandler
        // should be added to the spectrum.
        try {
            spectrum.getPoints().add(null);
            assert false;
        } catch (UnsupportedOperationException ex) {
            assert true;
        }

        // This part of the API might need improvements. One should be able to create a point, most likely through the
        // SpectralFactory, and then add it to the spectrum.
        point = SpectralFactory.appendPoint(spectrum);

        // Check that we can set values
        assert null == point.getFoo().getValue();
        point.getFoo().setValue(2.0);
        assert 2.0 == point.getFoo().getValue();

        // Check that the table has actually changed.
        assert 3 == spectrum.getPoints().size();
        assert 3 == table.getRowCount();
        assert 2.0 == (Double) table.getCell(2, 6);

        // Check that we are not generating new objects at each access.
        assert point.getCuration().getPublisher() == point.getCuration().getPublisher();
        assert point.getFoo() == point.getFoo();

        // Check that we can add previously non-existent PARAMs
        assert null == spectrum.getBar().getValue();
        spectrum.getBar().setValue("Baz");
        assert "Baz" == spectrum.getBar().getValue();

        // Visually inspect VOTable. This must be replaced by an automated comparison with a baseline file.
        // Third row should be all None except a 2.0 in the 7th column.
        // Last column should have a null (empty) in the first row, 1.0 in the second, and 2.0 in the third.
        // The third cell in the first row should have value 11111.0.
        // The Curation.Publisher PARAM should have value Foo
        // There should be a PARAM with utype "Bar" and value "Baz"
        new VOTableWriter().writeStarTable(SpectralFactory.getStarTable(spectrum), System.out);
    }
}