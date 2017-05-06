package com.jeeweb.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeweb.framework.core.exception.BusinessException;

public class SerializerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 序列化Object为二进制byte数组
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            LOG.error("序列化失败！", e);
            throw new BusinessException("序列化失败！", e);
        } finally {
            close(oos);
            close(baos);
        }
    }

    /**
     * 反序列化二进制byte数组到Object
     * 
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            Object object = ois.readObject();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("反序列化失败！", e);
            throw new BusinessException("反序列化失败！", e);
        } finally {
            close(bais);
            close(ois);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return (T) deserialize(bytes);
    }

    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}