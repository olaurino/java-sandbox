package cfa.vo.speclib.domain.model;

import java.util.List;

/**
 * Created by Omar on 9/10/2015.
 */
public interface Spectrum {
    Curation getCuration();
    Characterisation getCharacterisation();
    CoordSys getCoordSys();
    List<SpectrumPoint> getPoints();
}
