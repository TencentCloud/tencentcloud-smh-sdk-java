/*
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencentcloud.smh.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.tencentcloud.smh.internal.Releasable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Utilities for IO operations.
 */
public enum IOUtils {
    ;
    private static final int BUFFER_SIZE = 1024 * 4;
    private static final Logger defaultLog = LoggerFactory.getLogger(IOUtils.class);

    /**
     * Reads and returns the rest of the given input stream as a byte array. Caller is responsible
     * for closing the given input stream.
     */
    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[BUFFER_SIZE];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }


    /**
     * Closes the given Closeable quietly.
     * 
     * @param is the given closeable
     * @param log logger used to log any failure should the close fail
     */
    public static void closeQuietly(Closeable is, Logger log) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                Logger logger = log == null ? defaultLog : log;
                if (logger.isDebugEnabled()) {
                    logger.debug("Ignore failure in closing the Closeable", ex);
                }
            }
        }
    }

    /**
     * Releases the given {@link Closeable} especially if it was an instance of {@link Releasable}.
     * <p>
     * For example, the creation of a <code>ResettableInputStream</code> would entail physically
     * opening a file. If the opened file is meant to be closed only (in a finally block) by the
     * very same code block that created it, then it is necessary that the release method must not
     * be called while the execution is made in other stack frames.
     * 
     * In such case, as other stack frames may inadvertently or indirectly call the close method of
     * the stream, the creator of the stream would need to explicitly disable the accidental closing
     * via <code>ResettableInputStream#disableClose()</code>, so that the release method becomes the
     * only way to truly close the opened file.
     * </p>
     */
    public static void release(Closeable is, Logger log) {
        closeQuietly(is, log);
        if (is instanceof Releasable) {
            Releasable r = (Releasable) is;
            r.release();
        }
    }

    /**
     * Copies all bytes from the given input stream to the given output stream. Caller is
     * responsible for closing the streams.
     * 
     * @throws IOException if there is any IO exception during read or write.
     */
    public static long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while ((n = in.read(buf)) > -1) {
            out.write(buf, 0, n);
            count += n;
        }
        return count;
    }
    
//    /**
//     * Read all remaining data in the stream.
//     *
//     * @param in InputStream to read.
//     */
//    public static void drainInputStream(InputStream in) {
//        try {
//            while (in.read() != -1) {
//                // Do nothing.
//            }
//        } catch (IOException ignored) {
//            // Stream may be self closed by HTTP client so we ignore any failures.
//        }
//    }
}
