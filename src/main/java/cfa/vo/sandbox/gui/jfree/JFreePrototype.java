package cfa.vo.sandbox.gui.jfree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalDataItem;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import cfa.vo.sandbox.gui.stil.StilPrototype;

/**
 * Prototype class for experimenting with application requirements for the
 * SEDViewer.
 * 
 */
public class JFreePrototype extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    private ChartPanel panel;
    private StilPrototype table;
    
    private static final String DATA = "<html>POINT DATA<br>ID=%s<br>X=%s<br>Y=%s";
    @SuppressWarnings("serial")
    private StandardXYToolTipGenerator xyToolTip = new StandardXYToolTipGenerator() {
        @Override
        public String generateToolTip(XYDataset data, int series, int point) {
            return String.format(DATA, "Some point id", data.getXValue(series, point), data.getYValue(series, point));
        }
    };
    
    public JFreePrototype(final String title, StilPrototype table) throws Exception {
        super(title);
        this.table = table;

        final JFreeChart chart = createChart();
        
        panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(1000, 700));

        Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(0f));
        xCrosshair.setLabelVisible(true);
        Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                new BasicStroke(0f));
        yCrosshair.setLabelVisible(true);
        panel.addChartMouseListener(new ChartMouseActionListener(xCrosshair, yCrosshair, panel));

        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        panel.addOverlay(crosshairOverlay);

        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);

        setContentPane(panel);
    }

    private JFreeChart createChart() throws Exception {

        XYPlot xyPlot = new XYPlot();
        xyPlot.setBackgroundPaint(Color.lightGray);
        xyPlot.setDomainGridlinePaint(Color.white);
        xyPlot.setRangeGridlinePaint(Color.white);

        setScatterPlot(xyPlot);

        NumberAxis domainAxis = new LogarithmicAxis("X Data Label");
        NumberAxis rangeAxis = new LogarithmicAxis("Y Data Label");

        xyPlot.setDomainAxis(domainAxis);
        xyPlot.setRangeAxis(rangeAxis);
        xyPlot.mapDatasetToDomainAxis(0, 0);
        xyPlot.mapDatasetToRangeAxis(0, 0);
        
//        Theres something here, I'm just not yet totally clear on how function plotting works. Going to
//        hold off on doing more with it until I have a better understanding of model functions.
//        double[] reg = Regression.getPowerRegression(xyPlot.getDataset(0), 0);
//        PowerFunction2D func = new PowerFunction2D(reg[0], reg[1]);
//        xyPlot.setDataset(2, DatasetUtilities.sampleFunction2D(func, xyPlot.getDomainAxis().getLowerBound(), 
//                xyPlot.getDomainAxis().getUpperBound(), 50, "reg(sed1)"));
      

        final JFreeChart chart = new JFreeChart("", xyPlot);
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return chart;
    }

    private void setScatterPlot(XYPlot xyPlot) throws Exception {
        XYIntervalSeriesCollection data1 = table.getPlotPoints();
        
        XYErrorRenderer renderer1 = new XYErrorRenderer();
        renderer1.setBaseToolTipGenerator(xyToolTip);
        renderer1.setDrawXError(true);
        renderer1.setDrawYError(true);
        xyPlot.setDataset(0, data1);
        xyPlot.setRenderer(0, renderer1);
        
        for (int i=0; i<20; i++) {
            XYIntervalSeriesCollection data = table.getPlotPoints(i);
            
            renderer1.setBaseToolTipGenerator(xyToolTip);
            XYErrorRenderer renderer = new XYErrorRenderer();
            renderer.setBaseToolTipGenerator(xyToolTip);
            renderer.setDrawXError(true);
            renderer.setDrawYError(true);
            xyPlot.setDataset(i, data);
            xyPlot.setRenderer(i, renderer);
        }
    }
    
    /**
     * Listener class for mouse interactions over the plot.
     *
     */
    private class ChartMouseActionListener implements ChartMouseListener {
        
        private Crosshair xCrosshair;
        private Crosshair yCrosshair;
        private ChartPanel panel;

        public ChartMouseActionListener(Crosshair xCrosshair, 
                                        Crosshair yCrosshair,
                                        ChartPanel panel) {
            this.xCrosshair = xCrosshair;
            this.yCrosshair = yCrosshair;
            
            this.panel = panel;
        }

        public void chartMouseClicked(ChartMouseEvent event) {
            ChartEntity entity = event.getEntity();
            if (entity == null || !(entity instanceof XYItemEntity)) {
                return;
            }

            // This doesn't do anything, just an example of how to get data
            // from a mouse hit or hover.
            XYDataset dataset = ((XYItemEntity) entity).getDataset();
            int seriesIndex = ((XYItemEntity) entity).getSeriesIndex();
            int item = ((XYItemEntity) entity).getItem();

            XYIntervalSeries series = ((XYIntervalSeriesCollection) dataset).getSeries(seriesIndex);
            XYIntervalDataItem xyItem = (XYIntervalDataItem) series.getDataItem(item);
            
            try {
                JOptionPane.showMessageDialog(panel, table.getRowMetadata(item));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void chartMouseMoved(ChartMouseEvent event) {
            
            // Crosshairs
            Rectangle2D dataArea = this.panel.getScreenDataArea();
            JFreeChart chart = event.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();

            ValueAxis xAxis = plot.getDomainAxis();
            double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea,
                    RectangleEdge.BOTTOM);

            ValueAxis yAxis = plot.getRangeAxis();
            double y = yAxis.java2DToValue(event.getTrigger().getY(), dataArea,
                    RectangleEdge.LEFT);

            this.xCrosshair.setValue(x);
            this.yCrosshair.setValue(y);
        }
    }
}
