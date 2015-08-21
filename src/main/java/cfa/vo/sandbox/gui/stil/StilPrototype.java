  package cfa.vo.sandbox.gui.stil;

import java.io.File;
import java.io.IOException;

import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import uk.ac.starlink.table.RowSequence;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.StoragePolicy;
import uk.ac.starlink.table.gui.StarJTable;
import uk.ac.starlink.util.DataSource;
import uk.ac.starlink.util.FileDataSource;
import uk.ac.starlink.votable.VOTableBuilder;

public class StilPrototype {

    private StarTable table;

    public StilPrototype(String URL) throws Exception {
        DataSource dataSource = new FileDataSource(new File(URL));
        
        // Or just StarTableFactory and ignore the previous step
        VOTableBuilder builder = new VOTableBuilder();
        this.table = builder.makeStarTable(dataSource, false, StoragePolicy.PREFER_MEMORY);
        
        for (int i=0; i<table.getColumnCount(); i++) {
            System.out.println(">>> " + table.getColumnInfo(i));
        }
    }
    
    public StarTable getTable() {
        return table;
    }
    
    public XYIntervalSeriesCollection getPlotPoints(int i) throws IOException {
        XYIntervalSeriesCollection data = new XYIntervalSeriesCollection();
        XYIntervalSeries xyIntervalSeries = new XYIntervalSeries(
                table.getName());

        RowSequence rs = table.getRowSequence();
        while (rs.next()) {
            Object[] row = rs.getRow();

            double x = (Double) row[13];
            double y = Math.abs((Double) row[14] * i) + 1;
            double x_h = x;
            double x_l = x;
            double y_h = y;// y + (Double) row[15];
            double y_l = y;// y - (Double) row[15];
            xyIntervalSeries.add(x, x_l, x_h, y, y_l, y_h);
        }
        data.addSeries(xyIntervalSeries);
        return data;
    }
    
    public XYIntervalSeriesCollection getPlotPoints() throws IOException {
        XYIntervalSeriesCollection data = new XYIntervalSeriesCollection();
        XYIntervalSeries xyIntervalSeries = new XYIntervalSeries(
                table.getName());

        RowSequence rs = table.getRowSequence();
        while (rs.next()) {
            Object[] row = rs.getRow();

            double x = (Double) row[13];
            double y = (Double) row[14];
            double x_h = x;
            double x_l = x;
            double y_h = y;// y + (Double) row[15];
            double y_l = y;// y - (Double) row[15];
            xyIntervalSeries.add(x, x_l, x_h, y, y_l, y_h);
        }
        data.addSeries(xyIntervalSeries);
        return data;
    }

    public String getRowMetadata(long row) throws Exception {
        int ct = table.getColumnCount();
        Object[] r = table.getRow(row);

        StringBuilder b = new StringBuilder();
        for (int i=0; i<ct; i++) {
            b.append(String.format("%s=%s\n", table.getColumnInfo(i).getName(), r[i]));
        }
        
        return b.toString();
    }
}
