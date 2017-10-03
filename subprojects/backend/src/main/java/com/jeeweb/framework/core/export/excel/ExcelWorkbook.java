/**
 * 
 */
package com.jeeweb.framework.core.export.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 袁进勇
 *
 */
public class ExcelWorkbook {
    private String name;
    private List<ExcelSheet> excelSheetList = new ArrayList<>();

    public ExcelWorkbook() {
    }

    public ExcelWorkbook(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExcelSheet> getExcelSheetList() {
        return excelSheetList;
    }

    public void setExcelSheetList(List<ExcelSheet> sheetList) {
        this.excelSheetList = sheetList;
    }

    public void addSheet(ExcelSheet sheet) {
        excelSheetList.add(sheet);
    }
}
