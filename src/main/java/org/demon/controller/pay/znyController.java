/**
 * 项目名：admin
 * 包名：org.demon.controller.pay
 * 文件名：znyController
 * 日期：2018/7/7-上午9:38
 * Copyright (c) 2018
 */
package org.demon.controller.pay;

import org.demon.bean.pay.ZnyBean;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.service.pay.ZnyService;
import org.demon.util.Logger;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：znyController
 * 类描述：  智能云支付
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 上午9:38
 * 修改人：
 * 修改时间：2018/7/7 上午9:38
 * 修改备注：
 */
@RestController
public class znyController {
    private static final Logger logger = Logger.newInstance(znyController.class);

    @Autowired
    private ZnyService znyService;


    @RequestMapping(value = "zny/order", method = RequestMethod.POST)
    public Object order(@RequestBody ZnyBean bean) {
        ZnyBean resp = znyService.order(bean);
        if (NumberUtil.isInValidId(resp.code)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "order fail");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("qrcode", resp.data.qrcode);
        map.put("ordno", resp.ordno);
        return map;
    }


}
