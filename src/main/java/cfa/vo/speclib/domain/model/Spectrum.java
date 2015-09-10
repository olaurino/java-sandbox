package cfa.vo.speclib.domain.model;

import java.util.List;

/**
 * Created by Omar on 9/10/2015.
 */
public interface Spectrum {
    List<Point> getPoints();
    Curation getCuration();
    CoordSys getCoordSys();
}
