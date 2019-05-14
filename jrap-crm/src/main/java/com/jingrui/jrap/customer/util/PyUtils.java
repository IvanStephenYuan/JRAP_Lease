/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@28f8f19$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 鹏元工具类
 */
public class PyUtils {

    /**
     * 将流转换为字符串
     *
     * @param is
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String toString(InputStream is, String encoding) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel dest = Channels.newChannel(bos);
        ReadableByteChannel src = Channels.newChannel(is);
        ByteBuffer bb = ByteBuffer.allocate(4096);

        while (src.read(bb) != -1) {
            bb.flip();
            dest.write(bb);
            bb.clear();
        }
        src.close();
        dest.close();

        return new String(bos.toByteArray(), encoding);
    }

    /**
     * 解码及解压缩
     *
     * @param value
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String decodeAndDecompress(String value, String encoding) throws Exception {

        byte[] data = new Base64().decode(value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ZipInputStream zis = new ZipInputStream(bais);
        ZipEntry ze = zis.getNextEntry();
        byte[] buffer = new byte[2014];
        copy(zis, baos, buffer);
        zis.close();
        bais.close();
        baos.close();
        return new String(baos.toByteArray(), encoding);
    }

    public static void copy(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }
}
