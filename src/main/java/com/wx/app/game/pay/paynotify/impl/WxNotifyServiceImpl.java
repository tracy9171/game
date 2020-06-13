package com.wx.app.game.pay.paynotify.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.pay.paynotify.WxNotifyService;
import com.wx.app.game.utils.pay.ConfigUtil;
import com.wx.app.game.utils.pay.PayCommonUtil;
import com.wx.app.game.utils.pay.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


@Service
@Slf4j
public class WxNotifyServiceImpl implements WxNotifyService {


    @Override
    public R weixin_notify(HttpServletRequest request) {
        try {
            // 读取参数
            InputStream inputStream = request.getInputStream();
            StringBuffer sb = new StringBuffer();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();

            // 解析xml成map
            Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
            // 过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            // 账号信息
            String key = ConfigUtil.API_KEY; // key
            boolean tenpaySign = PayCommonUtil.isTenpaySign("UTF-8", packageParams, key);




        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

        return null;
    }
}
