package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.VOModel;
import cfa.vo.speclib.generic.quantity.Quantity;

/**
 * Created by Omar on 9/11/2015.
 */
public interface SpectrumMeta {
    Curation getCuration();

    Characterisation getCharacterisation();

    CoordSys getCoordSys();

    @VOModel(
            utype="Bar",
            contentType=String.class
    )
    Quantity<String> getBar();
}
