/**
 * 
 */
package com.jeeweb.framework.core.export.excel;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;

/**
 * @author 袁进勇
 *
 */
public class ExcelXlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExcelWorkbook excelWorkbook = (ExcelWorkbook) model.get(ExcelWorkbook.class.getName());
        String fileName = URLEncoder.encode(excelWorkbook.getName(), "UTF-8"); // 中文文件名需要编码
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        for (ExcelSheet excelSheet : excelWorkbook.getExcelSheetList()) {
            createSheet(workbook, excelSheet);
        }
    }

    private Sheet createSheet(Workbook workbook, ExcelSheet sheetCfg) {
        Sheet sheet = workbook.createSheet(sheetCfg.getName()); // 创建工作表

        // 产生表格标题行
        int rowNum = createSheetTitle(workbook, sheet, sheetCfg, 0);
        // 产生表格列头行
        rowNum = createSheetHeader(workbook, sheet, sheetCfg, rowNum);
        // 产生表格数据行
        rowNum = createSheetData(workbook, sheet, sheetCfg, rowNum);

        // 让列宽随着导出的列长自动适应
        adjustColumnWidth(sheet, sheetCfg);

        return sheet;
    }

    private int createSheetTitle(Workbook workbook, Sheet sheet, ExcelSheet sheetCfg, int startRowNum) {
        int rowNum = startRowNum;
        if (!HelpUtil.isEmpty(sheetCfg.getTitle())) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(getTitleStyle(workbook)); // 设置标题样式
            cell.setCellValue(sheetCfg.getTitle());

            // 合并单元格
            int rowSpan = rowNum++; // 标题占2行
            int colSpan = sheetCfg.getColumns().size() - (sheetCfg.getShowLineNum() ? 0 : 1); // 标题占所有列
            sheet.addMergedRegion(new CellRangeAddress(0, rowSpan, 0, colSpan));
        }

        return rowNum;
    }

    private int createSheetHeader(Workbook workbook, Sheet sheet, ExcelSheet sheetCfg, int startRowNum) {
        int rowNum = startRowNum;
        CellStyle style = getHeaderStyle(workbook);

        int colNum = 0;
        Row row = sheet.createRow(rowNum++);
        if (sheetCfg.getShowLineNum()) {
            Cell cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style); // 设置列头样式
            cell.setCellValue("序号");
        }

        for (ExcelColumn column : sheetCfg.getColumns()) {
            Cell cell = row.createCell(colNum++, Cell.CELL_TYPE_STRING); // 创建列头对应个数的单元格
            cell.setCellStyle(style); // 设置列头样式
            cell.setCellValue(new HSSFRichTextString(column.getName())); // 设置列头单元格的值
        }

        return rowNum;
    }

    private int createSheetData(Workbook workbook, Sheet sheet, ExcelSheet sheetCfg, int startRowNum) {
        int rowNum = startRowNum;
        DataFormat format = workbook.createDataFormat();

        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体"); // 设置字体名字
        font.setFontHeightInPoints((short) 12);

        List<CellStyle> styleList = new ArrayList<>(); // 单元格样式对象
        if (sheetCfg.getShowLineNum()) {
            CellStyle style = workbook.createCellStyle();
            styleList.add(style);
            style.setFont(font); // 在样式用应用设置的字体;
            style.setDataFormat(format.getFormat("0"));

            style.setBorderBottom(CellStyle.BORDER_THIN); // 设置底边框;
            style.setBorderLeft(CellStyle.BORDER_THIN); // 设置左边框;
            style.setBorderRight(CellStyle.BORDER_THIN); // 设置右边框;
            style.setBorderTop(CellStyle.BORDER_THIN); // 设置顶边框;

            style.setAlignment(CellStyle.ALIGN_RIGHT); // 设置水平对齐的样式为居中对齐;
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 设置垂直对齐的样式为居中对齐;
            style.setWrapText(false); // 设置自动换行;
        }
        for (ExcelColumn column : sheetCfg.getColumns()) {
            CellStyle style = workbook.createCellStyle();
            styleList.add(style);
            style.setFont(font); // 在样式用应用设置的字体;
            style.setDataFormat(format.getFormat(column.getFormat()));

            style.setBorderBottom(CellStyle.BORDER_THIN); // 设置底边框;
            style.setBorderLeft(CellStyle.BORDER_THIN); // 设置左边框;
            style.setBorderRight(CellStyle.BORDER_THIN); // 设置右边框;
            style.setBorderTop(CellStyle.BORDER_THIN); // 设置顶边框;

            style.setAlignment(column.getAlignment());
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 设置垂直对齐的样式为居中对齐;
            style.setWrapText(false); // 设置自动换行;
        }

        List<RowMap> dataList = sheetCfg.getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            int colNum = 0;
            Row row = sheet.createRow(rowNum++);
            if (sheetCfg.getShowLineNum()) {
                Cell cell = row.createCell(colNum++, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(styleList.get(colNum - 1));
                cell.setCellValue(i + 1);
            }

            RowMap data = dataList.get(i);
            for (ExcelColumn column : sheetCfg.getColumns()) {
                Cell cell = row.createCell(colNum++, column.getType());
                cell.setCellStyle(styleList.get(colNum - 1)); // 设置样式
                if (!data.containsKey(column.getId())) {
                    continue;
                }

                if (Cell.CELL_TYPE_NUMERIC == column.getType()) {
                    Object value = data.get(column.getId());
                    if (value instanceof Timestamp) {
                        cell.setCellValue(new Date(((Timestamp) value).getTime())); // 设置单元格的值
                    } else if (value instanceof Date) {
                        cell.setCellValue((Date) value); // 设置单元格的值
                    } else {
                        cell.setCellValue(data.$double(column.getId(), null)); // 设置单元格的值
                    }
                } else {
                    cell.setCellValue(data.$(column.getId(), "")); // 设置单元格的值
                }
            }
        }

        return rowNum;
    }

    private void adjustColumnWidth(Sheet sheet, ExcelSheet sheetCfg) {
        int colNum = 0;
        if (sheetCfg.getShowLineNum()) {
            sheet.setColumnWidth(colNum++, 10 * 256);
        }

        for (ExcelColumn column : sheetCfg.getColumns()) {
            if (column.getWidth() > 0) {
                sheet.setColumnWidth(colNum++, (int) ((column.getWidth() * 1.20) * 256));
                continue;
            }

            // 自动列宽
            int columnWidth = sheet.getColumnWidth(colNum++) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) { // 当前行未被使用过
                    row = sheet.createRow(rowNum);
                }

                Cell cell = row.getCell(colNum - 1);
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    RichTextString textStr = cell.getRichStringCellValue();
                    if (textStr != null) {
                        int length = textStr.getString().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }

            sheet.setColumnWidth(colNum - 1, (columnWidth > 255 ? 255 : columnWidth) * 256);
        }
    }

    /*
     * 标题单元格样式
     */
    private CellStyle getTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setBorderBottom(CellStyle.BORDER_THIN); // 设置底边框;
        style.setBorderLeft(CellStyle.BORDER_THIN); // 设置左边框;
        style.setBorderRight(CellStyle.BORDER_THIN); // 设置右边框;
        style.setBorderTop(CellStyle.BORDER_THIN); // 设置顶边框;

        style.setAlignment(CellStyle.ALIGN_CENTER); // 设置水平对齐的样式为居中对齐;
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 设置垂直对齐的样式为居中对齐;
        style.setWrapText(false); // 设置自动换行;

        // 设置字体
        Font font = workbook.createFont();
        style.setFont(font); // 在样式用应用设置的字体;
        font.setFontName("宋体"); // 设置字体名字
        font.setFontHeightInPoints((short) 16); // 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗

        return style;
    }

    /*
     * 列头单元格样式
     */
    private CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setBorderBottom(CellStyle.BORDER_THIN); // 设置底边框;
        style.setBorderLeft(CellStyle.BORDER_THIN); // 设置左边框;
        style.setBorderRight(CellStyle.BORDER_THIN); // 设置右边框;
        style.setBorderTop(CellStyle.BORDER_THIN); // 设置顶边框;

        style.setAlignment(CellStyle.ALIGN_CENTER); // 设置水平对齐的样式为居中对齐;
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 设置垂直对齐的样式为居中对齐;
        style.setWrapText(false); // 设置自动换行;

        // 设置字体
        Font font = workbook.createFont();
        style.setFont(font); // 在样式用应用设置的字体;
        font.setFontName("宋体"); // 设置字体名字
        font.setFontHeightInPoints((short) 12); // 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗

        return style;
    }
}
