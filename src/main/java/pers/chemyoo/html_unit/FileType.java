package pers.chemyoo.html_unit;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年2月5日 下午1:52:50
 * @since 2018年2月5日 下午1:52:50
 * @description 16进制表示的文件类型
 */
public enum FileType {

	JPEG("FFD8FF", "jpeg"), 
	PNG("89504E47", "png"), 
	GIF("47494638", "gif"), 
	TIFF("49492A00", "tiff"), 
	BMP("424D", "bmp"), 
	DWG("41433130", "dwg"), 
	PSD("38425053", "psd"), 
	RTF("7B5C727466", "rtf"), 
	XML("3C3F786D6C", "xml"), 
	HTML("68746D6C3E", "html"), 
	EML("44656C69766572792D646174653A", "eml"), 
	DBX("CFAD12FEC5FD746F", "dbx"), 
	PST("2142444E", "pst"), 
	XLS_DOC("D0CF11E0", "xls/doc"), 
	MDB("5374616E64617264204A", "mdb"), 
	WPD("FF575043", "wpd"), 
	EPS("252150532D41646F6265", "eps"), 
	PDF("255044462D312E", "pdf"), 
	QDF("AC9EBD8F", "qdf"), PWL("E3828596", "pwl"), 
	ZIP("504B0304", "zip"), 
	XLSX_DOCX("504B0304", "xlsx/docx"), 
	RAR("52617221", "rar"), 
	WAV("57415645", "wav"), 
	AVI("41564920", "avi"), 
	RAM("2E7261FD", "ram"), 
	RM("2E524D46", "rmvb"), 
	MPG("000001BA", "mpg"), 
	MOV("6D6F6F76", "mov"),
	ASF("3026B2758E66CF11", "asf"), 
	MID("4D546864", "mid"), 
	UNKNOWN("undefined", "unknown");

	private String value = "";
	private String name = "";

	private FileType(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public static String getFileType(String value) {
		if (ChemyooUtils.isEmpty(value)) {
			return UNKNOWN.getName();
		}
		String fileType = UNKNOWN.getName();
		FileType[] values = values();
		String upperValue = value.toUpperCase();
		for (FileType type : values) {
			if (upperValue.startsWith(type.getValue())) {
				fileType = type.getName();
				break;
			}
		}
		return fileType;
	}
}
