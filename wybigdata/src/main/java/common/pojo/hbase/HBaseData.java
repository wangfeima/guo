package common.pojo.hbase;

import java.io.Serializable;


public class HBaseData implements Serializable {

    private String rowKeyName;
    private String timeStamp;
    private String columnFamily;
    private String column;
    private String value;


    public String getRowKeyName() {
        return rowKeyName;
    }

    public void setRowKeyName(String rowKeyName) {
        this.rowKeyName = rowKeyName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
