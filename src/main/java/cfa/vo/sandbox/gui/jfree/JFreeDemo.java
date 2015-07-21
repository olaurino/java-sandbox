package cfa.vo.sandbox.gui.jfree;

import java.awt.RenderingHints;
import java.util.Random;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * JFreeChart demo and exposition of a few things we may be interesting
 * for visualizing and plotting SEDs.
 * 
 * Demonstrated here are plotting points, plotting line segments, and
 * overlaying them on the same chart. Note that we can handle 1 million points
 * with relative ease.
 *
 */
public class JFreeDemo extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    private static final int COUNT = 1000000;
    
    private NumberAxis domainAxis;
    private NumberAxis rangeAxis;
    
    private XYPlot xyPlot;


    public JFreeDemo(final String title) {
        super(title);

        final JFreeChart chart = createChart();
//        chart.draw(g2, area);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(1000, 700));
        
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        setContentPane(panel);
    }
    
    private JFreeChart createChart() {
        domainAxis = new NumberAxis("X Data Label");
        rangeAxis = new NumberAxis("Y Data Label");

        xyPlot = new XYPlot();
        setScatterPlot();
        setLinePlot();
        
        xyPlot.setDomainAxis(domainAxis);
        xyPlot.setRangeAxis(rangeAxis);
        xyPlot.mapDatasetToDomainAxis(0, 0);
        xyPlot.mapDatasetToRangeAxis(0, 0);
        
        final JFreeChart chart = new JFreeChart("Multiple DataSets Chart", xyPlot);
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        return chart;
    }
    
    /**
     * Generate a scatter plot of points on a parabola.
     * 
     */
    private void setScatterPlot() {
        XYDataset data = getPointData();
        
        // Renderers handle the drawing of your data. They also handle drawing options for
        // color, shape, weight, etc.
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotHeight(5);
        renderer.setDotWidth(5);
        
        // Add the data to the shared plot.
        xyPlot.setDataset(0, data);
        xyPlot.setRenderer(0, renderer);
    }
    
    private XYDataset getPointData() {
        double[][] values = new double[2][COUNT];
        for (int i = 0; i < values[0].length; i++) {
            final float x = (float) i;
            values[0][i] = x;
            values[1][i] = (float) (Math.pow(x, 2));
        }
        
        DefaultXYDataset data = new DefaultXYDataset();
        data.addSeries("scatter plot", values);
        
        return data;
    }
    
    /**
     * Generate a line plot.
     */
    private void setLinePlot() {
        XYDataset data = getPlotData();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        
        // Add the data to the shared plot.
        xyPlot.setDataset(1, data);
        xyPlot.setRenderer(1, renderer);
    }
    
    private XYDataset getPlotData() {
        XYSeries seriesData = new XYSeries("data series");
        Random random = new Random();
        for (int i = 0; i <= COUNT; i = i + COUNT/1000) {
            seriesData.add(i, (.9 + (random.nextFloat()/2)) * Math.pow(i, 2));
        }
        XYSeriesCollection data = new XYSeriesCollection(seriesData);
        return data;
    }

    /**
     * Kickoff the demo app.
     * 
     */
    public static void main(final String[] args) {

        final JFreeDemo demo = new JFreeDemo("Fast Scatter Plot Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}