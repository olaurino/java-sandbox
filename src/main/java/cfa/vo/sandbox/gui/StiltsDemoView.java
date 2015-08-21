package cfa.vo.sandbox.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;

import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.sandbox.gui.stil.iris.StarSegment;
import cfa.vo.sandbox.gui.stil.iris.StarTablePreferences;
import uk.ac.starlink.ttools.plot2.task.PlanePlot2Task;
import uk.ac.starlink.ttools.plot2.task.PlotDisplay;
import uk.ac.starlink.ttools.task.MapEnvironment;

public class StiltsDemoView extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    
    private PlotDisplay display;
    
    private IrisApplication app;
    private IWorkspace ws;
    private SedlibSedManager sedManager;
    private List<StarSegment> tables;
    
    private StarTablePreferences tablePreferences;
    
    public StiltsDemoView(String title, IrisApplication app, IWorkspace ws) {
        
        super(title);
        
        this.ws = ws;
        this.app = app;
        
        // Default settings for the table
        tablePreferences = new StarTablePreferences()                
                .setColor("blue")
                .setXlog(true)
                .setYlog(true)
                .setGrid(true)
                .setxCol("DataSpectralValue")
                .setyCol("DataFluxValue")
                .setYerrhi("DataFluxStatErr")
                .setErrBar("capped_lines");
        
        // This abstraction needs to be reworked
        this.sedManager = (SedlibSedManager) ws.getSedManager(); 
        
        reset();
    }
    
    private void reset() {        

        ExtSed sed = sedManager.getSelected();
        this.tables = new ArrayList<StarSegment>(sed.getNumberOfSegments());
        
        for (int i=0; i<sed.getNumberOfSegments(); ++i) {
            tables.add(i, new StarSegment(sed.getSegment(i)));
        }
        
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
        env.setValue("insets", new Insets(50, 40, 40, 40)); 
        env.setValue("title", "STILTS Prototype");
        
        for (String key : tablePreferences.preferences.keySet()) {
            env.setValue(key, tablePreferences.preferences.get(key));
        }
        
        for (StarSegment layer : tables) {            
            for (String key : layer.getPreferences().keySet()) {
                env.setValue(key, layer.getPreferences().get(key));
            }
        }
        return new PlanePlot2Task().createPlotComponent(env, true);
    }
    
    // Add methods for setting colors, etc...
    // As well as listener methods
}