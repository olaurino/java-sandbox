package cfa.vo.speclib.domain;

import cfa.vo.speclib.StilDynamicProxy;
import cfa.vo.speclib.Utils;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.votable.TableElement;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class SpectralFactory {
    public static List<Spectrum> getSpectra(File f, String prefix) {
        try {
            List<TableElement> nodes = Utils.getTableElements(f);

            List<Spectrum> spectra = new ArrayList();
            Class[] ifaces = new Class[]{Point.class};

            for (int index=0; index<nodes.size(); index++) {
                TableElement node = nodes.get(0);
                Spectrum proxy = (Spectrum) Proxy.newProxyInstance(SpectralFactory.class.getClassLoader(), new Class[]{Spectrum.class}, new StilDynamicProxy(node, "", index++, null));
                SpectrumImpl spectrum = new SpectrumImpl(proxy);
                spectra.add(spectrum);
                for (long i=0; i<node.getNrows(); i++) {
                    spectrum.add((Point) Proxy.newProxyInstance(SpectralFactory.class.getClassLoader(), ifaces, new StilDynamicProxy(node, prefix, index, i)));
                }
            }
            return spectra;
        } catch (Throwable ex) {
            throw new RuntimeException("oops");
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

}
