package tieba;



import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import java.util.Timer;
import java.util.TimerTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	private static final String TIEBA_PRE_URL = "http://tieba.baidu.com/f?ie=utf-8&fr=search&kw=";
	private static final String LIKE_INDEX_URL = "http://tieba.baidu.com/f/like/mylike";
	private static final String COOKIES = "";
	
	public static void main(String[] args) throws Exception{
		Document doc = getDocument(LIKE_INDEX_URL,COOKIES);
		int pageCount = getMaxPageIndex(doc);
		List<TBInfo> infos = getTieBas(pageCount);
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:MM:ss");
		long startTime = System.currentTimeMillis();
		
		sb.append("<span style=\"color:rgb(255,0,0);\"><b>" +sdf1.format(new Date())+ "</b></span><br><br>");
		sb.append(sdf2.format(startTime) + "¿ªÊ¼Ç©µ½");
		sb.append("<br>--------------------------------------------------<br>");
//		sign("Ã©Ò°°®ÒÂ","5310d2e462465c261480404481");
		for(TBInfo info:infos){
			System.out.println(info.getTitle()+":"+getTbs(info.getTitle()));
			String result = sign(info.getTitle(),getTbs(info.getTitle()));
			sb.append(result + "<br>");
		}
		
		long endTime = System.currentTimeMillis();
		sb.append("-----------------------------------------<br>");
		sb.append(sdf2.format(endTime)+"Ç©µ½½áÊø£ººÄÊ± "+(endTime-startTime) + "ºÁÃë<br>");
	}
	
	private static String sign(String tbName,String tbs){
		String result = null;
		Document doc = null;
		String tbName2 = null;
		try {
			tbName2 = URLEncoder.encode(tbName,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			doc = Jsoup.connect("http://tieba.baidu.com/sign/add")
					.header("Accept", "application/json, text/javascript, */*; q=0.01")
					.header("Accept-Language", "zh-CN,zh;q=0.8")
					.cookie("Cookie", COOKIES)
					.timeout(100000)
					.data("ie","utf-8")
					.data("kw",tbName2)
					.data("tbs",tbs)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36")
					.post();
			
			JSONObject json = new JSONObject(doc.text());
			
			result = tbName + "---------->" + Result.getName(Integer.parseInt(json.get("no").toString()));
			System.out.println(result+"-----"+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static String getTbs(String tibName){
		String tbs = null;
//		tibName = "Ã©Ò°°®ÒÂ";
		Document doc = getDocument(TIEBA_PRE_URL+tibName, COOKIES);
		Elements eles = doc.getElementsByTag("script");
//		System.out.println(eles);
		for(Element ele:eles){
			if(ele.outerHtml().contains("PageData.user")&&!ele.outerHtml().contains("Bigpipe")){
				ScriptEngineManager sem = new ScriptEngineManager();
				ScriptEngine se = sem.getEngineByName("js");
				StringBuffer sb = new StringBuffer();
				
				sb.append(ele.html());
				sb.append("var mytbs = PageData.tbs;");
				
				try {
					se.eval(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Object obj = se.get("mytbs");
				tbs = (String)obj;
			}
		}
		return tbs;
	}
	
	/**
	 * @param pageCount
	 * @return
	 */
	private static List<TBInfo> getTieBas(int pageCount){
		List<TBInfo> list = new ArrayList<TBInfo>();
		Document doc = null;
		for(int i = 1 ;i <= pageCount;i++){
			doc = getDocument(LIKE_INDEX_URL + "?&pn=" + i,COOKIES);
			Element allTieBas = doc.select("div.forum_table").first();
			Elements tieba = allTieBas.select("tr");
//			System.out.println(tieba.get(1));
			for(int j=1;j<tieba.size();j++){
				Element ele = tieba.get(j);
				TBInfo info = new TBInfo();
				info.setTitle(ele.select("a").attr("title"));
				info.setUrl(ele.select("a").attr("href"));
				info.setBadgelv(Integer.parseInt(ele.select("a").select("div.like_badge_lv").text()));
				info.setBadgeTitle(ele.select("a").select("div.like_badge_title").text());
				info.setExp(Integer.parseInt(ele.select("a.cur_exp").text()));
				
				list.add(info);
			}
		}
		
		return list;
	}
	
	private static int getMaxPageIndex(Document doc){
		int pageCount = -1;
		Element element = doc.select("div.pagination").first();
//		System.out.println(element);
		String maxPageIndexUrl = null;
		Elements pages = element.select("a");
//		System.out.println(pages);
		for(Element page : pages){
			if(page.text().equals("Î²Ò³")){
//				System.out.println(page);
				maxPageIndexUrl = page.absUrl("href");//»ñµÃ¾ø¶ÔµØÖ·
//				System.out.println(maxPageIndexUrl);
				
			}
		}
		pageCount = Integer.parseInt(maxPageIndexUrl.split("pn=")[1]);
		return pageCount;
	}
	
	private static Document getDocument(String url,String cookie){
		Document doc = null;
		try {
			doc = Jsoup.connect(url)
			.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
			.header("Accept-Language", "zh-CN,zh;q=0.8")
			.cookie("cookie",cookie)
			.timeout(10000)
			.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36")
			.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	

}
