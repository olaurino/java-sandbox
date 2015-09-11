package cfa.vo.sandbox.gui;

import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.sandbox.gui.stil.iris.StarSegment;
import cfa.vo.sandbox.gui.stil.iris.StarTablePreferences;
import cfa.vo.speclib.domain.SpectralFactory;
import cfa.vo.speclib.domain.model.Sed;
import cfa.vo.speclib.domain.model.Spectrum;
import uk.ac.starlink.ttools.plot2.task.PlanePlot2Task;
import uk.ac.starlink.ttools.plot2.task.PlotDisplay;
import uk.ac.starlink.ttools.task.MapEnvironment;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StiltsDemoView extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private PlotDisplay display;
    
    private IrisApplication app;
    private IWorkspace ws;
    private SedlibSedManager sedManager;
    private List<StarSegment> tables;
    
    private StarTablePreferences tablePreferences;
    
    public StiltsDemoView(String title, IrisApplication app, IWorkspace ws) {
        
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
        
        // This abstraction needs to be reworked if we go this route
        this.sedManager = (SedlibSedManager) ws.getSedManager();
        
        setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 0, 0, 0));
        
        reset();
    }
    
    private void reset() {
        
        this.tables = new ArrayList<StarSegment>();
        
        try {
            this.display = createPlotComponent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        add(display, BorderLayout.CENTER);
    }
    
    private PlotDisplay createPlotComponent() throws Exception {
        
        MapEnvironment env = new MapEnvironment();
        env.setValue("type", "plot2plane");
        env.setValue("insets", new Insets(50, 40, 40, 40));

        URL url = getClass().getResource("/data/asdcMulti.xml");
        File f = new File(url.toURI());
        Sed sed = SpectralFactory.getSed(f, "spec");
        
        // Would be helpful if this were iterable.
        if (sed != null) {
            for (Spectrum s : sed.getSpectra()) {
                tables.add(new StarSegment(SpectralFactory.getStarTable(s)));
            }
        }
        
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