package cfa.vo.sed

import cfa.vo.sed.model.ISedSegment

import java.beans.PropertyVetoException

/**
 * Created by Omar on 7/21/2015.
 */
@Singleton
class SedManager {
    private Map<String, ISedSegment> idIndex = [:]
    private Map<String, ISedSegment> nameIndex = new WeakHashMap<String, ISedSegment>()

    def add(ISedSegment instance) {
        idIndex.put(instance.id, instance)
        nameIndex.put(instance.name, instance)
        instance.propertyChange = { evt ->
            if (evt.propertyName in ['name', 'id']) {
                this."${evt.propertyName}Index".put(evt.newValue, evt.source)
                this."${evt.propertyName}Index".remove(evt.oldValue)
            }
        }
        instance.vetoableChange = { evt ->
            if (evt.newValue in idIndex) {
                throw new PropertyVetoException("ID already present", evt)
            }
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

}
