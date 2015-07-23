package cfa.vo.sed.model
import cfa.vo.sed.SedManager
import spock.lang.Specification
/**
 * Created by Omar on 7/21/2015.
 */
class SedManagerTest extends Specification {
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

    def "manager can find entities by ID"() {
        expect:
        manager.findById("Spectrum_$sCounter") == s0
        manager.findById("Spectrum_${sCounter+1}") == s1
        manager.findById("PhotometryPoint_$pCounter") == p0
        manager.findById("PhotometryPoint_${pCounter+1}") == p1
    }
}
