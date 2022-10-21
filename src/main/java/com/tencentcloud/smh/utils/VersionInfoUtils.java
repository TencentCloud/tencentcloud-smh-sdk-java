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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class VersionInfoUtils {
    /** The SMH SDK version info file with SDK versioning info */
    static final String VERSION_INFO_FILE = "/versionInfo.properties";

    /** SDK version info */
    private static volatile String version;

    /** SDK platform info */
    private static volatile String platform;

    /** User Agent info */
    private static volatile String userAgent;

    /** Shared logger for any issues while loading version information */
    private static final Logger log = LoggerFactory.getLogger(VersionInfoUtils.class);

    /**
     * Returns the current version for the SMH SDK in which this class is running. Version
     * information is obtained from from the versionInfo.properties file which the SMH Java SDK
     * build process generates.
     *
     * @return The current version for the SMH SDK, if known, otherwise returns a string indicating
     *         that the version information is not available.
     */
    public static String getVersion() {
        if (version == null) {
            synchronized (VersionInfoUtils.class) {
                if (version == null) {
                    initializeVersion();
                }
            }
        }
        return version;
    }

    /**
     * Returns the current platform for the SMH SDK in which this class is running. Version
     * information is obtained from from the versionInfo.properties file which the SMH Java SDK
     * build process generates.
     *
     * @return The current platform for the SMH SDK, if known, otherwise returns a string indicating
     *         that the platform information is not available.
     */
    public static String getPlatform() {
        if (platform == null) {
            synchronized (VersionInfoUtils.class) {
                if (platform == null)
                    initializeVersion();
            }
        }
        return platform;
    }

    /**
     * @return Returns the User Agent string to be used when communicating with the SMH services.
     *         The User Agent encapsulates SDK, Java, OS and region information.
     */
    public static String getUserAgent() {
        if (userAgent == null) {
            synchronized(VersionInfoUtils.class) {
                if (userAgent == null) {
                    userAgent =  String.format("smh-java-sdk-v%s/%s/jdk-%s/%s",
                                               getVersion(),
                                               System.getProperty("os.name"),
                                               System.getProperty("java.version"),
                                               System.getProperty("java.vm.name"));
                } 
            }
        }
        return userAgent;
    }

    /**
     * Loads the versionInfo.properties file from the SMH Java SDK and stores the information so
     * that the file doesn't have to be read the next time the data is needed.
     */
    private static void initializeVersion() {
        InputStream inputStream = VersionInfoUtils.class.getResourceAsStream(VERSION_INFO_FILE);
        Properties versionInfoProperties = new Properties();
        try {
            if (inputStream == null) {
                throw new Exception(VERSION_INFO_FILE + " not found on classpath");
            }

            versionInfoProperties.load(inputStream);
            version = versionInfoProperties.getProperty("version");
        } catch (Exception e) {
            log.info("Unable to load version information for the running SDK: " + e.getMessage());
            version = "unknown-version";
            platform = "java";
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
