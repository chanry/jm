package com.cl.jm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "19809127";
    public static final String API_KEY = "AyPFnUVKT8yv3VnGHsYa161t";
    public static final String SECRET_KEY = "54vELFtv0x8RgzzcgGdKcWL6b94NRIys";
    public static final String webSite[] = new String[]{"溜溜", "建E", "欧模", "知末"};


    public static void main(String[] args) throws IOException {
        // 初始化一个AipImageClassify
    	AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        String path = "E:\\om.jpeg";
        JSONObject res = client.webImage(FileUtils.readFileToByteArray(new File(path)), options);
//        List<ImgInfo> infoList = new ArrayList<ImgInfo>();
//        infoList = JSONArray.parseArray(res.get("words_result").toString(), ImgInfo.class);
//        String data[] = filterList(infoList);
//        System.out.println(data[0] + "," + data[1]);
        //System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(infoList));
        //String idWord =  infoList.get(infoList.size()-1).getWords();
        //System.out.println(idWord.substring(idWord.indexOf(":")+1));
       
    }
    
    /**
     * 只有连续出现网站名和ID才识别
     * @param infoList
     * @return
     */
//    public static String[] filterList(List<ImgInfo> infoList) {
//    	
//    	String data[] = new String[2];
//    	
//    	boolean isTag = false;
//    	for (int i = 0; i < infoList.size(); i ++) {
//    		ImgInfo imgInfo = infoList.get(i);
//    		String word = imgInfo.getWords();
//    		if(!isTag && isContain(word)) {
//    			isTag = true;
//    			continue;
//    		}
//    		if (isTag) {
//    			if (word.toUpperCase().indexOf("D:") > -1) {
//    				data[0] = infoList.get(i-1).getWords();
//    				data[1] = word.split(":")[1];
//    				break;
//    			}
//    		} else {
//    			isTag = false;
//    		}
//    	}
//    	
//    	return data;
//    }
    
    public static boolean isContain(String word) {
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
