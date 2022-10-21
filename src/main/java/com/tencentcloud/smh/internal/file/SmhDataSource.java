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


package com.tencentcloud.smh.internal.file;

import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;

import static com.tencentcloud.smh.utils.IOUtils.release;


/**
 * Used to represent an Smh data source that either has a file or an input
 * stream.
 */
public interface SmhDataSource {
    public File getFile();

    public void setFile(File file);

    public InputStream getInputStream();

    public void setInputStream(InputStream inputStream);

    /**
     * {@link SmhDataSource} specific utilities.
     */
    public static enum Utils {
        ;
        /**
         * Clean up any temporary streams created during the execution,
         * and restore the original file and/or input stream.
         */
        public static void cleanupDataSource(SmhDataSource req,
                                             final File fileOrig, final InputStream inputStreamOrig,
                                             InputStream inputStreamCurr, Logger log) {
            if (fileOrig != null) {
                // We opened a file underneath so would need to release it
                release(inputStreamCurr, log);
            }
            // restore the original input stream so the caller could close
            // it if necessary
            req.setInputStream(inputStreamOrig);
            req.setFile(fileOrig);
        }
    }
}
