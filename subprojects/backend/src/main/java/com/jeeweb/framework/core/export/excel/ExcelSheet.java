/**
 * 
 */
package com.jeeweb.framework.core.export.excel;

import java.util.ArrayList;
import java.util.List;

import com.jeeweb.framework.core.model.RowMap;

/**
 * @author 袁进勇
 *
 */
public class ExcelSheet {
    private String name;
    private String title;
    private Boolean showLineNum;
    private List<ExcelColumn> columns = new ArrayList<>();
    private List<RowMap> dataList;

    public ExcelSheet() {
        this(null, null);
    }

    public ExcelSheet(String name, List<RowMap> dataList) {
        this(name, null, dataList);
    }

    public ExcelSheet(String name, String title, List<RowMap> dataList) {
        this(name, title, true, dataList);
    }

    public ExcelSheet(String name, String title, Boolean showLineNum, List<RowMap> dataList) {
        this.name = name;
        this.title = title;
        this.showLineNum = showLineNum;
        this.dataList = dataList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getShowLineNum() {
        return showLineNum;
    }

    public void setShowLineNum(Boolean showLineNum) {
        this.showLineNum = showLineNum;
    }

    public List<ExcelColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExcelColumn> columns) {
        this.columns = columns;
    }

    public List<RowMap> getDataList() {
        return dataList;
    }

    public void setDataList(List<RowMap> dataList) {
        this.dataList = dataList;
    }

    public void addColumn(ExcelColumn column) {
        columns.add(column);
    }
}
