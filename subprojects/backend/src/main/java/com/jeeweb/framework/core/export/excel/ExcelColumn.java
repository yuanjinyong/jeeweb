/**
 * 
 */
package com.jeeweb.framework.core.export.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author 袁进勇
 *
 */
public class ExcelColumn {
    private String id;
    private String name;
    private int type;
    private String format;
    private short alignment;
    private int width;

    public ExcelColumn(String id, String name) {
        this(id, name, Cell.CELL_TYPE_STRING);
    }

    public ExcelColumn(String id, String name, int type) {
        this(id, name, type, CellStyle.ALIGN_LEFT);
    }

    public ExcelColumn(String id, String name, int type, short alignment) {
        this(id, name, type, alignment, "TEXT");
    }

    public ExcelColumn(String id, String name, int type, short alignment, int width) {
        this(id, name, type, alignment, "TEXT", 0);
    }

    public ExcelColumn(String id, String name, int type, short alignment, String format) {
        this(id, name, type, alignment, "TEXT", 0);
    }

    public ExcelColumn(String id, String name, int type, short alignment, String format, int width) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.alignment = alignment;
        this.format = format;
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
