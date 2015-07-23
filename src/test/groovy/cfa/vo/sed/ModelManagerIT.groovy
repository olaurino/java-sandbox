package cfa.vo.sed
import cfa.vo.sed.model.PhotometryPoint
import cfa.vo.sed.model.Spectrum
import spock.lang.Specification

import java.beans.PropertyVetoException
/**
 * Created by Omar on 7/21/2015.
 */
class ModelManagerIT extends Specification {
    def sCounter
    def pCounter
    def s0
    def s1
    def p0
    def p1
    def manager

    def setup() {
        sCounter = Spectrum._counter.get()
        pCounter = PhotometryPoint._counter.get()
        s0 = new Spectrum()
        s1 = new Spectrum()
        p0 = new PhotometryPoint()
        p1 = new PhotometryPoint()

        manager = SedManager.instance

        manager.add(s0)
        manager.add(s1)
        manager.add(p0)
        manager.add(p1)
    }

    def "manager updates index when Entity ID has changed"() {
        given:
        s0.id = "foo"

        expect:
        manager.findById("Spectrum_$sCounter") == null
        manager.findById("foo") == s0

    }

    def "cannot give an Entity an existent ID"() {
        given:
        s0.id = "bar"

        when:
        s1.id = "bar"

        then:
        thrown(PropertyVetoException)
    }
}
