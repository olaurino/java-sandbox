package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.quantity.Quantity;
import cfa.vo.speclib.generic.VOModel;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Curation {
    @VOModel(
            utype="Spectrum.Curation.Publisher",
            contentType=String.class
    )
    Quantity<String> getPublisher();
}
