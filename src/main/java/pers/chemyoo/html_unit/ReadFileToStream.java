package pers.chemyoo.html_unit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReadFileToStream {

	private InputStream inputStream;

	public ReadFileToStream(String filePath) {
		try {
			// 读取文件为文件流
			FileInputStream fileInput = new FileInputStream(filePath);
			inputStream = new BufferedInputStream(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ReadFileToStream(File file) {
		try {
			// 读取文件为文件流
			FileInputStream fileInput = new FileInputStream(file);
			inputStream = new BufferedInputStream(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void closeQuietly() {
		IOUtils.closeQuietly(inputStream);
	}

}
