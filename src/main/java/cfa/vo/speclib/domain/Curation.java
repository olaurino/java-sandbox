package cfa.vo.speclib.domain;

import cfa.vo.speclib.UTYPE;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Curation {
    @UTYPE("Spectrum.Curation.Publisher")
    String getPublisher();
    void setPublisher(String publisher);
}
