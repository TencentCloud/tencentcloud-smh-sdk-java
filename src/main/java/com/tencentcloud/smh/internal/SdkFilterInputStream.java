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

package com.tencentcloud.smh.internal;

import com.tencentcloud.smh.exception.AbortedException;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Base class for SMH Java SDK specific {@link FilterInputStream}.
 */
public class SdkFilterInputStream extends FilterInputStream implements Releasable {
    protected SdkFilterInputStream(InputStream in) {
        super(in);
    }
    private volatile boolean aborted = false;

    /**
     * Aborts with subclass specific abortion logic executed if needed. Note the interrupted status
     * of the thread is cleared by this method.
     * 
     * @throws AbortedException if found necessary.
     */
    protected final void abortIfNeeded() {
        if (Thread.interrupted()) {
            abort(); // execute subclass specific abortion logic
            throw new AbortedException();
        }
    }

    /**
     * Can be used to provide abortion logic prior to throwing the
     * AbortedException. If the wrapped {@code InputStream} is also an instance
     * of this class, then it will also be aborted, otherwise this is a no-op.
     */
    public void abort() {
        if(aborted)
            return;
        aborted = true;
        if (in instanceof SdkFilterInputStream) {
            ((SdkFilterInputStream) in).abort();
        }
    }

    protected boolean isAborted() {
        return aborted;
    }

    @Override
    public int read() throws IOException {
        abortIfNeeded();
        return in.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        abortIfNeeded();
        return in.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        abortIfNeeded();
        return in.skip(n);
    }

    @Override
    public int available() throws IOException {
        abortIfNeeded();
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
        abortIfNeeded();
    }

    @Override
    public synchronized void mark(int readlimit) {
        abortIfNeeded();
        in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        abortIfNeeded();
        in.reset();
    }

    @Override
    public boolean markSupported() {
        abortIfNeeded();
        return in.markSupported();
    }

    @Override
    public void release() {
        // Don't call IOUtils.release(in, null) or else could lead to infinite loop
        SdkIOUtils.closeQuietly(this);
        if (in instanceof Releasable) {
            // This allows any underlying stream that has the close operation
            // disabled to be truly released
            Releasable r = (Releasable) in;
            r.release();
        }
    }
}
