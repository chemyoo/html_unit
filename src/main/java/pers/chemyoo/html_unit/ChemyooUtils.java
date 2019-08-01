package pers.chemyoo.html_unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * Personal utils
 * 
 * @author Chemyoo
 */
public class ChemyooUtils {

	private static final long MILLS_OF_DAY = 1000 * 60 * 60 * 24L;

	public static final String EMPTY = "";

	/** 默认文字编码 */
	private static final String DEFUALT_CHARSET = "UTF-8";

	private static final String EOF = "EOF";

	public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = ".xlsx";

	private ChemyooUtils() {
	};

	/**
	 * use to judge the collection is empty or not
	 * 
	 * @param collection
	 * @return true or false
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * use to judge the collection is empty or not ,the static funtion isEmpty's
	 * opposite
	 * 
	 * @param collection
	 * @return true or false
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return !isEmpty(collection);
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return (map == null || map.isEmpty());
	}

	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return isEmpty(map);
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * judge a class object is empty or not
	 * 
	 * @param type
	 *            is Java Type
	 * @return
	 */
	public static <T> boolean isNotEmpty(T type) {
		if (type == null)
			return false;
		Class<? extends Object> c = type.getClass();
		if (!c.isInterface()) {
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					Object obj = f.get(type);
					if (obj != null) {
						if (obj instanceof String && "".equals(((String) obj).trim())) {
							continue;
						}
						return true;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				f.setAccessible(false);
			}
		}
		return false;
	}

	/**
	 * judge a class object is empty or not
	 * 
	 * @param type
	 *            is Java Type
	 * @return
	 */
	public static <T> boolean isEmpty(T type) {
		return !isNotEmpty(type);
	}

