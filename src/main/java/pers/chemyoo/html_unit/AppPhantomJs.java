package pers.chemyoo.html_unit;
/** 
 * @author Author : jianqing.liu
 * @version version : created time：2019年4月10日 下午4:17:35 
 * @since since from 2019年4月10日 下午4:17:35 to now.
 * @description class description
 */

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppPhantomJs {

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
		config.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"/software/phantomjs-home/phantomjs-for-windows/bin/phantomjs.exe");
		// 创建无界面浏览器对象
		PhantomJSDriver driver = new PhantomJSDriver(config);

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
		driver.close();
		driver.quit();
	}

}
