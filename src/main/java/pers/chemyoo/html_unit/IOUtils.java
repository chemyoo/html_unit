package pers.chemyoo.html_unit;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;


/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年4月16日 上午9:30:23
 * @since 2018年4月16日 上午9:30:23
 * @description 类说明
 */
public class IOUtils {
	private IOUtils() {
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
	
	public static File validateFileName(File file) throws IOException {
		String fileContent = ChemyooUtils.getFileContent(file);
		String fileType = FileType.getFileType(fileContent);
		String realName = file.getPath();
		String fileExt = StringUtils.EMPTY;
		if(realName.contains(".")) {
			fileExt = file.getName().substring(file.getName().lastIndexOf('.') + 1);
		}
		if(!realName.endsWith(fileType) && !(FileType.XLSX_DOCX.getName().contains(fileExt) 
				&& fileType == FileType.ZIP.getName())) {
			realName = realName.replace(fileExt, "") + fileType;
			FileUtils.copyFile(file, new File(realName));
			FileUtils.deleteQuietly(file);
		}
		return new File(realName);
	}
}
