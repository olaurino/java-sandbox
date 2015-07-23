package cfa.vo.sed

import cfa.vo.sed.model.ISedSegment
import groovy.util.logging.Log

import java.beans.PropertyVetoException

/**
 * Created by Omar on 7/21/2015.
 */
@Log()
@Singleton
class SedManager {
    private Map<String, ISedSegment> idIndex = [:]

    def add(ISedSegment instance) {
        idIndex.put(instance.id, instance)
        try {
            instance.propertyChange = { evt ->
                if (evt.propertyName == "id") {
                    this."${evt.propertyName}Index".put(evt.newValue, evt.source)
                    this."${evt.propertyName}Index".remove(evt.oldValue)
                }
            }
            instance.vetoableChange = { evt ->
                if (evt.newValue in idIndex) {
                    throw new PropertyVetoException("ID already present", evt)
                }
            }
        } catch (NoSuchMethodException) {
            log.warning("object $instance does not implement propertyChange and or vetoableChange. Continuing without registering")
        }

    }

    def findById(String id) {
        def tokens = id.split("\\.")
        def tail = null
        if (tokens.length > 1) {
            tail = tokens.tail()
        }
        def resp = idIndex.get(tokens.head())
        if (tail) {
            resp = resp.findById"${tail.join(".")}"
        }
        return resp
    }

    def findByName(String name) {
        idIndex.findAll { it.value.name == name }.collect {it.value};
    }

}