	/**
	 * 将日期转换成指定格式的字符串
	 * 
	 * @param format
	 *            时间表现形式，例如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
	 * @param date
	 *            待格式化的日期
	 * @return 返回格式化后的日期字符串
	 */
	public static String formatDate(String format, Date date) {
		String formatStr = "";
		if (date != null) {
			if (null == format) {
				format = "EEE MMM d HH:mm:ss z yyyy";// 美国时间格式
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			formatStr = simpleDateFormat.format(date);
		}
		return formatStr;
	}

	/**
	 * 计算两个日期间隔的天数
	 * 
	 * @param first
	 *            第一个日期
	 * @param second
	 *            第二个日期
	 */
	public static long getDiffDays(Date first, Date second) {
		long mm = first.getTime() - second.getTime();
		long days = mm / MILLS_OF_DAY;
		return days;
	}

	/**
	 * 计算两个日期间隔的毫秒数
	 * 
	 * @param first
	 *            第一个日期
	 * @param second
	 *            第二个日期
	 */
	public static long getDiffMills(Date first, Date second) {
		return first.getTime() - second.getTime();
	}
	// /**
	// * 关闭计算机
	// * @param second 延迟的秒数
	// */
	// public static void powerOff(long second)
	// {
	// Runtime rt=Runtime.getRuntime();
	// try
	// {
	// if(second < 0)
	// second = 5;
	// rt.exec("shutdown.exe -s -t "+second);
	// /* 单位为秒。
	// 如果是想定时关机，可用这句：rt.exec("at 19:00 shutdown.exe -s");*/
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// /**
	// * 定时关闭计算机
	// * @param clock 关闭计算机的时间
	// * @throws ParseException
	// */
	// public static void powerOff(String clock) throws ParseException
	// {
	// Runtime rt=Runtime.getRuntime();
	// Pattern pattern = Pattern.compile("^[0-2]??[0-9][:]{1}[0-5][0-9]$");
	// Matcher matcher = pattern.matcher(clock);
	// // 字符串是否与正则表达式相匹配
	// if(matcher.matches())
	// {
	// try
	// {
	// if(clock == null)
	// clock = "00:00";
	// rt.exec("at "+ clock +" shutdown.exe -s");
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// else
	// throw new ParseException("时间格式不正确，格式为00:00或0:00,冒号为英文字符",0);
	// }
	/**
	 * // * 重新启动计算机 // * @param clock 关闭计算机的时间 // * @throws ParseException //
	 */

	// public static void restart()
	// {
	// Runtime rt=Runtime.getRuntime();
	// try
	// {
	// rt.exec("shutdown.exe -r");
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	/**
	 * <p>
	 * Adds the specified (signed) amount of time to the given calendar field, based
	 * on the calendar's rules.
	 * </p>
	 *
	 * <p>
	 * Add rule 1. The value of field after the call minus the value of field before
	 * the call is amount, modulo any overflow that has occurred in field. Overflow
	 * occurs when a field value exceeds its range and, as a result, the next larger
	 * field is incremented or decremented and the field value is adjusted back into
	 * its range.
	 * </p>
	 *
	 * <p>
	 * Add rule 2. If a smaller field is expected to be invariant, but it is
	 * impossible for it to be equal to its prior value because of changes in its
	 * minimum or maximum after field is changed, then its value is adjusted to be
	 * as close as possible to its expected value. A smaller field represents a
	 * smaller unit of time. HOUR is a smaller field than DAY_OF_MONTH. No
	 * adjustment is made to smaller fields that are not expected to be invariant.
	 * The calendar system determines what fields are expected to be invariant.
	 * </p>
	 *
	 * @throws
	 *             <p>
	 * 			IllegalArgumentException
	 *             - if field is ZONE_OFFSET, DST_OFFSET, or unknown, or if any
	 *             calendar fields have out-of-range values in non-lenient mode.
	 *             </p>
	 * @param datetime
	 *            the date or time to be added.
	 * @param datepart
	 *            the calendar datepart.
	 * @param value
	 *            the amount of date or time to be added to the field.
	 * @return Date the Date after operation
	 */
	public static Date dateAdd(Date datetime, int datepart, int value) {
		GregorianCalendar grego = new GregorianCalendar();
		grego.setTime(datetime);
		grego.add(datepart, value);
		return grego.getTime();
	}

	/**
	 * clear lists
	 * 
	 * @param list
	 */
	public static void clearList(List<?>... list) {
		if (list != null) {
			for (int i = 0, size = list.length; i < size; i++) {
				if (isNotEmpty(list[i])) {
					list[i].clear();
					list[i] = null;// 使对象变为游离态
				}
			}
		}
	}

	/**
	 * clear maps
	 * 
	 * @param map
	 */
	public static void clearMap(Map<?, ?>... map) {
		if (map != null) {
			for (int i = 0, size = map.length; i < size; i++) {
				if (isNotEmpty(map[i])) {
					map[i].clear();
					map[i] = null;// 使对象变为游离态
				}
			}
		}
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static Object toObject(Object o1, Object o2) throws ClassNotFoundException {
		if (o1 == null)
			return o2;
		Class<?> c = Class.forName(o1.getClass().getName());
		return c.cast(o2);
	}

	/**
	 * @return a uppercase character
	 */
	public static char getUppercaseLetter() {
		return (char) ((int) Math.floor(Math.random() * 26) + 65);
	}

	/**
	 * @return a lowercase character
	 */
	public static char geLowercaseLetter() {
		return (char) ((int) Math.floor(Math.random() * 26) + 97);
	}

	/**
	 * 将文件头转换成16进制字符串
	 * 
	 * @param 原生byte
	 * @return 16进制字符串
	 */
	public static String bytesToHexString(byte[] b) {

		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String str = Integer.toHexString(v);
			if (str.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(str);
		}
		return stringBuilder.toString();
	}

	/**
	 * 得到文件头
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件头
	 * @throws IOException
	 */
	public static String getFileContent(InputStream inputStream) {
		byte[] b = new byte[28];
		try {
			if (inputStream.markSupported()) {
				inputStream.mark(0);
				inputStream.read(b, 0, 28);
				inputStream.reset();
			} else {
				throw new IOException("this input stream not supports the mark!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesToHexString(b);
	}

	/**
	 * 得到文件头
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件头
	 * @throws IOException
	 */
	public static String getFileContent(File file) {
		byte[] b = new byte[28];
		ReadFileToStream reader = new ReadFileToStream(file);
		InputStream is = reader.getInputStream();
		try {
			is.read(b, 0, 28);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return bytesToHexString(b);
	}

	public void note() {
		String m = null;
		Date start = TimeUtils.convertStringToDate("yyyy-MM", "2015-01");
		Date end = TimeUtils.convertStringToDate("yyyy-MM", "2018-02");
		GregorianCalendar grego1 = new GregorianCalendar();
		GregorianCalendar grego2 = new GregorianCalendar();
		grego1.setTime(start);
		grego2.setTime(end);
		int y1 = grego1.get(Calendar.YEAR);
		int y2 = grego2.get(Calendar.YEAR);
		int m1 = grego1.get(Calendar.MONTH);
		int m2 = grego2.get(Calendar.MONTH);
		List<String> monthList = new ArrayList<String>();
		int loopStart = 0;
		int loopend = m2 + 1;
		int year = y2 - y1;
		if (year >= 1) {
			// 开始年月份
			int loop = 12 - m1 - 1;
			for (int i = 0; i <= loop; i++) {
				m = (m1 + i + 1) < 10 ? ("0" + (m1 + i + 1)) : ("" + (m1 + i + 1));
				monthList.add(y1 + "-" + m);
			}
			if (year > 1) {
				// 中间整年月份
				int loopYear = year - 1;
				for (int i = 0; i <= loopYear; i++) {
					for (int j = 1; j < 13; j++) {
						m = (j) < 10 ? ("0" + (j)) : ("" + (j));
						monthList.add((y1 + i + 1) + "-" + m);
					}
				}
			}
		} else {
			loopStart = m1;
		}
		// 结束年月份
		for (int i = loopStart; i < loopend; i++) {
			monthList.add(y2 + "-" + ((i + 1) < 10 ? ("0" + (i + 1)) : (i + 1)));
		}
		// int months = year * 12 + m2-m1;
		// for(int i = 0; i<= months; i++){
		//
		// monthList.add(TimeUtil.convertDateToString(grego1.getTime(), "yyyy-MM"));
		// grego1.add(Calendar.MONTH, 1);
		// }
	}

	/**
	 * 通用导出Excel表格方法
	 * 
	 * @param outTarget
	 *            导出目标地址
	 * @param titles
	 *            表格标题
	 * @param list
	 *            表体数据
	 * @param fillEmptyValue
	 *            空值填充
	 * @param allStringValue
	 *            是否全以字符串类型导出
	 * @throws IllegalArgumentException
	 *             没有标题行时抛出 IllegalArgumentException（titles length is zero）
	 */
	public static void commonExportData2Excel(OutputStream outTarget, List<String> titles,
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
			e.printStackTrace();
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
					title.add("ID");
					for (int i = 0, size = row.getLastCellNum(); i < size; i++) {
						title.add(row.getCell(i).getStringCellValue());
					}
				}
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}
	
	/** 通用读取Excel表格的方法，只读第一页 */
	public static List<Map<String, Object>> commonReadExcelData(String path) {
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
						title.add(row.getCell(i).getStringCellValue());
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
							if (title.isEmpty()) {
								mapRow.put(String.valueOf(k), cellValue);
							} else {
								mapRow.put(title.get(k), cellValue);
							}
						}
						list.add(mapRow);
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			workbook = null;
		}
		return list;
	}

	public static String formatDecimalDigits(long i, NumberFormatBase base) {
		StringBuilder str = new StringBuilder();
		int baseNumber = base.getNumber();
		for (long n = i; n > 0; n /= baseNumber) {
			str.append(n % baseNumber);
		}
		return str.reverse().toString();
	}

	public static String encode2UTF8(String str, String oldCharset) {
		if (str != null) {
			try {
				String temp = new String(str.getBytes(oldCharset), DEFUALT_CHARSET);
				if (temp.endsWith(EOF)) {
					return temp.substring(0, temp.length() - EOF.length());
				}
				return temp;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public static String encode(String str, String oldCharset, String newCharset) {
		if (str != null) {
			try {
				return new String((str + EOF).getBytes(oldCharset), newCharset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * Shallow Copy（潜复制）
	 * @param list
	 * @return
	 */
	public static <T> List<T> listCopyValue(List<T> list) {
		List<T> copy = new ArrayList<>();
		if(isNotEmpty(list)) {
			for(T t : list) {
				copy.add(t);
			}
		}
		return copy;
	}
}
