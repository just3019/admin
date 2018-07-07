package org.demon.parse.impl;


import org.demon.exception.net.ParseBeanException;
import org.demon.parse.InputStreamParser;
import org.demon.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Description:
 * Date: 2016/11/21 20:29
 *
 * @author Sean.xie
 */
public class FileInputParser implements InputStreamParser<Boolean> {

    private static final Logger logger = Logger.newInstance(FileInputParser.class);

    private final String file;

    public FileInputParser(String file) {
        this.file = file;
    }

    @Override
    public Boolean parser(InputStream inputStream) throws ParseBeanException {
        FileOutputStream fos = null;
        try {
            File f = new File(file);
            f.getParentFile().mkdirs();
            fos = new FileOutputStream(f);
            int len = -1;
            byte[] bytes = new byte[1024 * 8];
            while ((len = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            logger.printStackTrace(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.printStackTrace(e);
                }
            }
        }
        return false;
    }
}