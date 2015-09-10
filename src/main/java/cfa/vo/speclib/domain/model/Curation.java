package cfa.vo.speclib.domain.model;

import cfa.vo.speclib.generic.UTYPE;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Curation {
    @UTYPE("Spectrum.Curation.Publisher")
    String getPublisher();
    void setPublisher(String publisher);
}
