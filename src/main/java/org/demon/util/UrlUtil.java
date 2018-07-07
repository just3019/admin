package org.demon.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Copyright (C) 2005-2010 TENCENT Inc.All Rights Reserved.
 * FileName：UrlUtil.java Description： History： 1.0 sekarao 2014-2-20 Create
 */

public class UrlUtil {

    private static final String URL_CODING = "utf-8";
    private static Pattern VALID_URL = Pattern.compile("(.+)(\\.)(.+)[^\\w]*(.*)",
            Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_LOCAL_URL = Pattern.compile(
            "(^file://.+)|(.+localhost:?\\d*/?.*)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_MTT_URL = Pattern.compile("mtt://(.+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_QB_URL = Pattern.compile("qb://(.+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_PAY_URL = Pattern.compile("(tenpay|alipay)://(.+)",
            Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_IP_ADDRESS = Pattern.compile("(\\d){1,3}\\.(\\d){1,3}"
            + "\\.(\\d){1,3}\\.(\\d){1,3}(:\\d{1,4})?(/(.*))?", Pattern.CASE_INSENSITIVE);

    /**
     * 处理url中的中文字符
     *
     * @param url
     * @return
     */
    public static String escapeAllChineseChar(String url) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);

            if (c >= 0x4E00 && c <= 0x9FFF || c >= 0xFE30 && c <= 0xFFA0) {
                String escapedStr;
                try {
                    escapedStr = URLEncoder.encode(String.valueOf(c), URL_CODING);

                    sb.append(escapedStr);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 是否javaScript
     *
     * @param url
     * @return
     */
    public static boolean isJavascript(String url) {
        // javascript:
        return (null != url) && (url.length() > 10)
                && url.substring(0, 11).equalsIgnoreCase("javascript:");
    }


    public static boolean isSpecialUrl(String url) {
        if (url == null) {
            return false;
        }

        String lowser = url.toLowerCase();
        return lowser.startsWith("about:blank")
                || lowser.startsWith("data:");
    }

    /**
     * 判断URL是否是一个有效的格式
     */
    public static boolean isCandidateUrl(final String aUrl) {
        if (aUrl == null || aUrl.length() == 0 || aUrl.startsWith("data:")) {
            return false;
        }
        String url = aUrl.trim();

        Matcher validUrl = VALID_URL.matcher(url);
        Matcher validLocal = VALID_LOCAL_URL.matcher(url);
        Matcher validIp = VALID_IP_ADDRESS.matcher(url);
        Matcher validMtt = VALID_MTT_URL.matcher(url);
        Matcher validQb = VALID_QB_URL.matcher(url);
        Matcher validPay = VALID_PAY_URL.matcher(url);

        if (validUrl.find() || validLocal.find() || validIp.find() || validMtt.find()
                || validQb.find() || validPay.find())
            return true;
        else
            return false;
    }

    /**
     * 判断URL是否有一个有效的协议头
     */
    public static boolean hasValidProtocal(final String url) {
        if (url == null || url.length() == 0) {
            return false;
        }

        // 2013-06-29, modified by p_edenwang
        // Remove trim and toLowerCase operation for url.
        // Spaces and uppercase will not affect the order of "://" & "."
        // This modification will save 20+ms when loading www.sohu.com/?fr=wap.
        int pos1 = url.indexOf("://");
        int pos2 = url.indexOf('.');

        // 检测"wap.fchgame.com/2/read.jsp?url=http://www.zaobao.com/zg/zg.shtml"类型网址
        if (pos1 > 0 && pos2 > 0 && pos1 > pos2) {
            return false;
        }

        return url.contains("://");
    }

    /**
     * 根据输入，得到一个有效URL 如果输入无法被解析为一个URL，返回NULL
     */
    public static String resolvValidUrl(final String aUrl) {
        if (aUrl == null || aUrl.length() == 0) {
            return null;
        }

        String url = aUrl.trim();

        if (isJavascript(url) || UrlUtil.isSpecialUrl(url)) {
            return url;
        } else if (isCandidateUrl(url)) {
            if (hasValidProtocal(url)) {
                return url;
            } else {
                return "http://" + url;
            }
        } else {
            return null;
        }
    }

    /**
     * Url 连接
     *
     * @param pre
     * @param part
     * @return
     */
    public static String contatPart(String pre, String part) {
        if (StringUtil.isBlank(pre)) {
            return pre;
        }
        if (StringUtil.isBlank(part)) {
            return pre;
        }
        if (pre.endsWith("/")) {
            return pre + part;
        } else {
            return pre + "/" + part;
        }
    }

    /**
     * url 添加参数
     *
     * @param url        ex："http://exp.xielei.com/index.html?xie=16&lei=16#/app/home"
     * @param paramName
     * @param paramValue
     * @return
     */
    public static String addURLParam(String url, String paramName, String paramValue) {
        //参数和参数名为空的话就返回原来的URL
        if (StringUtil.isEmpty(paramValue) || StringUtil.isEmpty(paramName)) {
            return url;
        }
        //先很据# ? 将URL拆分成一个String数组
        String a = "";
        String b = "";
        String c = "";

        String[] abcArray = url.split("[?]");
        a = abcArray[0];
        if (abcArray.length > 1) {
            String bc = abcArray[1];
            String[] bcArray = bc.split("#");
            b = bcArray[0];
            if (bcArray.length > 1) {
                c = bcArray[1];
            }
        }
        if (StringUtil.isEmpty(b)) {
            return url + "?" + urlDecode(paramName) + "=" + urlDecode(paramValue);
        }

        // 用&拆p, p1=1&p2=2 ，{p1=1,p2=2}
        String[] bArray = b.split("&");
        String newb = "";
        boolean found = false;
        for (int i = 0; i < bArray.length; i++) {
            String bi = bArray[i];
            if (StringUtil.isEmpty(bi))
                continue;
            String key = "";
            String value = "";

            String[] biArray = bi.split("="); // {p1,1}
            key = biArray[0];
            if (biArray.length > 1)
                value = biArray[1];

            if (key.equals(paramName)) {
                found = true;
                if (!StringUtil.isEmpty(paramValue)) {
                    newb = newb + "&" + key + "=" + paramValue;
                } else {
                    continue;
                }
            } else {
                newb = newb + "&" + key + "=" + value;
            }
        }
        // 如果没找到，加上
        if (!found && !StringUtil.isEmpty(paramValue)) {
            newb = newb + "&" + paramName + "=" + paramValue;
        }
        if (!StringUtil.isEmpty(newb))
            a = a + "?" + newb.substring(1);
        if (!StringUtil.isEmpty(c))
            a = a + "#" + c;
        return a;
    }

    /**
     * UTF-8 编码
     *
     * @param value
     * @return
     */
    public static String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, Charsets.UTF_8);
        } catch (Exception e) {
            return value;
        }
    }

