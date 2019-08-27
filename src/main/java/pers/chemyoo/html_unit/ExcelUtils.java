package pers.chemyoo.html_unit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = ".xlsx";
	public static Logger log = Logger.getGlobal();
	
	
	public static class Excel {
		
		/**
		 * 通用导出Excel表格方法
		 * 
		 * @param outTarget      导出目标地址
		 * @param titles         表格标题
		 * @param list           表体数据
		 * @param fillEmptyValue 空值填充
		 * @param allStringValue 是否全以字符串类型导出
		 * @throws IllegalArgumentException 没有标题行时抛出 IllegalArgumentException（titles
		 *                                  length is zero）
		 */
		public static void commonExportData(OutputStream outTarget, List<String> titles,
				List<Map<String, Object>> list, Object fillEmptyValue, boolean allStringValue)
				throws IllegalArgumentException {
			// 声明一个工作薄
			if (list == null || titles == null) {
				throw new NullPointerException("导出的数据或标题行不能为 null");
			}
			if (titles.isEmpty()) {
				throw new IllegalArgumentException("请设置标题行！");
			}
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet("sheet1");
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 25);
			sheet.setDefaultRowHeight((short) 300);
			sheet.createFreezePane(0, 1, 0, 1);

			/* 生成一个样式 */
			CellStyle style = workbook.createCellStyle();
			/* 设置这些样式 */
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			/* 生成一个字体 */
			Font font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 11);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			/* 把字体应用到当前的样式 */
			style.setFont(font);
			style.setAlignment(CellStyle.ALIGN_LEFT);

			// 产生表格标题行
			Row row = sheet.createRow(0);
			int i = 0;
			for (String title : titles) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(title);
				i++;
			}

			Cell cell = null;
			int index = 1;
			Object cellValue = null;
			style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_LEFT);
			try {
				for (Map<String, Object> map : list) {
					i = 0;
					row = sheet.createRow(index);
					for (String title : titles) {
						cell = row.createCell(i);
						cell.setCellStyle(style);// 设置左对齐
						cellValue = map.get(title);
						if (cellValue == null || "".equals(cellValue)) {
							cellValue = fillEmptyValue;
						}
						// 单元格值全为字符类型显示
						if (allStringValue) {
							cell.setCellValue(cellValue + "");
						} else {
							// 单元格值不全为字符类型显示
							if (cellValue instanceof Double) {
								cell.setCellValue((Double) cellValue);
							} else if (cellValue instanceof Date) {
								cell.setCellValue((Date) cellValue);
							} else if (cellValue instanceof Calendar) {
								cell.setCellValue((Calendar) cellValue);
							} else if (cellValue instanceof Boolean) {
								cell.setCellValue((Boolean) cellValue);
							} else if (cellValue instanceof Integer) {
								cell.setCellValue(((Integer) cellValue).doubleValue());
							} else if (cellValue instanceof Long) {
								cell.setCellValue(((Long) cellValue).doubleValue());
							} else {
								cell.setCellValue(cellValue + "");
							}
						}
						i++;
					}
					index++;
				}
				workbook.write(outTarget);
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}
		
		public static List<String> getTitles(String path){
			InputStream is = null;
			Workbook workbook = null;
			List<String> title = new ArrayList<>();
			try {
				if (isNotEmpty(path)) {
					if (path.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
						is = new FileInputStream(path);
						workbook = new HSSFWorkbook(is);
					} else if (path.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
						is = new FileInputStream(path);
						workbook = new XSSFWorkbook(is);
					}
				}
				if (workbook != null) {
					int numSheet = workbook.getNumberOfSheets();
					if (numSheet < 1) {
						throw new IOException("读取Excel表格出现异常,表格中没有可用的Sheet...");
					}
					Sheet sheet = workbook.getSheetAt(0);
					// 获取标题行
					int rowNum = sheet.getLastRowNum();
					Row row;
					if (rowNum > 1) {
						row = sheet.getRow(0);
						for (int i = 0, size = row.getLastCellNum(); i < size; i++) {
							title.add(row.getCell(i).getStringCellValue());
						}
					}
				} 
			} catch (Exception e) {
				log.info(e.getMessage());
			}
			return title;
		}
		
		/** 通用读取Excel表格的方法，只读第一页 */
		@SuppressWarnings("deprecation")
		public static List<Map<String, Object>> commonReadExcelData(String path, boolean hasTitle) {
			InputStream is = null;
			Workbook workbook = null;
			List<Map<String, Object>> list = new ArrayList<>();
			try {
				if (isNotEmpty(path)) {
					if (path.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
						is = new FileInputStream(path);
						workbook = new HSSFWorkbook(is);
					} else if (path.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
						is = new FileInputStream(path);
						workbook = new XSSFWorkbook(is);
					}
				}
				if (workbook != null) {
					int numSheet = workbook.getNumberOfSheets();
					if (numSheet < 1) {
						throw new IOException("读取Excel表格出现异常,表格中没有可用的Sheet...");
					}
					Sheet sheet = workbook.getSheetAt(0);
					if (sheet == null) {
						return list;
					}
					// 获取标题行
					List<String> title = new ArrayList<>();
					int rowNum = sheet.getLastRowNum();
					Row row;
					Map<String, Object> mapRow = null;
					if (rowNum > 1) {
						row = sheet.getRow(0);
						for (int i = 0, size = row.getLastCellNum(); i < size; i++) {
							if(hasTitle) {
								title.add(row.getCell(i).getStringCellValue());
							} else {
								title.add(Integer.toString(i));
							}
						}
					}
					if (rowNum > 2) {
						for (int i = 1; i < rowNum; i++) {
							row = sheet.getRow(i);
							mapRow = new HashMap<>();
							for (int k = 0, size = row.getLastCellNum(); k < size; k++) {
								Cell cell = row.getCell(k);
								String cellValue = "";
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										// 如果是date类型则 ，获取该cell的date值
										cellValue = TimeUtils.convertDateToString(
												HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), "yyyy-MM");
									} else {
										cell.setCellType(Cell.CELL_TYPE_STRING);
										cellValue = cell.getStringCellValue();
									}
								} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
									cellValue = String.valueOf(cell.getBooleanCellValue());
								} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
									cellValue = String.valueOf(cell.getCellFormula());
								} else {
									cellValue = cell.getStringCellValue();
								}
								mapRow.put(title.get(k), cellValue);
							}
							list.add(mapRow);
						}
					}

				}
			} catch (IOException e) {
				log.info(e.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
				workbook = null;
			}
			return list;
		}
		
		/** 处理EXCEL导出时，IE浏览器文件名乱码的问题 */
		public static void dealMessyCode(HttpServletResponse response, HttpServletRequest request, String fileName) {
			if (fileName == null) {
				throw new NullPointerException("文件名称不能为NULL");
			}
			fileName += TimeUtils.convertDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd");
			if (!fileName.toLowerCase().endsWith(OFFICE_EXCEL_2003_POSTFIX) && 
					!fileName.toLowerCase().endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
				fileName += OFFICE_EXCEL_2010_POSTFIX;
			}
			response.reset();
			String userAgent = request.getHeader("user-agent");
			try {
				// 处理IE浏览器文件名乱码的问题
				if (userAgent != null && (userAgent.indexOf("Firefox") >= 0 
						|| userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0)) {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				} else {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				}
				response.setContentType("application/msexcel");
			} catch (UnsupportedEncodingException e) {
				log.info(e.getMessage());
			}
		}
	}
	
	
	public static class CSV {
	
		/** 处理EXCEL导出时，IE浏览器文件名乱码的问题 */
		public static void dealMessyCode(HttpServletResponse response, HttpServletRequest request, String fileName) {
			if (fileName == null) {
				throw new NullPointerException("文件名称不能为NULL");
			} else if(!fileName.toLowerCase().endsWith(".csv")) {
				fileName += TimeUtils.convertDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd") + ".csv";
			}
			response.reset();
			String userAgent = request.getHeader("user-agent");
			try {
				// 处理IE浏览器文件名乱码的问题
				if (userAgent != null && (userAgent.indexOf("Firefox") >= 0 
						|| userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0)) {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
				} else {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				}
				response.setContentType("text/csv;charset=gbk");
			} catch (UnsupportedEncodingException e) {
				log.info(e.getMessage());
			}
		}
		
		public static void commonExportData(OutputStream outTarget, List<String> titles,
				List<Map<String, Object>> list, Object fillEmptyValue) {
			if (list == null || titles == null) {
				throw new NullPointerException("导出的数据或标题行不能为 null");
			}
			if (titles.isEmpty()) {
				throw new IllegalArgumentException("请设置标题行！");
			}
			// 新建临时文件
			String name = DigestUtils.md5Hex(TimeUtils.getNow().toString()) + ".csv";
			File csvTempFile = new File("/data/temp/", name);
			File parent = csvTempFile.getParentFile();
			if (parent != null && !parent.exists()) {
			    parent.mkdirs();
			}
			boolean created = true;
			try (OutputStream out = new FileOutputStream(csvTempFile);
				 Writer writer = new OutputStreamWriter(out, "GBK");
				 BufferedWriter csvWriter = new BufferedWriter(writer, 1024)){
				csvTempFile.createNewFile();
				writeTitle(titles, csvWriter);
				for(Map<String, Object> data : list) {
					writeLine(data, titles, csvWriter, fillEmptyValue);
				}
				csvWriter.flush();
			} catch (IOException e) {
				log.info(e.getMessage());
				created = false;
			} finally {
				// 如果文件创建成功进行写入目标流中
				if(created) {
					try {
						outTarget.write(FileUtils.readFileToByteArray(csvTempFile));
					} catch (IOException e) {
						log.info(e.getMessage());
					}
				}
				csvTempFile.delete();
			}
		}
		
		/**
	     * 写一行数据方法
	     * @param row
	     * @param csvWriter
	     * @throws IOException
	     */
	    private static void writeLine(Map<String, Object> data, List<String> titles, BufferedWriter csvWriter, Object fillEmptyValue) throws IOException {
	        // 写入文件头部
	    	StringBuffer sb = new StringBuffer();
	    	int start = 0;
	    	int length = titles.size();
	        for (String title : titles) {
	        	Object value = data.get(title);
	        	if(value == null) {
	        		value = fillEmptyValue;
	        	}
	        	sb.append(value);
	        	start ++;
	        	if(start < length) {
	        		sb.append(",");
	        	}
	        }
	        csvWriter.write(sb.toString());
	        csvWriter.newLine();
	    }
	    
	    private static void writeTitle(List<String> titles, BufferedWriter csvWriter) throws IOException {
	        // 写入文件头部
	    	StringBuffer sb = new StringBuffer();
	    	int start = 0;
	    	int length = titles.size();
	        for (String title : titles) {
	        	start ++;
	        	sb.append(title);
	        	if(start < length) {
	        		sb.append(",");
	        	}
	        }
	        csvWriter.write(sb.toString());
	        csvWriter.newLine();
	    }
	}

	private static boolean isNotEmpty(String str) {
		return !(str == null || str.trim().length() == 0);
	}
}
