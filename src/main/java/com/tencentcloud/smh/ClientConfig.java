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


package com.tencentcloud.smh;

import com.tencentcloud.smh.http.HttpProtocol;
import com.tencentcloud.smh.retry.BackoffStrategy;
import com.tencentcloud.smh.retry.PredefinedBackoffStrategies;
import com.tencentcloud.smh.retry.PredefinedRetryPolicies;
import com.tencentcloud.smh.retry.RetryPolicy;
import com.tencentcloud.smh.utils.VersionInfoUtils;

public class ClientConfig {

    // 默认的获取连接的超时时间, 单位ms
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    // 默认连接超时, 单位ms
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;
    // 默认的SOCKET读取超时时间, 单位ms
    private static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;
    // 默认的维护最大HTTP连接数
    private static final int DEFAULT_MAX_CONNECTIONS_COUNT = 1024;
    private static final int DEFAULT_IDLE_CONNECTION_ALIVE = 60 * 1000;
    // 默认的user_agent标识
    private static final String DEFAULT_USER_AGENT = VersionInfoUtils.getUserAgent();

    // Read Limit
    private static final int DEFAULT_READ_LIMIT = (2 << 17) + 1;
    /**
     * default retry times is 3 when retryable exception occured
     **/
    private static final int DEFAULT_RETRY_TIMES = 3;
    /**
     * The max retry times if retryable exception occured
     **/
    private int maxErrorRetry = DEFAULT_RETRY_TIMES;
    /**
     * The retry policy if exception occured
     **/
    private static final RetryPolicy DEFAULT_RETRY_POLICY = PredefinedRetryPolicies.DEFAULT;
    /**
     * The sleep time interval between exception occured and retry
     **/
    public static final BackoffStrategy DEFAULT_BACKOFF_STRATEGY = PredefinedBackoffStrategies.DEFAULT;
    private HttpProtocol httpProtocol = HttpProtocol.https;
    private String endPointSuffix = null;
    private RetryPolicy retryPolicy = DEFAULT_RETRY_POLICY;
    private BackoffStrategy backoffStrategy = DEFAULT_BACKOFF_STRATEGY;

    // http proxy代理，如果使用http proxy代理，需要设置IP与端口
    private String httpProxyIp = null;
    private int httpProxyPort = 0;
    private String proxyUsername = null;
    private String proxyPassword = null;
    private boolean useBasicAuth = false;
    private int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private int maxConnectionsCount = DEFAULT_MAX_CONNECTIONS_COUNT;
    private int idleConnectionAlive = DEFAULT_IDLE_CONNECTION_ALIVE;
    private String userAgent = DEFAULT_USER_AGENT;
    private int readLimit = DEFAULT_READ_LIMIT;

    // 不传入region 用于后续调用List Buckets(获取所有的bucket信息)
    public ClientConfig() {
        super();
    }



    public int getIdleConnectionAlive() {
        return this.idleConnectionAlive;
    }

    public void setIdleConnectionAlive(int idleConnectionAlive) {
        this.idleConnectionAlive = idleConnectionAlive;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(HttpProtocol httpProtocol) {
        this.httpProtocol = httpProtocol;
    }

    public String getHttpProxyIp() {
        return httpProxyIp;
    }

    public void setHttpProxyIp(String httpProxyIp) {
        this.httpProxyIp = httpProxyIp;
    }

    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMaxConnectionsCount() {
        return maxConnectionsCount;
    }

    public void setMaxConnectionsCount(int maxConnectionsCount) {
        this.maxConnectionsCount = maxConnectionsCount;
    }


    @Deprecated
    public String getEndPointSuffix() {
        return endPointSuffix;
    }

    public void setEndPointSuffix(String endPointSuffix) {
        this.endPointSuffix = endPointSuffix;
    }

    public int getReadLimit() {
        return readLimit;
    }

    public void setReadLimit(int readLimit) {
        this.readLimit = readLimit;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setUseBasicAuth(boolean useBasicAuth) {
        this.useBasicAuth = useBasicAuth;
    }

    public boolean useBasicAuth() {
        return useBasicAuth;
    }

    public int getMaxErrorRetry() {
        return maxErrorRetry;
    }

    public void setMaxErrorRetry(int maxErrorRetry) {
        this.maxErrorRetry = maxErrorRetry;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public BackoffStrategy getBackoffStrategy() {
        return backoffStrategy;
    }

    public void setBackoffStrategy(BackoffStrategy backoffStrategy) {
        this.backoffStrategy = backoffStrategy;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

}
