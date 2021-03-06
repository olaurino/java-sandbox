package cfa.vo.sandbox.gui.stil.iris;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.RowSequence;
import uk.ac.starlink.table.StarTable;
import cfa.vo.sedlib.ArrayOfPoint;
import cfa.vo.sedlib.ISegment;
import cfa.vo.sedlib.Point;
import cfa.vo.sedlib.SedQuantity;
import cfa.vo.sedlib.Segment;

public class StarSegment implements StarTable {
    
    private ISegment data;
    private ColumnInfo[] columnInfo;
    private String prefix;
    
    public StarTablePreferences.LayerPreferences layerPreferences;
    
    public enum ColumnName {
        // Order is important. DO NOT CHANGE.
        X_COL("X axis values", Double.class),
        X_ERR_HI("X axis high error values", Double.class),
        X_ERR_LO("X axis low error values", Double.class),
        Y_COL("Y axis values", Double.class),
        Y_ERR_HI("Y axis high error values", Double.class),
        Y_ERR_LO("Y axis low error values", Double.class);
        
        public String description;
        public Class contentClass;
        
        private ColumnName(String description, Class contentClass) {
            this.description = description;
            this.contentClass = contentClass;
        }
    }    
    
    public StarSegment(ISegment data) {
        this.data = data;
        this.prefix = StringUtils.isEmpty(data.getDataID().toString()) ?
                UUID.randomUUID().toString() : data.getDataID().toString();
        
        this.layerPreferences = 
                new StarTablePreferences.LayerPreferences(prefix)
                .setInSource(this)
                .setShape("open");
        
        this.columnInfo = createColumnInfo();
    }
    
    private ColumnInfo[] createColumnInfo() {
        
        List<ColumnInfo> infos = new ArrayList<ColumnInfo>(ColumnName.values().length);
        ColumnName[] names = ColumnName.values();
        
        for (int i=0; i<names.length; i++) {
            ColumnName cn = names[i];
            ColumnInfo col = new ColumnInfo(
                    cn.name(),
                    cn.contentClass,
                    cn.description);
            infos.add(col);
        }
        
        return infos.toArray(new ColumnInfo[0]);
    }

    @Override
    public int getColumnCount() {
        return columnInfo.length;
    }

    @Override
    public long getRowCount() {
        return data.getLength();
    }

    @Override
    public String getName() {
        return data.getTarget().getName().getName();
    }

    @Override
    public ColumnInfo getColumnInfo(int icol) {
        return columnInfo[icol];
    }

    @Override
    public List getColumnAuxDataInfos() {
        List<Object> list = new ArrayList<Object>(columnInfo.length);
        
        for (int i=0; i<columnInfo.length; i++) {
            list.add(i, columnInfo[i].getAuxData());
        }
        
        return list;
    }

    @Override
    public boolean isRandom() {
        return true;
    }

    @Override
    public Object getCell(long irow, int icol) throws IOException {
        throw new RuntimeException("Called getCell");
    }

    @Override
    public Object[] getRow(long irow) throws IOException {
        Point p = data.getData().getPoint().get((int) irow);
        return pointToRow(p);
    }

    private Object[] pointToRow(Point point) {

        Object[] row = new Object[ColumnName.values().length];

        Double xCol = new Double(point.getSpectralAxis().getValue().getValue());
        Double xErrHi = xCol;
        Double xErrLo = xCol;
        Double yCol = new Double(point.getFluxAxis().getValue().getValue());
        Double yErrHi = yCol;
        Double yErrLo = yCol;

        row[0] = xCol;
        row[1] = xErrHi;
        row[2] = xErrLo;
        row[3] = yCol;
        row[4] = yErrHi;
        row[5] = yErrLo;

        return row;
    }

    @Override
    public RowSequence getRowSequence() throws IOException {
        throw new RuntimeException("Called getRowSequence");
    }
    
    public Map<String, Object> getPreferences() {
        return this.layerPreferences.preferences;
    }
    
    // Hopefully we won't need these?
    @Override
    public List getParameters() {
        throw new RuntimeException("Called getParameters");
    }

    @Override
    public DescribedValue getParameterByName(String parname) {
        throw new RuntimeException("Called getParameterByName");
    }

    @Override
    public void setParameter(DescribedValue dval) {
        throw new RuntimeException("Called setParameter");
    }

    @Override
    public URL getURL() {
        throw new RuntimeException("Called getURL");
    }

    @Override
    public void setURL(URL url) {
        throw new RuntimeException("Called setURL");
    }

    @Override
    public void setName(String name) {
        throw new RuntimeException("Called setName");
    }

    
    /**
     * RowIterator
     *
     */
    public class SegmentIterator implements RowSequence {
        
        private Object[] next;

        private Iterator<Point> it;

        public SegmentIterator() {
            it = data.getData().getPoint().iterator();
        }

        @Override
        public boolean next() throws IOException {
            if (it.hasNext()) {
                next = pointToRow(it.next());
            }

            return it.hasNext();
        }

        @Override
        public Object getCell(int icol) throws IOException {
            return next[icol];
        }

        @Override
        public Object[] getRow() throws IOException {
            return next;
        }

        @Override
        public void close() throws IOException {
        }
    }
}
