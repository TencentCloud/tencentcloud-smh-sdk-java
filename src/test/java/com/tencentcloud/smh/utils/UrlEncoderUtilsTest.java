package com.tencentcloud.smh.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlEncoderUtilsTest {

    @Test
    public void testEncode() {
        String encodeStr =
                UrlEncoderUtils.encode("! \"#$%&'()*+,-./0123456789:;" +
                        "<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
                        "abcdefghijklmnopqrstuvwxyz{|}~");
        assertEquals("%21%20%22%23%24%25%26%27%28%29%2A%2B%2C-." +
                        "%2F0123456789%3A%3B%3C%3D%3E%3F%40ABCDEFGHIJKLMNOPQRSTUVWXYZ%5B%5C%5D%5E_%" +
                        "60abcdefghijklmnopqrstuvwxyz%7B%7C%7D~",
                     encodeStr);
    }

}
