package com.cl.jm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;
import com.cl.jm.entity.ImgInfo;
import com.cl.jm.entity.JMInfo;
import com.cl.jm.entity.JsonResult;
import com.cl.jm.exception.JmException;
import com.cl.jm.service.JmServiceImpl;
import com.cl.jm.utils.StringUtil;

/**
 * @author chenli
 *
 */
@Controller
@RequestMapping(value = "/")
public class WebController extends BaseController {
	
	@Autowired
	private JmServiceImpl jmService;
	
	public static final String APP_ID = "19809127";
    public static final String API_KEY = "AyPFnUVKT8yv3VnGHsYa161t";
    public static final String SECRET_KEY = "54vELFtv0x8RgzzcgGdKcWL6b94NRIys";
    public static final String webSite[] = new String[]{"溜溜", "建E", "欧模", "知末"};
    public static final AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    public static final String pattern = "([^\\x00-\\xff]*[0-9]*[a-zA-Z]+[^\\x00-\\xff]*[:|：]*)([0-9]{6,10})";
    public static final Pattern r = Pattern.compile(pattern);
  
	
    {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

	@RequestMapping(path = "/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	/**
	 * 获取溜溜网建模价格 873096,1014333,1017699,1028062
	 * 
	 * @param request
	 * @return
	 * @throws JmException
	 */
	@ResponseBody
	@RequestMapping(value = "/getLLPrice", method = RequestMethod.GET)
	public JsonResult getLLPrice(HttpServletRequest request) throws JmException {

		String ids = getNotEmptyValue(request, "ids");
		if (StringUtil.isEmpty(ids)) {
			return result(200, "success");
		}
		String[] idArr = StringUtil.split(ids.trim(), "\n");

		List<JMInfo> moneys = jmService.findLLJmInfoList(idArr);

		return result(200, "success", moneys);
	}

	/**
	 * 获取建E网建模价格   
	 * 116089
	 * 25318
	 * 14317
	 * 1429466
	 * 41014
	 * 
	 * @param request
	 * @return
	 * @throws JmException
	 */
	@ResponseBody
	@RequestMapping(value = "/getJEPrice", method = RequestMethod.GET)
	public JsonResult getJEPrice(HttpServletRequest request) throws JmException {

		String ids = getNotEmptyValue(request, "ids");
		
		if (StringUtil.isEmpty(ids)) {
			return result(200, "success");
		}
		
		String[] idArr = StringUtil.split(ids.trim(), "\n");
		List<JMInfo> moneys = jmService.findJEJmInfoList(idArr);
		
		return result(200, "success", moneys);
	}

	

	/**
	 * 获取欧模网建模价格 1039139
	 * 
	 * @param request
	 * @return
	 * @throws JmException
	 */
	@ResponseBody
	@RequestMapping(value = "/getOMPrice", method = RequestMethod.GET)
	public JsonResult getOMPrice(HttpServletRequest request) throws JmException {

		String ids = getNotEmptyValue(request, "ids");
		
		if (StringUtil.isEmpty(ids)) {
			return result(200, "success");
		}
		
		String[] idArr = StringUtil.split(ids.trim(), "\n");
		List<JMInfo> moneys = jmService.findOMJmInfoList(idArr);

		return result(200, "success", moneys);
	}

	/**
	 * 获取知末网建模价格 254507405
	 * 
	 * @param request
	 * @return
	 * @throws JmException
	 */
	@ResponseBody
	@RequestMapping(value = "/getZMPrice", method = RequestMethod.GET)
	public JsonResult getZMPrice(HttpServletRequest request) throws JmException {

		String ids = getNotEmptyValue(request, "ids");
		
		if (StringUtil.isEmpty(ids)) {
			return result(200, "success");
		}
		
		String[] idArr = StringUtil.split(ids.trim(), "\n");
		List<JMInfo> moneys = jmService.findZMJmInfoList(idArr);
		
		return result(200, "success", moneys);
	}
	
	/**
	 * 图片上传
	 * 
	 * @param request
	 *            请求
	 * @param file
	 *            上传的文件
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@ResponseBody
	@RequestMapping(value = "/doImgUpload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonResult doImgUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
		
		if (file.isEmpty())
			return result(-1, "上传图片失败，请选择要上传的图片!");
		
//		String id = "";
//		String region = "";
		String data[] = new String[2];
		
			if (!file.getContentType().contains("image"))
				return result(-1, "上传的文件不是图片类型，请重新上传!");
			
		// 调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.webImage(file.getBytes(), options);
        List<ImgInfo> infoList = new ArrayList<ImgInfo>();
        String result = res.get("words_result").toString();
        System.out.println(result);
        infoList = JSONArray.parseArray(res.get("words_result").toString(), ImgInfo.class);
        data = filterList(infoList);
        if (data[1] != null)
        	return result(200, "上传成功！", data);
        return result(-1, "该图片无法识别");
	}
	
	/**
     * 只有连续出现网站名和ID才识别
     * 正则表达式  String pattern = [^\x00-\xff]*([a-zA-Z])+[^\x00-\xff]*[:|：][0-9]{6,10}
     * @param infoList
     * @return
     */
	private String[] filterList(List<ImgInfo> infoList) {
    	
    	String data[] = new String[2];
    	
    	boolean isTag = false;
    	for (int i = 0; i < infoList.size(); i ++) {
    		ImgInfo imgInfo = infoList.get(i);
    		String word = imgInfo.getWords();
    		if(!isTag && isContain(word)) {
    			isTag = true;
    			continue;
    		}
    		if (isTag) {
    			Matcher m =r.matcher(word);
    			if (m.find()) {
    				System.out.println("group0:" + m.group(0));
        			System.out.println("group1:" + m.group(1));
        			System.out.println("group2:" + m.group(2));
    				data[0] = infoList.get(i-1).getWords();
    				data[1] = m.group(2);
    				break;
    			}
    		} else {
    			isTag = false;
    		}
    	}
    	
    	return data;
    }
    
    private boolean isContain(String word) {
    	boolean result = false;
    	for (String web : webSite) {
    		if (word.indexOf(web) > -1) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
}
