package cfa.vo.sandbox.gui.samples;

import java.awt.EventQueue;
import java.awt.RenderingHints;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.data.xy.XYIntervalSeriesCollection;

import cfa.vo.sandbox.gui.stil.StilPrototype;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class PlotterView extends JInternalFrame {
    
    final ChartPanel plotter;
    final ChartPanel residuals;
    private JTextField txtXposistion;
    private JTextField txtYposition;
    
    private StilPrototype stil;
    
    private JFreeChart fillContent() throws IOException {
        try {
            stil = new StilPrototype("resources/data/SEDSample1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        XYPlot xyPlot = new XYPlot();
        xyPlot.setBackgroundPaint(Color.white);
        xyPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        xyPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYIntervalSeriesCollection data = stil.getPlotPoints();
        XYErrorRenderer renderer = new XYErrorRenderer();
        renderer.setDrawXError(true);
        renderer.setDrawYError(true);
        xyPlot.setDataset(0, data);
        xyPlot.setRenderer(0, renderer);

        NumberAxis domainAxis = new LogarithmicAxis("X Data Label");
        NumberAxis rangeAxis = new LogarithmicAxis("Y Data Label");

        xyPlot.setDomainAxis(domainAxis);
        xyPlot.setRangeAxis(rangeAxis);
        xyPlot.mapDatasetToDomainAxis(0, 0);
        xyPlot.mapDatasetToRangeAxis(0, 0);

        final JFreeChart chart = new JFreeChart("", xyPlot);
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        return chart;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlotterView frame = new PlotterView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PlotterView() throws Exception {
        setIcon(true);
        setMaximum(true);
        setMaximizable(true);
        setIconifiable(true);
        setBounds(100, 100, 1096, 800);
        
        plotter = new ChartPanel(fillContent());
        plotter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        plotter.setBackground(Color.WHITE);
        plotter.setVisible(true);
        
        residuals = new ChartPanel((JFreeChart) null);
        residuals.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        residuals.setBackground(Color.WHITE);
        residuals.setVisible(true);
        
        JButton btnReset = new JButton("Reset");
        
        JToggleButton tglbtnShowhideResiduals = new JToggleButton("Show Residuals");
        
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerListModel(new String[] {"Residuals", "Ratios", "Something"}));
        
        JButton button = new JButton("In");
        
        JButton button_1 = new JButton("Out");
        
        BasicArrowButton basicArrowButton = new BasicArrowButton(0);
        basicArrowButton.setDirection(7);
        
        BasicArrowButton basicArrowButton_1 = new BasicArrowButton(0);
        basicArrowButton_1.setDirection(3);
        
        BasicArrowButton basicArrowButton_2 = new BasicArrowButton(0);
        basicArrowButton_2.setDirection(1);
        
        BasicArrowButton basicArrowButton_3 = new BasicArrowButton(0);
        basicArrowButton_3.setDirection(5);
        
        txtXposistion = new JTextField();
        txtXposistion.setText("x-position");
        txtXposistion.setColumns(10);
        
        txtYposition = new JTextField();
        txtYposition.setText("y-position");
        txtYposition.setColumns(10);
        
        JCheckBox chckbxAbsolute = new JCheckBox("Absolute");
        
        JButton btnUnits = new JButton("Units");
        
        JSpinner spinner_1 = new JSpinner();
        spinner_1.setModel(new SpinnerListModel(new String[] {"Flux", "Flux Density"}));
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(plotter, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
                        .addComponent(residuals, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(btnReset)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(button)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(button_1)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(basicArrowButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(basicArrowButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(basicArrowButton_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(basicArrowButton_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(82)
                            .addComponent(chckbxAbsolute)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(txtXposistion, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(txtYposition, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                            .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnUnits))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(tglbtnShowhideResiduals)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spinner, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(txtXposistion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(chckbxAbsolute)
                                .addComponent(txtYposition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnUnits)
                                .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED))
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(basicArrowButton_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED))
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(basicArrowButton_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED))
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                    .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(basicArrowButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED))
                                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(groupLayout.createSequentialGroup()
                                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(button_1)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                    .addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGap(12))
                                        .addGroup(groupLayout.createSequentialGroup()
                                            .addComponent(basicArrowButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)))))))
                    .addComponent(plotter, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addGap(18)
                    .addComponent(residuals, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(tglbtnShowhideResiduals, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnF = new JMenu("File");
        menuBar.add(mnF);
        
        JMenuItem mntmExport = new JMenuItem("Export");
        mnF.add(mntmExport);
        
        JMenuItem mntmProperties = new JMenuItem("Properties");
        mnF.add(mntmProperties);
        
        JMenuItem mntmOpen = new JMenuItem("Open");
        mnF.add(mntmOpen);
        
        JMenuItem mntmSave = new JMenuItem("Save");
        mnF.add(mntmSave);
        
        JMenuItem mntmPrint = new JMenuItem("Print");
        mnF.add(mntmPrint);
        
        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);
        
        JMenuItem mntmSomething = new JMenuItem("Something");
        mnEdit.add(mntmSomething);
        
        JMenu mnView = new JMenu("View");
        menuBar.add(mnView);
        
        JMenu mnPlotType = new JMenu("Plot Type");
        mnView.add(mnPlotType);
        
        JMenu mnLog = new JMenu("Log");
        mnPlotType.add(mnLog);
        
        JMenuItem mntmRegularLog = new JMenuItem("Regular Log");
        mnLog.add(mntmRegularLog);
        
        JMenuItem mntmExcendedLog = new JMenuItem("Extended Log");
        mnLog.add(mntmExcendedLog);
        
        JMenuItem mntmLinear = new JMenuItem("Linear");
        mnPlotType.add(mntmLinear);
        
        JMenuItem mntmXlog = new JMenuItem("xLog");
        mnPlotType.add(mntmXlog);
        
        JMenuItem mntmYlog = new JMenuItem("yLog");
        mnPlotType.add(mntmYlog);
        
        JMenuItem mntmErrorBars = new JMenuItem("Error Bars");
        mnView.add(mntmErrorBars);
        
        JMenuItem mntmAutofixed = new JMenuItem("Auto/Fixed");
        mnView.add(mntmAutofixed);
        
        JMenuItem mntmGridOnoff = new JMenuItem("Grid on/off");
        mnView.add(mntmGridOnoff);
        
        JMenuItem mntmCoplot = new JMenuItem("Coplot");
        mnView.add(mntmCoplot);

    }
    private static void addPopup(Component component, final JPopupMenu popup) {
    }
}
