package cfa.vo.sandbox.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.StarTableFactory;
import uk.ac.starlink.ttools.plot2.task.PlanePlot2Task;
import uk.ac.starlink.ttools.plot2.task.PlotDisplay;
import uk.ac.starlink.ttools.task.MapEnvironment;

public class StiltsDemoView extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private PlotDisplay display;
    private List<StarTable> tables;
    
    public StiltsDemoView(String title) {
        super(title);
        StarTableFactory f = new StarTableFactory();
        
        try {
            tables = Arrays.asList(
            //    f.makeStarTable("https://s3.amazonaws.com/eho86/SEDSample1")
                );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        init();
    }

    public StiltsDemoView(String title, List<StarTable> tables) {
        super(title);
        this.tables = tables;
        init();
    }
    
    private void init() {
        try {
            this.display = createPlotComponent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        display.setPreferredSize(new java.awt.Dimension(1000, 800));
        add(display, BorderLayout.CENTER);

        pack();
    }
    
    private PlotDisplay createPlotComponent() throws Exception {
        
        MapEnvironment env = new MapEnvironment();
        env.setValue("type", "plot2plane"); 
        env.setValue( "insets", new Insets(50, 40, 40, 40)); 
        env.setValue("title", "STILTS Prototype");
        
        StarTablePreferences tablePrefs = new StarTablePreferences()
                .setColor("blue")
                .setXlog(true)
                .setYlog(true)
                .setGrid(true)
                .setxCol("DataSpectralValue")
                .setyCol("DataFluxValue")
                .setYerrhi("DataFluxStatErr")
                .setErrBar("capped_lines");
        for (String key : tablePrefs.preferences.keySet()) {
            env.setValue(key, tablePrefs.preferences.get(key));
        }
        
        for (int i=0; i<tables.size(); i++) {
            LayerPreferences layer = new LayerPreferences(i + "")
                    .setType("xyerror")
                    .setInSource(tables.get(i))
                    .setColor("red")
                    .setShape("open");
            
            for (String key : layer.preferences.keySet()) {
                env.setValue(key, layer.preferences.get(key));
            }
        }
        return new PlanePlot2Task().createPlotComponent(env, true);
    }
    
    
    // This is only a small sample of the preferences available in STILTS, for
    // the full list, see http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#TypedPlot2Task
    // Global Settings
    public static final String SHAPE = "shape";
    public static final String GRID = "grid";
    public static final String X_LABEL = "xlabel";
    public static final String Y_LABEL = "ylabel";
    public static final String X_LOG = "xlog";
    public static final String Y_LOG = "ylog";
    
    // Override-able Settings
    public static final String TYPE = "layer";
    public static final String IN = "in";
    public static final String X_COL = "x";
    public static final String Y_COL = "y";
    public static final String X_ERR_HI = "xerrhi";
    public static final String Y_ERR_HI = "yerrhi";
    public static final String X_ERR_LO = "xerrlo";
    public static final String Y_ERR_LO = "yerrlo";
    public static final String COLOR = "color";
    public static final String ERROR_BAR = "errorbar";
    
    
    public static class StarTablePreferences {
        
        public Map<String, Object> preferences;

        public StarTablePreferences() {
            this.preferences = new HashMap<String, Object>();
        }
        
        public StarTablePreferences setType(String arg1) {
            this.preferences.put(TYPE, arg1);
            return this;
        }
        public StarTablePreferences setColor(String arg1) {
            this.preferences.put(COLOR, arg1);
            return this;
        }
        public StarTablePreferences setErrBar(String arg1) {
            this.preferences.put(ERROR_BAR, arg1);
            return this;
        }
        public StarTablePreferences setShape(String arg1) {
            this.preferences.put(SHAPE, arg1);
            return this;
        }
        public StarTablePreferences setGrid(boolean arg1) {
            this.preferences.put(GRID, arg1);
            return this;
        }
        public StarTablePreferences setxCol(String arg1) {
            this.preferences.put(X_COL, arg1);
            return this;
        }
        public StarTablePreferences setyCol(String arg1) {
            this.preferences.put(Y_COL, arg1);
            return this;
        }
        public StarTablePreferences setXerrhi(String arg1) {
            this.preferences.put(X_ERR_HI, arg1);
            return this;
        }
        public StarTablePreferences setXerrlo(String arg1) {
            this.preferences.put(X_ERR_LO, arg1);
            return this;
        }
        public StarTablePreferences setYerrhi(String arg1) {
            this.preferences.put(Y_ERR_HI, arg1);
            return this;
        }
        public StarTablePreferences setYerrlo(String arg1) {
            this.preferences.put(Y_ERR_LO, arg1);
            return this;
        }
        public StarTablePreferences setYlabel(String arg1) {
            this.preferences.put(Y_LABEL, arg1);
            return this;
        }
        public StarTablePreferences setXlabel(String arg1) {
            this.preferences.put(X_LABEL, arg1);
            return this;
        }
        public StarTablePreferences setXlog(boolean arg1) {
            this.preferences.put(X_LOG, arg1);
            return this;
        }
        public StarTablePreferences setYlog(boolean arg1) {
            this.preferences.put(Y_LOG, arg1);
            return this;
        }
    }
    
    public static class LayerPreferences {
        
        public Map<String, Object> preferences;
        private String suffix;

        public LayerPreferences(String layerName) {
            this.preferences = new HashMap<String, Object>();
            this.suffix = '_' + layerName;
        }

        public LayerPreferences setType(String arg1) {
            this.preferences.put(TYPE + suffix, arg1);
            return this;
        }
        public LayerPreferences setInSource(Object arg1) {
            this.preferences.put(IN + suffix, arg1);
            return this;
        }
        public LayerPreferences setColor(String arg1) {
            this.preferences.put(COLOR + suffix, arg1);
            return this;
        }
        public LayerPreferences setShape(String arg1) {
            this.preferences.put(SHAPE + suffix, arg1);
            return this;
        }
        public LayerPreferences setxCol(String arg1) {
            this.preferences.put(X_COL + suffix, arg1);
            return this;
        }
        public LayerPreferences setyCol(String arg1) {
            this.preferences.put(Y_COL + suffix, arg1);
            return this;
        }
        public LayerPreferences setXerrhi(String arg1) {
            this.preferences.put(X_ERR_HI + suffix, arg1);
            return this;
        }
        public LayerPreferences setXerrlo(String arg1) {
            this.preferences.put(X_ERR_LO + suffix, arg1);
            return this;
        }
        public LayerPreferences setYerrhi(String arg1) {
            this.preferences.put(Y_ERR_HI + suffix, arg1);
            return this;
        }
        public LayerPreferences setYerrlo(String arg1) {
            this.preferences.put(Y_ERR_LO + suffix, arg1);
            return this;
        }
    }
}