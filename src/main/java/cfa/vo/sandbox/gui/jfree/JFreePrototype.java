package cfa.vo.sandbox.gui.jfree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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
import org.jfree.ui.RefineryUtilities;

/**
 * Prototype class for experimenting with application requirements for the
 * SEDViewer.
 * 
 */
public class JFreePrototype extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    private static final int COUNT = 100;

    private ChartPanel panel;
    
    private static final String DATA = "<html>POINT DATA<br>ID=%s<br>X=%s<br>Y=%s";
    @SuppressWarnings("serial")
    private StandardXYToolTipGenerator xyToolTip = new StandardXYToolTipGenerator() {
        @Override
        public String generateToolTip(XYDataset data, int series, int point) {
            return String.format(DATA, "Some point id", data.getXValue(series, point), data.getYValue(series, point));
        }
    };
    
    public JFreePrototype(final String title) {
        super(title);

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

    private JFreeChart createChart() {

        XYPlot xyPlot = new XYPlot();
        xyPlot.setBackgroundPaint(Color.lightGray);
        xyPlot.setDomainGridlinePaint(Color.white);
        xyPlot.setRangeGridlinePaint(Color.white);

        setScatterPlot(xyPlot);

        NumberAxis domainAxis = new NumberAxis("X Data Label");
        NumberAxis rangeAxis = new NumberAxis("Y Data Label");
        NumberAxis rangeAxis2 = new NumberAxis("Other Y Data");

        xyPlot.setDomainAxis(domainAxis);
        xyPlot.setRangeAxis(rangeAxis);
        xyPlot.setRangeAxis(1, rangeAxis2);
        xyPlot.mapDatasetToDomainAxis(0, 0);
        xyPlot.mapDatasetToRangeAxis(0, 0);
        xyPlot.mapDatasetToRangeAxis(1, 1);
        
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

    private void setScatterPlot(XYPlot xyPlot) {

        // Add the data to the shared plot.
        XYIntervalSeriesCollection data1 = getPointData("sed 1", 0);
        XYErrorRenderer renderer1 = new XYErrorRenderer();
        renderer1.setBaseToolTipGenerator(xyToolTip);
        xyPlot.setDataset(0, data1);
        xyPlot.setRenderer(0, renderer1);

        // Add the data to the shared plot.
        XYIntervalSeriesCollection data2 = getPointData("sed 2", .5);
        XYErrorRenderer renderer2 = new XYErrorRenderer();
        renderer2.setDrawXError(true);
        renderer2.setDrawYError(true);
        renderer2.setBaseToolTipGenerator(xyToolTip);
        xyPlot.setDataset(1, data2);
        xyPlot.setRenderer(1, renderer2);
    }

    private XYIntervalSeriesCollection getPointData(String seriesName,
            double xStart) 
    {
        XYIntervalSeriesCollection data = new XYIntervalSeriesCollection();
        XYIntervalSeries xyIntervalSeries = new XYIntervalSeries(seriesName);

        Random g = new Random();
        for (double i = xStart; i < xStart + COUNT; i++) {
            double x = (double) i;
            double y = Math.abs(20 * Math.sin(x));
            double x_h = x + g.nextDouble();
            double x_l = x - g.nextDouble();
            double y_h = y + g.nextDouble();
            double y_l = y - g.nextDouble();
            xyIntervalSeries.add(x, x_l, x_h, y, y_l, y_h);
        }
        
        data.addSeries(xyIntervalSeries);
        return data;
    }
    
    /**
     * Listener class for mouse interactions over the plot.
     *
     */
    private static class ChartMouseActionListener implements ChartMouseListener {
        
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
            
            JOptionPane.showMessageDialog(panel, "So much information here! \n" + xyItem.toString());
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

    /**
     * Kickoff the demo app.
     * 
     */
    public static void main(final String[] args) {

        final JFreePrototype demo = new JFreePrototype("Prototype");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
