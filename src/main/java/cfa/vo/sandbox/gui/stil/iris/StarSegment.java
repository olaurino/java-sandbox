package cfa.vo.sandbox.gui.stil.iris;

import uk.ac.starlink.table.StarTable;

import java.util.Map;

public class StarSegment {
    
    private StarTable table;
    private String prefix;
    
    public StarTablePreferences.LayerPreferences layerPreferences;
    
    public StarSegment(StarTable table) {
        this.table = table;
        this.prefix = table.getName();
        
        this.layerPreferences = 
                new StarTablePreferences.LayerPreferences(prefix)
                .setInSource(table)
                .setShape("open");
    }

    public Map<String, Object> getPreferences() {
        return this.layerPreferences.preferences;
    }


}
