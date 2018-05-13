package org.demon.bean;

import org.demon.Constants;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Created by Sean.xie on 2017/3/27.
 */
public class BaseHeader extends BaseBean {

    /**
     * Session id
     */
    public String sessionId;

    /**
     * 请求序列编号
     */
    public String reqSeq;

    /**
     * app 版本号
     */
    public String appVer = Constants.NONE;

    /**
     * app类型
     */
    public String appType = Constants.UNKNOWN;

    /**
     * 附加数据
     */
    public String extData;

    /**
     * 服务调用方IP地址
     */
    public String targetIp;

    /**
     * 设备调用方IP地址
     */
    public String deviceIp;

    /**
     * 请求UID
     */
    public Long targetUid;

    /**
     * 请求设备ID
     */
    public String deviceId;
    /**
     * 源请求地址
     */
    public String originHost;

    /**
     * 响应是否进行URLEncode
     */
    public boolean respDecode = false;
    /**
     * json完整输出
     */
    public boolean fullJson = false;
    /**
     * 是否进行HTML标签安全处理
     */
    public boolean htmlSafe = false;
    /**
     * 是否进行HTML标签安全处理
     */
    public boolean htmlEscape = false;

    /**
     * 过滤key 只留下 本类中包含的
     * 使用后维护起来不方便,所以被废弃
     *
     * @param source 源
     */
    public static Map<String, String> filterKeys(Map<String, String> source) {
        if (source == null) {
            return source;
        }

        Set<String> properties = new HashSet<>();
        Field[] fields = BaseHeader.class.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                properties.add(field.getName());
            }
        }
        Map<String, String> destMap = new HashMap<>();
        boolean isAdded;
        for (Map.Entry<String, String> entry : source.entrySet()) {
            isAdded = false;
            for (String property : properties) {
                if (entry.getKey().equalsIgnoreCase(property)) {
                    destMap.put(property, entry.getValue());
                    properties.remove(property);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                destMap.put(entry.getKey(), entry.getValue());
            }
        }

        return destMap;
    }
}
