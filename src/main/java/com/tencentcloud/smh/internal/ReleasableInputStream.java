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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * An input stream that can have the close operation disabled (to avoid accidentally being closed).
 * This is necessary, for example, when an input stream needs to be marked-and-reset multiple times
 * but only as long as the input stream has not been closed. To survive not being accidentally
 * closed, the close method can be disabled via {@link #disableClose()}.
 * <p>
 * The creator of this input stream should therefore always call {@link #release()} in a finally
 * block to truly release the underlying resources.
 * </p>
 * @see Releasable
 * @see ResettableInputStream
 */
public class ReleasableInputStream extends SdkFilterInputStream implements Releasable {
    private static final Logger log = LoggerFactory.getLogger(ReleasableInputStream.class);
    /**
     * True if the close method is disabled; false otherwise. Default is false. In case the close
     * method is disabled, caller would be responsible to release resources via {@link #release()}.
     */
    private boolean closeDisabled;

    /**
     * This constructor is not meant to be used directly. Use {@link #wrap(InputStream)} instead.
     */
    protected ReleasableInputStream(InputStream is) {
        super(is);
    }

    /**
     * If {@link #closeDisabled} is false, closes this input stream and releases any system
     * resources associated with the stream. Otherwise, this method does nothing.
     */
    @Override
    public final void close() {
        if (!closeDisabled) {
            doRelease();
        }
    }

    /**
     * Closes the underlying stream file and releases any system resources associated.
     */
    @Override
    public final void release() {
        doRelease();
    }

    /**
     * Used to truly release the underlying resources.
     */
    private void doRelease() {
        try {
            in.close();
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("FYI", ex);
            }
        }
        if (in instanceof Releasable) {
            // This allows any underlying stream that has the close operation
            // disabled to be truly released
            Releasable r = (Releasable) in;
            r.release();
        }
        abortIfNeeded();
    }

    /**
     * Returns true if the close method has been disabled; false otherwise. Once the close method is
     * disabled, caller would be responsible to release resources via {@link #release()}.
     */
    public final boolean isCloseDisabled() {
        return closeDisabled;
    }

    /**
     * Used to disable the close method. Once the close method is disabled, caller would be
     * responsible to release resources via {@link #release()}.
     */
    public final <T extends ReleasableInputStream> T disableClose() {
        this.closeDisabled = true;
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    /**
     * Wraps the given input stream into a {@link ReleasableInputStream} if necessary. Note if the
     * given input stream is a {@link FileInputStream}, a {@link ResettableInputStream} which is a
     * specific subclass of {@link ReleasableInputStream} will be returned.
     */
    public static ReleasableInputStream wrap(InputStream is) {
        if (is instanceof ReleasableInputStream) {
            return (ReleasableInputStream) is; // already wrapped
        }
        if (is instanceof FileInputStream) {
            return ResettableInputStream.newResettableInputStream((FileInputStream) is);
        }
        return new ReleasableInputStream(is);
    }
}
