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



package com.tencentcloud.smh.event;

import com.tencentcloud.smh.internal.SdkFilterInputStream;
import com.tencentcloud.smh.internal.SmhServiceRequest;

import java.io.IOException;
import java.io.InputStream;


public class ProgressInputStream extends SdkFilterInputStream {
    /**
     * @param is               the request content input stream
     * @param progressListener Optional progress listener
     * @return If the progress listener is non null
     * returns a new input stream decorated with progress reporting functionality.
     * If progress listener is null it returns the same input
     * stream.
     */
    public static InputStream inputStreamForRequest(InputStream is, ProgressListener progressListener) {
        return progressListener == null
                ? is
                : new RequestProgressInputStream(is, progressListener);
    }

    /**
     * Returns an input stream for response progress tracking purposes. If
     * request/response progress tracking is not enabled, this method simply
     * return the given input stream as is.
     * 
     * @param is the response content input stream
     */
    public static InputStream inputStreamForResponse(InputStream is, SmhServiceRequest req) {
        return req == null
             ? is
             : new ResponseProgressInputStream(is, req.getGeneralProgressListener());
    }

    /**
     * Returns an input stream for response progress tracking purposes.
     * If request/response progress tracking is not enabled, this
     * method simply return the given input stream as is.
     *
     * @param is               the response content input stream
     * @param progressListener Optional progress listener
     * @return If the progress listener is non null
     * returns a new input stream decorated with progress reporting functionality.
     * If progress listener is null it returns the same input stream.
     */
    public static InputStream inputStreamForResponse(InputStream is, ProgressListener progressListener) {
        return progressListener == null
                ? is
                : new ResponseProgressInputStream(is, progressListener);
    }

    /** The threshold of bytes between notifications. */
    private static final int DEFAULT_NOTIFICATION_THRESHOLD = 8 * 1024;

    private final ProgressListener listener;
    private final int notifyThresHold;
    /** The number of bytes read that the listener hasn't been notified about yet. */
    private int unnotifiedByteCount;
    private boolean hasBeenRead;
    private boolean doneEOF;
    private long notifiedByteCount;

    public ProgressInputStream(InputStream is, ProgressListener listener) {
        this(is, listener, DEFAULT_NOTIFICATION_THRESHOLD);
    }

    public ProgressInputStream(InputStream is, ProgressListener listener, int notifyThresHold) {
        super(is);
        if (is == null || listener == null) {
            throw new IllegalArgumentException();
        }
        this.notifyThresHold = notifyThresHold;
        this.listener = listener;
    }

    /**
     * The read method is called for the very first time.
     * Defaults to do nothing.
     */
    protected void onFirstRead() {}

    /**
     * An end-of-file event is to be notified.
     * Defaults to do nothing.
     */
    protected void onEOF() {}

    /**
     * Defaults to behave the same as {@link #onEOF()}.
     */
    protected void onClose() {
        eof();
    }

    /**
     * A reset event is to be notified.  Default to do nothing.
     */
    protected void onReset() {}

    /**
     * Upon notification of the number of bytes transferred since last
     * notification.  Default to do nothing.
     */
    protected void onNotifyBytesRead() {}

    /**
     * Upon reading the given number of bytes.
     * The default behavior is to accumulate the byte count and only fire off
     * a notification by invoking {@link #onNotifyBytesRead()} if the count
     * has exceeded the threshold.
     */
    private void onBytesRead(int bytesRead) {
        unnotifiedByteCount += bytesRead;
        if (unnotifiedByteCount >= notifyThresHold) {
            onNotifyBytesRead();
            notifiedByteCount += unnotifiedByteCount;
            unnotifiedByteCount = 0;
        }
    }

    @Override
    public int read() throws IOException {
        if (!hasBeenRead) {
            onFirstRead();
            hasBeenRead = true;
        }
        int ch = super.read();
        if (ch == -1) {
            eof();
        } else {
            onBytesRead(1);
        }
        return ch;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        onReset();
        unnotifiedByteCount = 0;
        notifiedByteCount = 0;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (!hasBeenRead) {
            onFirstRead();
            hasBeenRead = true;
        }
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            eof();
        } else {
            onBytesRead(bytesRead);
        }
        return bytesRead;
    }

    @Override
    public void close() throws IOException {
        onClose(); // report any left over bytes not yet reported
        super.close();
    }

    public final ProgressListener getListener() {
        return listener;
    }

    private void eof() {
        if (doneEOF) {
            return;
        }
        onEOF();
        unnotifiedByteCount = 0;
        doneEOF = true;
    }

    public final InputStream getWrappedInputStream() {
        return in;
    }

    protected final int getUnnotifiedByteCount() {
        return unnotifiedByteCount;
    }

    protected final long getNotifiedByteCount() {
        return notifiedByteCount;
    }

}
