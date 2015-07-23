package cfa.vo.sed.model
import cfa.vo.sed.SedManager
import spock.lang.Specification

import java.util.logging.Logger
/**
 * Created by Omar on 7/21/2015.
 */
class SedManagerTest extends Specification {

    def manager = SedManager.instance

    def "manager can find entities by Name"() {
        given:
        def segments = []
        4.times { segments.add(GroovyMock(SpectrumStub)) }
        segments.eachWithIndex { elem, index ->
            elem.id >> "ID_$index"
            elem.name >> "Spectrum${index%2}"
            manager.add(elem)
        }

        expect:
        manager.findByName("Spectrum0") == [segments[0], segments[2]]
        manager.findByName("Spectrum1") == [segments[1], segments[3]]
    }

    def "manager can find entities by ID"() {
        given:
        def segment = GroovyMock(SpectrumStub)
        segment.getId() >>> (0..3).collect{"ID_$it"}
        segment.getName() >>> (0..3).collect{"Spectrum$it"}
        4.times { manager.add segment }

        expect:
        manager.findById("ID_0").name == "Spectrum0"
        manager.findById("ID_1").name == "Spectrum1"
        manager.findById("ID_2").name == "Spectrum2"
        manager.findById("ID_3").name == "Spectrum3"
    }


    def "SedManager registers itself to propertyChange"() {
        given:
        def spectrum = GroovyMock(SpectrumStub)

        when:
        manager.add(spectrum)

        then:
        1 * spectrum.setPropertyChange(_)
        1 * spectrum.setVetoableChange(_)
    }

    def "An error is logged when object does not implement propertyChange"() {
        given:
        def segment = [getId: {"foo"}] as ISedSegment
        GroovyMock(Logger, global:true)

        when:
        manager.add(segment)

        then:
        1 * manager.log.isLoggable(_) >>> true
        1 * manager.log.warning(_)
    }
}
