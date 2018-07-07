/**
 * 项目名：admin
 * 包名：org.demon.admin
 * 文件名：JavaTest
 * 日期：2018/7/7-下午9:35
 * Copyright (c) 2018
 */
package org.demon.admin;

import org.demon.util.Base64;
import org.junit.Test;

/**
 * 类名称：JavaTest
 * 类描述：
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 下午9:35
 * 修改人：
 * 修改时间：2018/7/7 下午9:35
 * 修改备注：
 */
public class JavaTest {

    @Test
    public void test(){
        String a = "S6cTX9jAK/7KbyYQmk9eP3pD13lSpX4J85bPJBX39Nd2U+iY4qIqFjkZ/a6T/vLintAi9HH14aIabp8Heh9h7WYP3a9O6OdwAJ95wvSwGZDq2S1Ys+q8RWRkzp0yrGfV5RxwyfM1MKbptBCj7/rlYf8L3RyCw89pana4Us9wR9Kr34qNgKyXZ/MrYwoDs1L1E67YdFeBVIDn/UvQkujU0hi5oa6Qds5NDe+Cn6zv8Vj6gAkMeBx8vm8xyOJQBootY5POfxqnzUgy61leBrkvJwY6/+1pnsmbWVQa47xFuUb+p8sfGiSn+mA7Y4f1NSN5rkDTeHqlhBonxvfUQ2FiQvdIbhLtkMJxfNdnPuHcxkLxySzWmRqEhuiLPr3lTmKq";
        String b = Base64.decode2Sting(a);
        System.out.println(b);
    }
}
