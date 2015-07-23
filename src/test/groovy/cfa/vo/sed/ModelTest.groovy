package cfa.vo.sed
import cfa.vo.sed.model.PhotometryPoint
import cfa.vo.sed.model.Spectrum
import spock.lang.Specification
/**
 * Created by Omar on 7/21/2015.
 */

class ModelTest extends Specification {
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

    def "each entity has a unique ID"() {
        expect:
            s0.id == "Spectrum_$sCounter"
            s1.id == "Spectrum_${sCounter+1}"
            p0.id == "PhotometryPoint_$pCounter"
            p1.id == "PhotometryPoint_${pCounter+1}"
    }

}