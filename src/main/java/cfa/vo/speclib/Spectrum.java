package cfa.vo.speclib;

/**
 * Created by olaurino on 9/9/15.
 */
public interface Spectrum {
    Curation getCuration();
    void setCuration(Curation curation);
    Data getData();
    void setData(Data data);
}
