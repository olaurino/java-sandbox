package cfa.vo.speclib.domain;

import cfa.vo.speclib.domain.model.Sed;
import cfa.vo.speclib.domain.model.Spectrum;
import cfa.vo.speclib.domain.model.SpectrumPoint;
import cfa.vo.speclib.generic.Cache;
import cfa.vo.speclib.generic.RowWrapperStarTable;
import cfa.vo.speclib.generic.StarTableInvocationHandler;
import cfa.vo.speclib.generic.Utils;
import uk.ac.starlink.table.*;
import uk.ac.starlink.util.FileDataSource;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class SpectralFactory {
    private static StarTableFactory factory = new StarTableFactory();

    public static List<Spectrum> getSpectra(File f, String prefix) {
        try {
            TableSequence ls = factory.makeStarTables(new FileDataSource(f));

            List<Spectrum> spectra = new ArrayList();
            Class[] ifaces = new Class[]{SpectrumPoint.class};

            for (StarTable orig; (orig = ls.nextTable()) != null;) {
                RowListStarTable editable = Utils.getRowListStarTable(orig);
                RowWrapperStarTable table = new RowWrapperStarTable(editable);
                Cache cache = new Cache();
                Spectrum proxy = (Spectrum) Proxy.newProxyInstance(SpectralFactory.class.getClassLoader(), new Class[]{Spectrum.class}, new StarTableInvocationHandler(cache, table, prefix, null));
                SpectrumImpl spectrum = new SpectrumImpl(proxy);
                spectra.add(spectrum);
                for (long i=0; i<table.getRowCount(); i++) {
                    SpectrumPoint point = (SpectrumPoint) Proxy.newProxyInstance(SpectralFactory.class.getClassLoader(), ifaces, new StarTableInvocationHandler(cache, table, prefix, i));
                    point.setSpectrum(spectrum);
                    spectrum.add(point);
                }
            }
            return spectra;
        } catch (Throwable ex) {
            throw new RuntimeException("oops", ex);
        }
    }

    public static StarTable getStarTable(Spectrum spectrum) throws IntrospectionException {
        try {
            return Utils.getStarTableforProxy(spectrum);
//            throw new Exception();
        } catch (Exception e) {
            // FIXME replace with domain specific implementation of the StarTable interface for instances not backed up
            // by a dynamic proxy.
            return null;
        }
    }

    public static Sed getSed(File f, String prefix) {
        List<Spectrum> spectra = getSpectra(f, prefix);
        Sed sed = new SedImpl();
        sed.getSpectra().addAll(spectra);
        return sed;
    }

    // FIXME Should be Spectrum, not SpectrumImpl
    public static SpectrumPoint appendPoint(Spectrum spectrum) throws IntrospectionException {
        try {
            StarTableInvocationHandler handler = (StarTableInvocationHandler) Proxy.getInvocationHandler(((SpectrumImpl) spectrum).getProxy());
            Cache cache = handler.getCache();
            String prefix = handler.getPrefix();
            RowWrapperStarTable table = handler.getStarTable();
            Long index = table.getRowCount();
            table.appendRow();

            SpectrumPoint point = (SpectrumPoint) Proxy.newProxyInstance(SpectralFactory.class.getClassLoader(), new Class[]{SpectrumPoint.class}, new StarTableInvocationHandler(cache, table, prefix, index));
            ((SpectrumImpl) spectrum).add(point);
            return point;
        } catch (Exception e) {
            e.printStackTrace();
            // FIXME what to do here?
            return null;
        }
    }
}
