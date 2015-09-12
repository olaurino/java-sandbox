package cfa.vo.speclib.generic;

import uk.ac.starlink.table.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Omar on 9/11/2015.
 */
public class RowWrapperStarTable implements StarTable {
    RowListStarTable baseTable;

    public RowWrapperStarTable(RowListStarTable baseTable) {
        this.baseTable = baseTable;
    }

    protected RowListStarTable getBaseTable() {
        return baseTable;
    }

    protected void setBaseTable(RowListStarTable table) {
        this.baseTable = table;
    }

    public void addRow(Object[] values) {
        baseTable.addRow(values);
    }

    public void setRow(long lrow, Object[] values) {
        baseTable.setRow(lrow, values);
    }

    public void removeRow(long lrow) {
        baseTable.removeRow(lrow);
    }

    public void setParameters(List parameters) {
        baseTable.setParameters(parameters);
    }

    @Override
    public void setParameter(DescribedValue dval) {
        baseTable.setParameter(dval);
    }

    @Override
    public ColumnInfo getColumnInfo(int i) {
        return baseTable.getColumnInfo(i);
    }

    @Override
    public String getName() {
        return baseTable.getName();
    }

    @Override
    public void setName(String name) {
        baseTable.setName(name);
    }

    @Override
    public int getColumnCount() {
        return baseTable.getColumnCount();
    }

    @Override
    public long getRowCount() {
        return baseTable.getRowCount();
    }

    @Override
    public URL getURL() {
        return baseTable.getURL();
    }

    @Override
    public void setURL(URL url) {
        baseTable.setURL(url);
    }

    @Override
    public DescribedValue getParameterByName(String parname) {
        return baseTable.getParameterByName(parname);
    }

    public void clearRows() {
        baseTable.clearRows();
    }

    public void setCell(long lrow, int icol, Object value) {
        baseTable.setCell(lrow, icol, value);
    }

    public void insertRow(long lrow, Object[] values) {
        baseTable.insertRow(lrow, values);
    }

    @Override
    public boolean isRandom() {
        return baseTable.isRandom();
    }

    @Override
    public Object getCell(long l, int i) throws IOException {
        return baseTable.getCell(l, i);
    }

    @Override
    public Object[] getRow(long l) throws IOException {
        return baseTable.getRow(l);
    }

    @Override
    public RowSequence getRowSequence() {
        return baseTable.getRowSequence();
    }

    @Override
    public List getColumnAuxDataInfos() {
        return baseTable.getColumnAuxDataInfos();
    }

    @Override
    public List getParameters() {
        return baseTable.getParameters();
    }

    public void setCellInNewColumn(long lrow, Object value, ValueInfo vInfo) throws IOException {
        ColumnInfo cInfo = new ColumnInfo(vInfo);
        ArrayColumn column = ArrayColumn.makeColumn(cInfo, getBaseTable().getRowCount());
        column.storeValue(lrow, value);
        ColumnStarTable newTable = ColumnStarTable.makeTableWithRows(getBaseTable().getRowCount());
        newTable.addColumn(column);
        StarTable[] tableSet = new StarTable[ 2 ];
        tableSet[0] = getBaseTable();
        tableSet[1] = newTable;
        StarTable combinedTable = new JoinStarTable( tableSet );
        RowListStarTable newBase = Utils.getRowListStarTable(combinedTable);
        this.setBaseTable(newBase);
    }

    public void appendRow() {
        Object[] row = new Object[getColumnCount()];
        Arrays.fill(row, null);
        addRow(row);
    }
}
