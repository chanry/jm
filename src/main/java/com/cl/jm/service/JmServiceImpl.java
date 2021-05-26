package com.cl.jm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cl.jm.entity.JEResult;
import com.cl.jm.entity.JMInfo;
import com.cl.jm.entity.OMResult;
import com.cl.jm.exception.JmException;
import com.cl.jm.service.impl.JmService;
import com.cl.jm.utils.RestTemplateUtil;

/**
 * @author chenli
 *
 */
@Service
public class JmServiceImpl implements JmService {

	/**
	 * 日志记录
	 */
	protected Logger LOG = Logger.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	private String[] urls = { "https://www.justeasy.cn/su/id-@id.html", "https://www.justeasy.cn/tietu/detail/@id.html",
			"https://www.justeasy.cn/cad/id-@id.html", "https://www.justeasy.cn/softmaterial/detail/id-@id.html",
			"https://www.justeasy.cn/works/case/@id.html" };

	/**
	 * 根据ID获取溜溜网数据列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<JMInfo> findLLJmInfoList(String[] idArr) throws JmException {
		List<JMInfo> jmList = new ArrayList<JMInfo>();
		Map<String, Object> para = new HashMap<>();

		for (int i = 0; i < idArr.length; i++) {
			if (idArr[i].length() < 5) {
				continue;
			}
			para.put("kw", idArr[i]);
			String preUrl = idArr[i].trim().substring(0, 4);
			String url = "https://www.3d66.com/reshtml/" + preUrl + "/" + idArr[i].trim() + ".html";
			String html = "";
			try {
				html = RestTemplateUtil.getForObject(url, String.class, para);
			} catch (Exception e) {
				throw new JmException("请求url" + url + "发生错误");
			}

			Document doc = Jsoup.parse(html);
			Elements ets = doc.getElementsByClass("detail-discount");
			if (!ets.isEmpty()) {
				Element et = ets.get(0);
				Elements span = et.getElementsByTag("span");
				String money = span.get(0).html();
				Elements nameSpan = doc.getElementsByClass("info-model-name");
				JMInfo jmInfo = new JMInfo(idArr[i], money, nameSpan.get(0).text(), url);
				jmList.add(jmInfo);
			}
		}
		return jmList;
	}

	/**
	 * 根据ID获取建E网数据列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<JMInfo> findJEJmInfoList(String[] idArr) throws JmException {
		List<JMInfo> jmList = new ArrayList<JMInfo>();
		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> para = new HashMap<>();

		// 遍历每个ID，逐个类型搜索，直到搜索到结果或者搜索完毕
		for (int i = 0; i < idArr.length; i++) {
			// 3D模型money数据是从接口获取
			String url = "https://www.justeasy.cn/3d/id-" + idArr[i].trim() + ".html";
			headers.add("referer", url);
			HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<String> resEntity = restTemplate.exchange("https://www.justeasy.cn/home/model/getmodeldetails?id=" + idArr[i],
					HttpMethod.GET,
					requestEntity, String.class);

			String jsonStr = resEntity.getBody().toString();
			JEResult jeResult = JSONObject.parseObject(jsonStr, JEResult.class);
			JMInfo jmInfo = new JMInfo(idArr[i]);
			Elements ets = new Elements();
			Elements nameEts = new Elements();
			// 如果查不到结果继续从下一种类型遍历查找
			if (jeResult.getList() == null || jeResult.getList().getId() == null) {
				getMoneyByUrl(ets, nameEts, 0, jmInfo, idArr[i], para);
			} else {
				jmInfo.setMoney(jeResult.getList().getMoney_h());
				// 从3D模型api获取金币数后，再获取html获取name节点
				try {
					resEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
				} catch (Exception e) {
					throw new JmException("请求url" + url + "发生错误");
				}

				String html = resEntity.getBody().toString();

				Document doc = Jsoup.parse(html);
				ets = doc.getElementsByClass("model-name");
				nameEts = ets.get(0).getElementsByTag("h2");
				String name = nameEts.get(0).text();
				jmInfo.setName(name.substring(0, name.indexOf("ID")));
				jmInfo.setUrl(url);

			}
			jmList.add(jmInfo);
		}
		return jmList;
	}

	/**
	 * 遍历搜索3U、贴图、施工图、设计案例是否有对应ID的模型
	 * 
	 * @param ets
	 * @param index
	 * @param money
	 * @param id
	 * @param para
	 * @param moneys
	 * @return
	 */
	private void getMoneyByUrl(Elements moneyEts, Elements nameEts, int index, JMInfo jmInfo, String id, Map<String, Object> para) {
		if (index <= 4) {
			if (moneyEts.isEmpty()) {
				para.put("id", id);
				String url = urls[index].replace("@id", id);
				jmInfo.setUrl(url);
				String jsonStr = "";
				try {
					jsonStr = RestTemplateUtil.getForObject(url, String.class, para);
				} catch (Exception e) {
					LOG.error("请求url" + url + "发生错误");
				}

				Document doc = Jsoup.parse(jsonStr);
				if (index != 4) {
					moneyEts = doc.getElementsByClass("price");
					nameEts = doc.getElementsByClass("tit");
					index++;
					getMoneyByUrl(moneyEts, nameEts, index, jmInfo, id, para);
				} else {
					Element idEt = doc.getElementById("div_confirm_tpl");
					moneyEts = idEt.getElementsByClass("org");
					String money = moneyEts.get(1).text();
					jmInfo.setMoney(money.substring(0, money.indexOf("金点")));

					nameEts = doc.getElementsByTag("title");
					jmInfo.setName(nameEts.get(0).text());
				}

			} else {
				Element spanTag2 = moneyEts.get(0);
				jmInfo.setMoney(spanTag2.text());

				Elements nameSpan = nameEts.get(0).getElementsByTag("span");
				jmInfo.setName(nameSpan.get(0).text());

			}
		}
	}

