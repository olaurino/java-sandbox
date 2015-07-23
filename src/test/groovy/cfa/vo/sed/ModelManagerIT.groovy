package cfa.vo.sed

import cfa.vo.sed.model.SpectrumStub
import spock.lang.Specification

import java.beans.PropertyVetoException
/**
 * Created by Omar on 7/21/2015.
 */
class ModelManagerIT extends Specification {
    def manager = SedManager.instance

    def "manager updates index when Entity ID has changed"() {
        given:
        def segments = []
        4.times { segments.add( new SpectrumStub() ) }
        segments.eachWithIndex { elem, index ->
            elem.id = "ID_$index"
            elem.name = "Spectrum${index}"
            manager.add(elem)
        }

        when:
        segments[0].id = 'foo'

        then:
        manager.findById("Spectrum_0") == null
        manager.findById("foo") == segments[0]

    }

    def "cannot give an Entity an existent ID"() {
        given:
        def s1 = new SpectrumStub(id:"ANID")
        def s2 = new SpectrumStub(id:"ANOTHERID")
        manager.add(s1)
        manager.add(s2)

        when:
        s2.id = "ANID"

        then:
        thrown(PropertyVetoException)
    }
}
