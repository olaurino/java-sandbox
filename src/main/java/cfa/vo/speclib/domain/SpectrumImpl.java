package cfa.vo.speclib.domain;

import cfa.vo.speclib.domain.model.CoordSys;
import cfa.vo.speclib.domain.model.Curation;
import cfa.vo.speclib.domain.model.Point;
import cfa.vo.speclib.domain.model.Spectrum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class SpectrumImpl implements Spectrum {
    private List<Point> points = new ArrayList();
    private Spectrum proxy;

    public SpectrumImpl(Spectrum proxy) {
        this.proxy = proxy;
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }

    public boolean add(Point point) {
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
}
