package org.demon.parse.impl;


import org.demon.exception.net.ParseBeanException;
import org.demon.parse.InputStreamParser;
import org.demon.util.JSONType;
import org.demon.util.JSONUtil;
import org.demon.util.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Description:
 * Date: 2016/11/21 20:29
 *
 * @author Sean.xie
 */
public class GSONInputParser<T> implements InputStreamParser<T> {

    private static final Logger logger = Logger.newInstance(GSONInputParser.class);
    /**
     * 转换类型
     */
    private final JSONType<T> type;

    public GSONInputParser(JSONType<T> type) {
        this.type = type;
    }

    @Override
    public T parser(InputStream inputStream) throws ParseBeanException {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            int len = -1;
            byte[] bytes = new byte[1024 * 8];
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            return JSONUtil.json2Obj(bos.toString(), type);
        } catch (Exception e) {
            logger.printStackTrace(e);
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    logger.printStackTrace(e);
                }
            }
        }
    }
}