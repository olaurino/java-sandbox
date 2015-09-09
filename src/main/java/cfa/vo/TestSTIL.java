package cfa.vo;

import cfa.vo.speclib.Curation;
import cfa.vo.speclib.IOFactory;
import cfa.vo.speclib.Spectrum;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by olaurino on 9/4/15.
 */
public class TestSTIL {

    public static void main(String[] args) throws IOException, SAXException, XPathExpressionException {
        File f = new File("/Users/olaurino/asdcMulti.vot");
        List<Spectrum> spectra = IOFactory.getSpectra(f);
        Spectrum s = spectra.get(0);
        Curation c = s.getCuration();
        String publisher = c.getPublisher();
        assert "ASDC".equals(publisher);
        double[] spectral = s.getData().getSpectralAxis().getValue();
        System.out.println(spectral);
    }
}