package cfa.vo.speclib.generic.quantity;

import cfa.vo.speclib.generic.RowWrapperStarTable;
import uk.ac.starlink.table.ColumnInfo;

import java.io.IOException;

/**
 * Created by Omar on 9/12/2015.
 */
public class ValuedColumnInfo extends ColumnInfo {
    private RowWrapperStarTable table;
    private Long row;

    public ValuedColumnInfo(ColumnInfo info, RowWrapperStarTable table, Long row) {
        super(info);
        this.table = table;
        this.row = row;
    }

    public Object getValue() {
        for (int i=0; i<table.getColumnCount(); i++) {
            ColumnInfo info = table.getColumnInfo(i);
            if (getUtype().equals(info.getUtype())) {
                try {
                    return table.getCell(row, i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public void setValue(Object value) {
        for (int i=0; i<table.getColumnCount(); i++) {
            ColumnInfo info = table.getColumnInfo(i);
            if (getUtype().equals(info.getUtype())) {
                table.setCell(row, i, value);
                return;
            }
        }
        try {
            table.setCellInNewColumn(row, value, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
