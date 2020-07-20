package com.wx.app.game.utils.pay;


import com.google.gson.Gson;
import com.wx.app.game.config.propertiesConfig.PayConfig;
import com.wx.app.game.constant.pay.PayStringPool;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信H5支付工具类
 * 创建者 科帮网
 * 创建时间	2017年7月31日
 */
@Slf4j
@Component
public class MobileUtil {

	/**
	 * 获取用户hopenID
	 */
	public static String getOpenId(String code){
		if (code != null) {
			/*String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ "wxfdf21db7915067e8"
					+ "&secret="+ "ba8755223b3d1d3582bb332376a968b1" + "&code="
					+code + "&grant_type=authorization_code";*/

			StringBuffer url = new StringBuffer(PayStringPool.OPENID_URL);
			url.append("appid=").append(PayStringPool.MINI_APPID)
					.append("&secret=").append(PayStringPool.MINI_APP_SECRET)
					.append("&js_code=").append(code)
					.append("&grant_type=authorization_code");

			log.info("getOpenIdParams={}",url.toString());
			String returnData = getReturnData(url.toString());
			log.info("getOpenIdUrlResult={}",returnData);
			Gson gson = new Gson();
			OpenIdClass openIdClass = gson.fromJson(returnData,OpenIdClass.class);
			if (openIdClass.getOpenid() != null) {
				return openIdClass.getOpenid();
			}
		}
		return null;
	}


	public static String getReturnData(String urlString) {
		String res = "";
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url
					.openConnection();
			conn.connect();
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
   	  * 获取游戏方用户hopenID
	 */
	public static String getGameOpenId(String code){
		if (code != null) {

			StringBuffer url = new StringBuffer(PayStringPool.OPENID_URL);
			url.append("appid=").append("wx7ceb29220df286f4")
					.append("&secret=").append("0ce865f9b31412e9e397420e39987edc")
					.append("&js_code=").append(code)
					.append("&grant_type=authorization_code");

			log.info("getOpenIdParams={}",url.toString());
			String returnData = getReturnData(url.toString());
			log.info("getOpenIdUrlResult={}",returnData);
			Gson gson = new Gson();
			OpenIdClass openIdClass = gson.fromJson(returnData,OpenIdClass.class);
			if (openIdClass.getOpenid() != null) {
				return openIdClass.getOpenid();
			}
		}
		return null;
	}

	/**
	 * 回调request 参数解析为map格式
	 * @Author  科帮网
	 * @param request
	 * @return
	 * @throws Exception  Map<String,String>
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
}
