package org.demon.parse.impl;


import org.demon.exception.net.ParseBeanException;
import org.demon.parse.InputStreamParser;
import org.demon.util.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Description:
 * Date: 2016/11/21 20:29
 *
 * @author Sean.xie
 */
public class StringInputParser implements InputStreamParser<String> {

    private static final Logger logger = Logger.newInstance(StringInputParser.class);

    @Override
    public String parser(InputStream inputStream) throws ParseBeanException {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            int len = -1;
            byte[] bytes = new byte[1024 * 8];
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            return bos.toString();
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