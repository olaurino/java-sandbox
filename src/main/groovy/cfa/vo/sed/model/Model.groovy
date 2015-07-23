package cfa.vo.sed.model
import groovy.beans.Bindable
import groovy.beans.Vetoable
import groovy.transform.AutoClone

import java.util.concurrent.atomic.AtomicLong

interface ISedSegment {
    String getId()
    String getName()
    String getDescription()
}

class RedshiftableCategory {
    static def redshift(ISedSegment segment, Double to, Double from, Boolean keepEnergy=Boolean.FALSE, Boolean copy=Boolean.FALSE) {
        println "Overridden with to $to, from $from"
    }
}

trait RedshiftableTrait {
    abstract String getId()
    abstract FlatSegmentTrait asType(Class<FlatSegmentTrait> aClass)
    def redshift(Double to, Double from, Boolean keepEnergy, Boolean copy) {
        println "Reshifting ${id} from $from to $to"
    }
}





//interface ISedSegment {
//    String getId()
//    String getName()
//    String getDescription()
//    Map getAttributes()
//    /**
//     * The problem of defining science methods in this interface is that implementations should then know e.g.
//     * how to redshift themselves, but this depends on the implementation of the Science Tools.
//     * On the other hand, since several different kinds of objects can be, e.g. redshifted, factoring
//     * the science operations outside of the domain object interfaces would require the Science Tools to have
//     * different implementations based on the different Object Types (e.g. how to redshift an AggregatedSed rather
//     * than a Spectrum of a PhotometrySegment). One option would be to make the IFlatSegment interface central, and
//     * to require that all implementations of ISedSegment provide an asType(IFlatSegment) method that would make
//     * all segments equally treatable. However, in that case the complexity would be transferred to mapping the original,
//     * arbitraty structure of an ISedSegment implementation (e.g. an AggregateSed can have an arbitrary
//     * deep hierarchical structure, with mixed units)
//     * to the order of the points (and units) in the Arrays in IFlatSegment. Plus, all operations on IFlatSegment
//     * should be constrained not to shuffle the X and Y arrays.
//     * Another option might be to require that points are IDed in IFlatSegment, through a new Array.
//     * In any case Groovy offers the opetion of using traits, which do not solve all the issues above,
//     * but provide a better support for composition over inheritance, that can help with the science operations
//     */
//    def redshift(Double to, Double from, Boolean copy)
//}

class FlatSegmentTrait implements ISedSegment {
    String id
    String name
    String description
    Double[] x
    Double[] y
    Accuracy[] yErr
    String xUnits
    String yUnits
    String yErrUnits
    abstract propertyChange
}

@AutoClone
class FlatSegment extends FlatSegmentTrait implements RedshiftableTrait {

    @Override
    FlatSegmentTrait asType(Class<FlatSegmentTrait> aClass) {
        return this
    }
}

@AutoClone
@Bindable
final class Spectrum extends FlatSegment {
    static AtomicLong _counter = new AtomicLong()

    @Vetoable String id = "${getClass().simpleName}_${_counter.getAndIncrement()}"
    @Bindable String name
    String description
    Map attributes = [:]
}

@AutoClone
@Bindable
class PhotometryPoint implements ISedSegment {
    static AtomicLong _counter = new AtomicLong()

    @Vetoable String id = "${getClass().simpleName}_${_counter.getAndIncrement()}"
    String name
    String description
    Double x
    Double y
    Accuracy yErr
    String xUnit
    String yUnit
}

@AutoClone
@Bindable
class PhotometrySegment implements ISedSegment, RedshiftableTrait {
    static AtomicLong _counter = new AtomicLong()

    @Vetoable String id = "${getClass().simpleName}_${_counter.getAndIncrement()}"
    String name
    String description
    List<PhotometryPoint> points = []
    Map attributes = [:]
    /**
     * This does not allow to specify the units, so it does not really work.
     */
    FlatSegmentTrait asType(Class<FlatSegmentTrait> aClass) {
        new FlatSegment(name: this.name, description: this.description, x: points.collect {it.x}, y: points.collect {it.y})
    }

    def add(PhotometryPoint point) {
        points.add(point)
    }

    def findById(String id) {
        return points.find {
            id == it.id
        }
    }

}

@AutoClone
@Bindable
class AggregateSed implements ISedSegment, RedshiftableTrait {
    static AtomicLong _counter = new AtomicLong()

    @Vetoable String id = "${getClass().simpleName}_${_counter.getAndIncrement()}"
    String name
    String description
    List<ISedSegment> segments = []
    Map attributes = [:]

    def add(ISedSegment segment) {
        segments.add(segment)
    }

    def findById(String id) {
        def tokens = id.split("\\.")
        def tail = null
        if (tokens.length > 1) {
            tail = tokens.tail()
        }
        def resp = segments.find { it.id == tokens.head() }
        if (tail) {
            resp = resp.findById "${tail.join(".")}"
        }
        return resp
    }

    @Override
    FlatSegmentTrait asType(Class<FlatSegmentTrait> aClass) {
        return null
    }
}

class Accuracy {
    Double statError
    Double sysError
}

class UniformSed extends FlatSegment {

}

interface IrisManager {
    def find(String id)
    def add(Object instance)
    def select(String id)
    def select(ISedSegment instance)
    def remove(String id)
    def remove(Object instance)
    def getSelected()

}

class TestIrisManager {
    Map instances = [:]
}

class IrisService {

}