package pers.chemyoo.html_unit;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String url = "http://www.jxyycg.cn/yzxt/publicity/view?id=e749d2479cb14521a5419623fa82e22e&pageNo=%d";

		// HtmlUnit 模拟浏览器
		try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			webClient.getOptions().setCssEnabled(false); // 禁用css支持
			webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(30 * 1000); // 设置连接超时时间
			List<Map<String, Object>> list = new ArrayList<>();
			List<String> title = new ArrayList<>();
			for(int i = 1; i <= 9; i ++) {
				String href = String.format(url, i);
				HtmlPage page = webClient.getPage(href);
				String pageAsXml = page.asXml();
				// Jsoup解析处理
				Document doc = Jsoup.parse(pageAsXml, href);
				Elements table = doc.select(".bor_der"); // [src$=.png]获取所有图片元素集
				if(i == 1) {
					Elements thead = table.select("thead");
					Elements th = thead.select("th");
					th.forEach(e -> title.add(e.ownText()));
				}
				Elements tbody = table.select("tbody");
				Elements trs = tbody.select("tr");
				trs.forEach(e -> {
					Map<String, Object> map = new HashMap<>();
					Elements tds = e.select("td");
					tds.forEach(e1 -> {
						map.put(title.get(map.size()), e1.ownText());
					});
					list.add(map);
				});
			}
			OutputStream outTarget = new FileOutputStream("D:/流通集团型企业内部子公司的“两票制”认定结果.xlsx");
			ChemyooUtils.commonExportData2Excel(outTarget , title, list, "", true);
			ExcelUtils.CSV.commonExportData(outTarget, title, list, "");
			outTarget.flush();
			outTarget.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
