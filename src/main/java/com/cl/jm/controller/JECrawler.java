package com.cl.jm.controller;

import java.io.File;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.UrlUtils;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.util.ExceptionUtils;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;
import okhttp3.Request;

public class JECrawler extends BreadthCrawler {
	
	File baseDir = new File("E:\\images\\je");
	
	static String website = "https://www.justeasy.cn";
	
	public JECrawler(String crawlPath) {
		super(crawlPath, true);
		// TODO Auto-generated constructor stub
		
		//只有在autoParse和autoDetectImg都为true的情况下
        //爬虫才会自动解析图片链接
        //getConf().setAutoDetectImg(true);

        //如果使用默认的Requester，需要像下面这样设置一下网页大小上限
        //否则可能会获得一个不完整的页面
        //下面这行将页面大小上限设置为10M
        //getConf().setMaxReceiveSize(1024 * 1024 * 10);

        //添加种子URL
        addSeed(website);
        //限定爬取范围
        addRegex(website+"/3dmodel/.*");
        addRegex("-.*#.*");
        addRegex("-.*\\?.*");
        //设置线程数
        setThreads(1);

	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		  //根据http头中的Content-Type信息来判断当前资源是网页还是图片
        String contentType = page.contentType();
        System.out.println("抓取连接地址为="+page.url());
        if (contentType == null) {
            return;
        } else if (contentType.contains("html")) {
            //如果是网页，则抽取其中包含图片的URL，放入后续任务
            Elements imgs = page.select("img[src]");
            System.out.println("网页地址="+page.url());
            System.out.println(page.html());
            for (Element img : imgs) {
                String imgSrc = img.attr("abs:src");
                if (imgSrc.indexOf("thumb") < 0 && imgSrc.startsWith("https://big.justeasy.cn/")) {
                	String id = "";
                	String name = "";
                	Elements idEle = page.select("span[class='tit']");
                	if (!idEle.isEmpty()) {
                		id = idEle.text();
                		//name = page.select("div[class='info-title clearfix']").first().child(0).text();
                		System.out.println("模型id="+id);
                	}
                	imgSrc = imgSrc+"?id="+id;
                    next.add(imgSrc);
                    System.out.println("图片地址为"+ imgSrc);
                }
            }
        } else if (contentType.startsWith("image")) {
            //如果是图片，直接下载
            String extensionName = contentType.split("/")[1];
            try {
                byte[] image = page.content();
                //限制文件大小 10k
                if (image.length < 10240) {
                    return;
                }
                System.out.println("新的图片地址为111111111111111111111111111="+page.url());
                String fileName = page.url().split("\\?")[1];
                if(com.cl.jm.utils.StringUtil.isEmpty(fileName)) {
                	fileName = String.format("%s.%s", MD5Utils.md5(image), extensionName);
                } else {
                	fileName = String.format("%s.%s", fileName, extensionName);
                }
                //根据图片MD5生成文件名
                File imageFile = new File(baseDir, fileName);
                FileUtils.write(imageFile, image);
                System.out.println("保存图片 " + page.url() + " 到 " + imageFile.getAbsolutePath());
            } catch (Exception e) {
                ExceptionUtils.fail(e);
            }
        }

	}
	
	// 主要解决下载图片出现403的问题
    // 自定义的请求插件
    // 可以自定义User-Agent和Cookie
    public static class MyRequester extends OkHttpRequester {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36";

        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
        	
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
            return super.createRequestBuilder(crawlDatum)
                   .removeHeader("User-Agent")  //移除默认的UserAgent
                   .addHeader("Referer", website)
                   .addHeader("User-Agent", userAgent);
        }
    }
    
    public static void main(String[] args) throws Exception {
    	//crawl为日志目录
        JECrawler demoImageCrawler = new JECrawler("crawl");
        demoImageCrawler.setRequester(new MyRequester());
        //设置为断点爬取，否则每次开启爬虫都会重新爬取
        demoImageCrawler.setResumable(false);
        //爬取深度
        demoImageCrawler.start(5);
    }


}
