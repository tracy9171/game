package com.wx.app.game.pay.paynotify.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.constant.pay.PayStringPool;
import com.wx.app.game.pay.paynotify.WxNotifyService;
import com.wx.app.game.service.payService.OrderPayNotifyService;
import com.wx.app.game.utils.pay.PayCommonUtil;
import com.wx.app.game.utils.pay.PayNotifyUtils;
import com.wx.app.game.utils.pay.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信支付回调
 */
@Service
@Slf4j
public class WxNotifyServiceImpl implements WxNotifyService {

    @Autowired
    private OrderPayNotifyService orderPayNotifyServiceImpl;

    @Override
    public void weixin_notify(HttpServletRequest request, HttpServletResponse response) {
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
            Map<String, String> notifyData = XMLUtil.doXMLParse(sb.toString());
            log.info("wx_notify_result={}",notifyData);
            // 过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            Iterator it = notifyData.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = notifyData.get(parameter);

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            // 账号信息
            boolean tenpaySign = PayCommonUtil.isTenpaySign("UTF-8", packageParams, PayStringPool.API_KEY);
            if (!tenpaySign){
                log.info("微信支付回调通知签名验证失败");
                return ;
            }
            if (!StringPools.SUCCESS.equals(packageParams.get("result_code"))){
                log.info("支付失败,错误信息：{}",packageParams.get("err_code"));
                PayNotifyUtils.response(response,R.failed("报文为空"));
                return;
            }
            String orderNo = (String) packageParams.get("out_trade_no");
            //更新订单状态成功，
            R r = orderPayNotifyServiceImpl.toDoWxOrderNotify(orderNo);
            PayNotifyUtils.response(response,r);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
