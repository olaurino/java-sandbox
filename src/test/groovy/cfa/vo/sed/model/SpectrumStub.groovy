package cfa.vo.sed.model

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Omar on 7/23/2015.
 */
@Bindable
class SpectrumStub implements ISedSegment {
    @Bindable String name
    @Vetoable String id
    String description
}