    public static List<String> extractUrls(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }

    public static void main(String[] args) {
//        Pattern pattern = Pattern
//                .compile("((https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|])|(www\\.)?[^\u4e00-\u9fa5\\s+&@#/%?=~_|!:,.;]*?\\.(com|net|cn|me|tw|fr|我爱你|top|vip|wang|site|中国|集团|公司|网络|信息|广东|info|mobi|biz|link|ink|pro|online|pw)[^\u4e00-\u9fa5\\s]*");
        Pattern pattern = Pattern
                .compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        // 空格结束
        Matcher matcher = pattern
                .matcher("下载地址:http://www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss 1 ?sdfsyyy空格结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        matcher = pattern
                .matcher("下载地址//http://www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss 1 ?sdfsyyy空格结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }


        // 中文结束
        matcher = pattern
                .matcher("下载地址ahttp://www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss1 网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }


        matcher = pattern
                .matcher("下载地址：http://h5.welian.com/train/i/13#/chat/?wltk=0d4qTNJ 网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }


        // 没有http://开头
        matcher = pattern
                .matcher("下载地址:www.zuidaima.com/sdfsdf.htm?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }


        // net域名
        matcher = pattern
                .matcher("下载地址www.xxx.net/sdfsdf.htm?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }


        // xxx域名
        matcher = pattern
                .matcher("下载地址www.zuidaima.xxx/sdfsdf.htm?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none xxx");
        }


        // yyyy域名匹配不到
        matcher = pattern
                .matcher("下载地址http://www.zuidaima.yyyy/sdfsdf.html?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("匹配不到yyyy域名");
        }


        // 没有http://www.
        matcher = pattern
                .matcher("下载地址:zuidaima.com/sdfsdf.html?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        matcher = pattern
                .matcher("下载地址//zuidaima.com/sdfsdf.html?aaaa=%ee%sss网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        // 没有http://www.
        matcher = pattern
                .matcher("下载地址gr t.zuidaima.com/sdfsdf.html?aaaa=%ee%sss e网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        // 没有http://www.
        matcher = pattern
                .matcher("下载地址t.zuidaima.hl.我爱你/sdfsdf.html?aaaa=%ee%sss 1网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        // 没有http://www.
        matcher = pattern
                .matcher("下载地址baidu.com网址结束");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("none");
        }

        // 没有http://www.
        matcher = pattern
                .matcher("下载地址baidu.com网址结束址bairdu.com网址baidu4.com网");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }


    }
}