	/**
	 * 根据ID获取欧模网数据列表
	 * 
	 * @param ids
	 * @return
	 * @throws JmException 
	 */
	public List<JMInfo> findOMJmInfoList(String[] idArr) throws JmException {
		List<JMInfo> jmList = new ArrayList<JMInfo>();
		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> para = new HashMap<>();

		for (int i = 0; i < idArr.length; i++) {
			// 获取money从接口
			para.put("id", idArr[i]);
			try {
				String url = "https://www.om.cn/models/detail/" + idArr[i].trim() + ".html";
				headers.add("referer", url);
				HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
				ResponseEntity<String> resEntity = restTemplate.exchange("https://www.om.cn/v55/model/detail?id=" + idArr[i],
						HttpMethod.GET,
						requestEntity, String.class);
				
				//String jsonStr = RestTemplateUtil.getForObject("https://www.om.cn/v55/model/detail", String.class, para);
				OMResult omResult = JSONObject.parseObject(resEntity.getBody().toString(), OMResult.class);
				JMInfo jmInfo = new JMInfo(idArr[i], omResult.getData().getMoney());
				// 获取name从html
				String html = RestTemplateUtil.getForObject(url, String.class, para);
				Document doc = Jsoup.parse(html);
				Element h1 = doc.getElementById("foo");
				String name = h1.text();
				jmInfo.setUrl(url);
				jmInfo.setName(name);
				jmList.add(jmInfo);
			} catch (HttpClientErrorException e) {
				throw new JmException("请求网站错误："+ e.getMessage());
			}
		}
		return jmList;
	}

	/**
	 * 根据ID获取知末网数据列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<JMInfo> findZMJmInfoList(String[] idArr) throws JmException {
		List<JMInfo> jmList = new ArrayList<JMInfo>();

		Map<String, Object> para = new HashMap<>();

		for (int i = 0; i < idArr.length; i++) {
			para.put("id", idArr[i]);
			String url = "https://tietu.znzmo.com/tietu/" + idArr[i] + ".html";
			String html = "";
			try {
				html = RestTemplateUtil.getForObject(url, String.class, para);
			} catch (Exception e) {
				throw new JmException("请求url" + url + "发生错误");
			}

			Document doc = Jsoup.parse(html);
			Elements ets = doc.getElementsByClass("price___owsyh");
			Element et = ets.get(0);
			//Elements iTag = et.getElementsByTag("i");
			//String money = et.text().substring(1, et.text().indexOf("金币"));
			String money = et.text();
			Elements h1Ets = doc.getElementsByClass("title___beKma");
			Element h1Et = h1Ets.get(0);

			JMInfo jmInfo = new JMInfo(idArr[i], money, h1Et.getElementsByAttribute("title").text(), url);
			jmList.add(jmInfo);
		}

		return jmList;
	}

}
