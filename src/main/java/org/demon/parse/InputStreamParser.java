package org.demon.parse;


import org.demon.exception.net.ParseBeanException;

import java.io.InputStream;

public interface InputStreamParser<T> {

    /**
     * 解析流
     *
     * @param inputStream 实现中不需关心inputStream的关闭
     * @return
     */
    T parser(InputStream inputStream) throws ParseBeanException;

}
