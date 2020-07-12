package com.wx.app.game.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.Entity.WxReplacementOrderEntity;
import com.wx.app.game.commom.CheckLoginService;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.constant.pay.PayStringPool;
import com.wx.app.game.pay.WxGamePayOrderService;
import com.wx.app.game.pay.dto.PayParamsDto;
import com.wx.app.game.service.GameOrderService;
import com.wx.app.game.service.ReplacementOrderService;
import com.wx.app.game.utils.DateUtils;
import com.wx.app.game.utils.RedisLocks;
import com.wx.app.game.utils.pay.*;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


/**
 * 微信预下单
 */
@Service
@Slf4j
public class WxGamePayOrderServiceImpl implements WxGamePayOrderService {

    @Autowired
    private GameOrderService gameOrderServiceimpl;
    @Autowired
    private ReplacementOrderService replacementOrderServiceimpl;
    @Autowired
    private CheckLoginService checkLoginService;
    private Date time =new Date();



    /**
     * 游戏预下单
     * @param dto
     * @param request
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @Override
    public R toPay(PayParamsDto dto, HttpServletRequest request) {
        log.info("advancePayment_dto={}",dto);

        RedisLocks locks = new RedisLocks(10);
        String key ="GAME:ORDER:PAY:PAY_LOCK_" + dto.getCpOrderNO();
        if (!locks.lock(key)){
            return R.failed("操作频繁，请稍后");
        }

        //登陆校验
        R r = checkLoginService.checkToken(dto.getToken(), dto.getUserId());
        if (r!=null){
            return r;
        }
        WxGameOrderEntity gameOrderByOrderNo=null;
        String orderNo="";
        try {
             gameOrderByOrderNo = gameOrderServiceimpl.getGameOrderByCpOrderNo(dto.getCpOrderNO());
            if(gameOrderByOrderNo==null){
                //生成订单
                orderNo=DateUtils.getOrderNo();
                saveOrder(dto,orderNo,1);
            }else {
                if (gameOrderByOrderNo.getOrderStatus() !=1){
                    return R.failed("订单已处理！");
                }
                //更新订单信息
                gameOrderByOrderNo.setCpOrderNo(dto.getCpOrderNO());
                gameOrderByOrderNo.setUserId(dto.getUserId());
                gameOrderByOrderNo.setRoleId(dto.getRoleId());
                gameOrderByOrderNo.setOrderMoney(dto.getPayment());
                gameOrderByOrderNo.setPayMoney(dto.getPayment());
                gameOrderByOrderNo.setUpdatedDate(LocalDateTime.now());
                gameOrderByOrderNo.setGameId(dto.getGameId());
                orderNo=gameOrderByOrderNo.getOrderNo();
                gameOrderServiceimpl.updateById(gameOrderByOrderNo);
            }
            //获取code 这个在微信支付调用时会自动加上这个参数 无须设置
            String code = dto.getCode();
            String ipAddr = AddressUtils.getIpAddr(request);
            //获取用户openID(JSAPI支付必须传openid)
            String openId = MobileUtil.getOpenId(code);
            String nonce_str = PayStringPool.NONCE_STR;
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            packageParams.put("out_trade_no", orderNo);// 商户订单号
            packageParams.put("spbill_create_ip", ipAddr);// 发起人IP地址
            packageParams.put("nonce_str", nonce_str);//随机字符串
            packageParams.put("openid", openId);//用户openID  "oKbLd4sANxfwuuj2XTmGYD9KJZCs"
            packageParams.put("body",dto.getGameName());// 商品描述
            packageParams.put("total_fee", String.valueOf(dto.getPayment().multiply(new BigDecimal("100")).setScale( 0, BigDecimal.ROUND_UP ).longValue()));// 总金额
            //组装必要参数
            getParams(packageParams);

            String requestXML = PayCommonUtil.getRequestXml(packageParams);
            log.info("requestXML={}",requestXML);
            //发起微信预下单
            String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
            log.info("resXML={}",resXml);
            Map map = XMLUtil.doXMLParse(resXml);
            String returnCode = (String) map.get("return_code");
            String returnMsg = (String) map.get("return_msg");

            if (!"SUCCESS".equals(returnCode)){
                log.info("订单号:{}错误信息:1{}",orderNo,returnMsg);
                updateOrderStatus(dto,gameOrderByOrderNo,map.get("return_msg").toString(),orderNo);
                return R.failed("下单失败！！！！");
            }
            String resultCode = (String) map.get("result_code");
            String errCodeDes = (String) map.get("err_code_des");
            if (!"SUCCESS".equals(resultCode)){
                log.info("订单号:{}错误信息:2{}",orderNo,errCodeDes);
                updateOrderStatus(dto,gameOrderByOrderNo,map.get("err_code_des").toString(),orderNo);
                return R.failed("下单失败！");
            }

            //获取预支付交易会话标识
            String prepay_id = (String) map.get("prepay_id");
            String packages = "prepay_id=" + prepay_id;
            SortedMap<Object, Object> finalpackage = new TreeMap<>();
            String timestamp = System.currentTimeMillis()+"";
            //finalpackage.put("appid",  "wx010d1ce8bf447ffe");
            finalpackage.put("appId",  "wxfdf21db7915067e8");
            finalpackage.put("timeStamp", timestamp);
            finalpackage.put("nonceStr", nonce_str);
            finalpackage.put("package", packages);
            finalpackage.put("signType", "MD5");
            //这里很重要  参数一定要正确 狗日的腾讯 参数到这里就成大写了
            //可能报错信息(支付验证签名失败 get_brand_wcpay_request:fail)
            String sign = PayCommonUtil.createSign(StringPool.UTF_8, finalpackage,PayStringPool.API_KEY);

            Map<String ,String> result = new HashMap<>();
            result.put("timeStamp", timestamp);
            result.put("nonceStr", nonce_str);
            result.put("package", packages);
            result.put("signType", "MD5");
            result.put("paySign",sign);
            result.put("orderNo",orderNo);
            log.info("预支付成功={}",result);
            return R.ok(result);
        } catch (Exception e) {
            if (gameOrderByOrderNo !=null){
                gameOrderServiceimpl.updateById(gameOrderByOrderNo);
            }else {
                orderNo=DateUtils.getOrderNo();
                saveOrder(dto,orderNo,3);
            }
           log.error("");
        }finally {
            locks.releaseLock(key);
        }
        return R.failed(new ErrorCode("服务繁忙"));
    }

    //保存订单
    private void saveOrder(PayParamsDto dto,String orderNo,int status){
        WxGameOrderEntity entity = new WxGameOrderEntity();
        entity.setOrderStatus(status);
        entity.setCpOrderNo(dto.getCpOrderNO());
        entity.setUserId(dto.getUserId());
        entity.setRoleId(dto.getRoleId());
        entity.setOrderMoney(dto.getPayment());
        entity.setPayMoney(dto.getPayment());
        entity.setPlaceOrderDate(time);
        entity.setGameId(dto.getGameId());
        entity.setOrderNo(orderNo);
        entity.setProductName(dto.getProductName());
        entity.setSystems(dto.getSystems());
        entity.setRemark("初始下单");
        gameOrderServiceimpl.save(entity);
    }

    private void getParams(SortedMap<Object, Object> packageParams){

        packageParams.put("notify_url", PayStringPool.NOTIFY_URL);// 回调地址
        //MWEB 交易类型H5支付 也可以是小程序支付参数/JSAPI支付必须传openid
        packageParams.put("trade_type", PayStringPool.TRADE_TYPE);
        packageParams.put("mch_id", PayStringPool.MCH_ID);//商户号
        packageParams.put("appid", PayStringPool.APPID);//公众账号ID
        packageParams.put("sign_type", PayStringPool.SIGN_TYPE);//签名类型
        String sign = PayCommonUtil.createSign(StringPool.UTF_8, packageParams,PayStringPool.API_KEY);
        packageParams.put("sign", sign);// 签名
    }
    /**
    * @Description:  下单失败时插入补单列表数据，更新订单状态支付失败
    * @author:
    * @date :2020-06-14 09:27:57
    * @params: returnCode 返回的失败信息
     */
    private void updateOrderStatus (PayParamsDto dto,WxGameOrderEntity gameOrderByOrderNo,String message,String orderNo){
        WxReplacementOrderEntity wxReplacementOrderEntity=new WxReplacementOrderEntity();
        wxReplacementOrderEntity.setCpOrderNo(dto.getCpOrderNO());
        wxReplacementOrderEntity.setUserId(dto.getUserId());
        wxReplacementOrderEntity.setRoleId(dto.getRoleId());
        wxReplacementOrderEntity.setOrderMoney(dto.getPayment());
        wxReplacementOrderEntity.setPayMoney(dto.getPayment());
        wxReplacementOrderEntity.setPlaceOrderDate(time);
        wxReplacementOrderEntity.setOrderStatus(1);
        wxReplacementOrderEntity.setRemark(message);
        wxReplacementOrderEntity.setGameId(dto.getGameId());
        wxReplacementOrderEntity.setNotifyStatus(1);
        wxReplacementOrderEntity.setGameId(dto.getGameId());
        wxReplacementOrderEntity.setCreatedDate(LocalDateTime.now());
        wxReplacementOrderEntity.setOrderNo(orderNo);
        wxReplacementOrderEntity.setProductName(dto.getProductName());
        replacementOrderServiceimpl.save(wxReplacementOrderEntity);
        //更新订单状态
        gameOrderByOrderNo.setOrderStatus(3);
        gameOrderByOrderNo.setUpdatedDate(LocalDateTime.now());
        gameOrderByOrderNo.setRemark(message);
        gameOrderServiceimpl.updateById(gameOrderByOrderNo);
    }

    /**
     *  查询游戏订单状态
     * @param orderNo
     * @return
     */
    @Override
    public R selectOrderStatus(String orderNo) {
        log.info("selectOrderStatus={}",orderNo);
        if (StringUtils.isEmpty(orderNo)){
            return R.failed("无效订单号");
        }
        WxGameOrderEntity order = gameOrderServiceimpl.getGameOrderByOrderNo(orderNo);
        if (order == null){
            return R.failed("无效订单");
        }
        JSONObject result = new JSONObject();
        result.put("status",order.getOrderStatus());
        return R.ok(result);
    }
}
