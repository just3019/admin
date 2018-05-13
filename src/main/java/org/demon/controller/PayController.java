/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：PayController
 * 日期：2018/5/13-下午8:04
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.util.JSONUtil;
import org.demon.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：PayController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 下午8:04
 * 修改人：
 * 修改时间：2018/5/13 下午8:04
 * 修改备注：
 */
@Controller
public class PayController {

    private Logger logger = Logger.newInstance(PayController.class);

    @RequestMapping(value = "/zfbNotify")
    public String zfbNotify(HttpServletRequest request) {
        logger.info("支付回调");
        String flag = "fail";
        // 获取到返回的所有参数 先判断是否交易成功trade_status 再做签名校验
        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        // 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 4、验证app_id是否为该商户本身。上述1、2、3、4
        // 有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Enumeration<?> pNames = request.getParameterNames();
            Map<String, String> param = new HashMap<>();
            try {
                while (pNames.hasMoreElements()) {
                    String pName = (String) pNames.nextElement();
                    param.put(pName, request.getParameter(pName));
                }
                logger.info("支付宝返回参数：" + JSONUtil.obj2Json(param));
//                boolean signVerified = AlipaySignature.rsaCheckV1(param, AlipayConfig.publickey, AlipayConstants
//                        .CHARSET_UTF8, "RSA2"); // 校验签名是否正确
                boolean signVerified = true;
                if (signVerified) {
                    // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                    //todo 验签成功业务操作
                    //ZfbCallBack zfbCallBack = gson.fromJson(gson.toJson(param), ZfbCallBack.class);
                    //zfbCallBackService.updateCallback(zfbCallBack);
                    flag = "success";
                } else {
                    logger.info("验签失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
