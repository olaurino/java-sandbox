package cfa.vo.speclib.domain;

import cfa.vo.speclib.domain.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class SpectrumImpl implements Spectrum {
    // Immutable List
    // To add points use SpectralFactory.appendPoint
    private List<SpectrumPoint> points = new ArrayList();
    private Spectrum proxy;

    public SpectrumImpl(Spectrum proxy) {
        this.proxy = proxy;
    }

    public Spectrum getProxy() {
        return proxy;
    }

    @Override
    public List<SpectrumPoint> getPoints() {
        return Collections.unmodifiableList(points);
    }

    boolean add(SpectrumPoint point) {
        //FIXME this is partly safe because package public.
        //this is meant to be called by SpectralFactory.appendPoint
        return points.add(point);
    }

    @Override
    public Curation getCuration() {
        return proxy.getCuration();
    }

    @Override
    public CoordSys getCoordSys() {
        return proxy.getCoordSys();
    }

    @Override
    public Characterisation getCharacterisation() {
        return proxy.getCharacterisation();
    }
}
