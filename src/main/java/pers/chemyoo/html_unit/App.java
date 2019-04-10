package pers.chemyoo.html_unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {
		String url = "https://www.socwall.com/";

		// HtmlUnit 模拟浏览器
		try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			webClient.getOptions().setCssEnabled(false); // 禁用css支持
			webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(30 * 1000); // 设置连接超时时间
			HtmlPage page = webClient.getPage(url);
			// webClient.waitForBackgroundJavaScript(30 * 1000); // 等待js后台执行30秒

			String pageAsXml = page.asXml();

			// Jsoup解析处理
			Document doc = Jsoup.parse(pageAsXml, url);
			Elements pngs = doc.select("img"); // [src$=.png]获取所有图片元素集
			// 此处省略其他操作
			System.out.println(doc.toString());
			pngs.forEach(e -> System.out.println(e.absUrl("src")));
		} catch (Exception e) {

		}
    }
}
