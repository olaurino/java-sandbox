package cfa.vo.speclib;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Curation {
    @UTYPE("Spectrum.Curation.Publisher")
    String getPublisher();
    void setPublisher(String publisher);
}
