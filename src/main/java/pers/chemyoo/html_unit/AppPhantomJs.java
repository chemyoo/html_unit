package pers.chemyoo.html_unit;
/** 
 * @author Author : jianqing.liu
 * @version version : created time：2019年4月10日 下午4:17:35 
 * @since since from 2019年4月10日 下午4:17:35 to now.
 * @description class description
 */

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppPhantomJs {

	private static Random random = new Random();

	public static void main(String[] args) {
		// 设置必要参数
		DesiredCapabilities config = new DesiredCapabilities();
		// ssl证书支持
		config.setCapability("acceptSslCerts", true);
		// 截屏支持
		config.setCapability("takesScreenshot", true);
		// css搜索支持
		config.setCapability("cssSelectorsEnabled", true);
		// js支持
		config.setJavascriptEnabled(true);
		// 驱动支持（第二参数表明的是你的phantomjs引擎所在的路径）
		// 压缩包在lib文件夹下
		config.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"/software/phantomjs-home/phantomjs-for-windows/bin/phantomjs.exe");
		config.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "viewportSize", "{width: 1024, height: 600}");
		config.getBrowserName();
		// 创建无界面浏览器对象
		PhantomJSDriver driver = initDriver(config)[random.nextInt(3)];
		
//		WebDriverWait wait = new WebDriverWait(driver, 10)
		//开始打开网页，等待输入元素出现
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("")))
		// Jsoup处理
//		Document document = Jsoup.parse(driver.getPageSource())

		// 设置隐性等待（作用于全局）
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// 打开页面
		driver.get("https://www.socwall.com/");
		System.out.println("网站解析完成...");
		// 查找元素
		WebElement element = driver.findElement(By.tagName("body"));

		for (WebElement ele : element.findElements(By.cssSelector("img"))) {
			System.out.println(ele.getAttribute("src"));
		}

		for (WebElement ele : element.findElements(By.cssSelector("a[href]"))) {
			System.out.println("a[href] = " + ele.getAttribute("href"));
		}
		File imgFile = driver.getScreenshotAs(OutputType.FILE);
		System.out.println(imgFile.getAbsolutePath());
		driver.close();
		driver.quit();
	}
	
	public static PhantomJSDriver[] initDriver(DesiredCapabilities config) {
		String[] userAgent = new String []{"Mozilla/4.0","Mozilla/5.0","Opera/9.80"};
		PhantomJSDriver[] drivers = new PhantomJSDriver[userAgent.length];
		for(int i = 0, len = drivers.length; i < len; i++) {
			config.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent[i]);
			config.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent[i]);
			drivers[i] = new PhantomJSDriver(config);
		}
		return drivers;
	}

}
