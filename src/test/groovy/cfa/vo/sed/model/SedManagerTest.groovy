package cfa.vo.sed.model
import cfa.vo.sed.SedManager
import spock.lang.Specification
/**
 * Created by Omar on 7/21/2015.
 */
class SedManagerTest extends Specification {
    def s0
    def s1
    def p0
    def p1
    def manager

    def setup() {
        s0 = GroovyMock(Spectrum)
        s1 = GroovyMock(Spectrum)
        p0 = GroovyMock(PhotometryPoint)
        p1 = GroovyMock(PhotometryPoint)

        s0.id >> "Spectrum_0"
        s1.id >> "Spectrum_1"
        p0.id >> "PhotometryPoint_0"
        p1.id >> "PhotometryPoint_1"

        manager = SedManager.instance

        manager.add(s0)
        manager.add(s1)
        manager.add(p0)
        manager.add(p1)
    }

    def "manager can find entities by ID"() {
        expect:
        manager.findById("Spectrum_0") == s0
        manager.findById("Spectrum_1") == s1
        manager.findById("PhotometryPoint_0") == p0
        manager.findById("PhotometryPoint_1") == p1
    }
}
