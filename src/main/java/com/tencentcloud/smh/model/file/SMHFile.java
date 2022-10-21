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

package com.tencentcloud.smh.model.file;

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SMHFile extends CommonResponse implements Closeable, Serializable {
    private static final long serialVersionUID = 1L;

    /** The stream containing the contents of this object from SMH */
    private transient SMHFileInputStream objectContent;


    /**
     * Gets the input stream containing the contents of this object.
     *
     * <p>
     * <b>Note</b>: The method is a simple getter and does not actually create a stream. If you
     * retrieve an SMHFile, you should close this input stream as soon as possible, because the
     * object contents aren't buffered in memory and stream directly from TencentCloud SMH. Further,
     * failure to close this stream can cause the request pool to become blocked.
     * </p>
     *
     * @return An input stream containing the contents of this object.
     *
     * @see SMHFile#setObjectContent(InputStream)
     */
    public SMHFileInputStream getObjectContent() {
        return objectContent;
    }

    /**
     * Sets the input stream containing this object's contents.
     *
     * @param objectContent The input stream containing this object's contents.
     *
     * @see SMHFile#getObjectContent()
     */
    public void setObjectContent(SMHFileInputStream objectContent) {
        this.objectContent = objectContent;
    }

    /**
     * Sets the input stream containing this object's contents.
     *
     * @param objectContent The input stream containing this object's contents. Will get wrapped in
     *        an SMHFileInputStream.
     * @see SMHFile#getObjectContent()
     */
    public void setObjectContent(InputStream objectContent) {
        setObjectContent(new SMHFileInputStream(objectContent,
                this.objectContent != null ? this.objectContent.getHttpRequest() : null));
    }


    /**
     * Releases any underlying system resources. If the resources are already released then invoking
     * this method has no effect.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        InputStream is = getObjectContent();
        if (is != null)
            is.close();
    }

}
