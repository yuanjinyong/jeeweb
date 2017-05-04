/**
 *
 */
package com.jeeweb.framework.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeweb.framework.core.exception.BusinessException;

/**
 * @author 袁进勇
 *
 */
public final class BASE64Util {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BASE64Util.class);

    private BASE64Util() {

    }

    /**
     * 将字节数组进行Base64编码
     *
     * @param filePath
     * @return
     */
    public static String toBASE64Str(byte[] data) {
        // 返回Base64编码过的字节数组字符串
        return DatatypeConverter.printBase64Binary(data);
    }

    @SuppressWarnings("deprecation")
    public static String imageBASE64Str(File imageFile) {
        try {
            StringBuffer str = new StringBuffer("data:");
            str.append(imageFile.toURL().openConnection().getContentType());
            str.append(";base64,").append(readBASE64Str(imageFile.getAbsolutePath()));
            return str.toString();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 将文件转化为字节数组字符串并对其进行Base64编码
     *
     * @param filePath
     * @return
     */
    public static String readBASE64Str(String filePath) {
        InputStream in = null;
        try {
            // 读取文件字节数组
            in = new FileInputStream(filePath);
            byte[] data = new byte[in.available()];
            in.read(data);

            // 返回Base64编码过的字节数组字符串
            return DatatypeConverter.printBase64Binary(data);
        } catch (IOException e) {
            throw new BusinessException("读取文件出错！", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("关闭文件失败！", e);
                }
            }
        }
    }

    /**
     * 对字节数组字符串进行Base64解码并写入文件
     *
     * @param base64Str
     * @param filePath
     * @return
     */
    public static void writeBASE64Str(String base64Str, String filePath) {
        OutputStream out = null;
        try {
            // 写入文件
            out = new FileOutputStream(filePath);

            if (!HelpUtil.isEmpty(base64Str)) {
                // Base64解码
                byte[] b = DatatypeConverter.parseBase64Binary(base64Str);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {// 调整异常数据
                        b[i] += 256;
                    }
                }
                out.write(b);
            }

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new BusinessException("写入文件出错！", e);
        } catch (IOException e) {
            throw new BusinessException("写入文件出错！", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("关闭文件失败！", e);
                }
            }
        }
    }

    // 对字符串进行Base64编码
    public static String encode(String str) {
        if (HelpUtil.isEmpty(str)) {
            return str;
        }

        String encodeString = "";
        try {
            byte[] data = str.getBytes("utf-8");
            encodeString = DatatypeConverter.printBase64Binary(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    // 对字符串进行Base64解码
    public static String decode(String str) {
        if (HelpUtil.isEmpty(str)) {
            return str;
        }

        String decodeString = "";
        try {
            byte[] data = DatatypeConverter.parseBase64Binary(str);
            decodeString = new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeString;
    }
}
