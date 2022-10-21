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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencentcloud.smh.exception.FileLockException;
import com.tencentcloud.smh.exception.SmhClientException;
import com.tencentcloud.smh.exception.SmhServiceException;
import com.tencentcloud.smh.http.DefaultSmhHttpClient;
import com.tencentcloud.smh.http.HttpMethodName;
import com.tencentcloud.smh.http.HttpProtocol;
import com.tencentcloud.smh.http.HttpResponseHandler;
import com.tencentcloud.smh.http.SmhHttpClient;
import com.tencentcloud.smh.http.SmhHttpRequest;
import com.tencentcloud.smh.internal.SmhServiceRequest;
import com.tencentcloud.smh.internal.batch.BatchRequest;
import com.tencentcloud.smh.internal.directory.DirectoryDetailRequest;
import com.tencentcloud.smh.internal.directory.DirectoryRequest;
import com.tencentcloud.smh.internal.file.FileConfirmBodyRequest;
import com.tencentcloud.smh.internal.file.FileCopyRequest;
import com.tencentcloud.smh.internal.file.FileLinkRequest;
import com.tencentcloud.smh.internal.file.FileRequest;
import com.tencentcloud.smh.internal.file.FileThumbnailRequest;
import com.tencentcloud.smh.internal.file.FileUrlInfoRequest;
import com.tencentcloud.smh.internal.file.ObjectMetadata;
import com.tencentcloud.smh.internal.file.PutFilePartRequest;
import com.tencentcloud.smh.internal.file.PutFileRequest;
import com.tencentcloud.smh.internal.file.SmhDataSource;
import com.tencentcloud.smh.internal.file.SMHFileResponseHandler;
import com.tencentcloud.smh.internal.quota.QuotaRequest;
import com.tencentcloud.smh.internal.space.SpaceRequest;
import com.tencentcloud.smh.internal.stats.StatsRequest;
import com.tencentcloud.smh.internal.task.TaskRequest;
import com.tencentcloud.smh.internal.token.TokenRequest;
import com.tencentcloud.smh.internal.Constants;
import com.tencentcloud.smh.internal.FileLocks;
import com.tencentcloud.smh.internal.ResettableInputStream;
import com.tencentcloud.smh.internal.ReleasableInputStream;
import com.tencentcloud.smh.internal.SmhJsonResponseHandler;
import com.tencentcloud.smh.internal.SmhServiceResponse;
import com.tencentcloud.smh.internal.Unmarshaller;
import com.tencentcloud.smh.internal.Unmarshallers;
import com.tencentcloud.smh.internal.VoidSmhResponseHandler;
import com.tencentcloud.smh.model.batch.BatchResponse;
import com.tencentcloud.smh.model.directory.DirectoryContentsResponse;
import com.tencentcloud.smh.model.directory.DirectoryMoveResponse;
import com.tencentcloud.smh.model.directory.DirectoryInfoResponse;
import com.tencentcloud.smh.model.directory.DirectoryDeleteResponse;
import com.tencentcloud.smh.model.directory.DirectoryCopyResponse;
import com.tencentcloud.smh.model.directory.DirectoryCreateResponse;
import com.tencentcloud.smh.model.file.FileConfirmResponse;
import com.tencentcloud.smh.model.file.FileCopyResponse;
import com.tencentcloud.smh.model.file.FileDeleteResponse;
import com.tencentcloud.smh.model.file.FileInfoResponse;
import com.tencentcloud.smh.model.file.FileLinkToResponse;
import com.tencentcloud.smh.model.file.FileMoveResponse;
import com.tencentcloud.smh.model.file.FileUploadStatusResponse;
import com.tencentcloud.smh.model.file.FileUrlInfoResponse;
import com.tencentcloud.smh.model.file.SMHFile;
import com.tencentcloud.smh.model.file.UploadFileResponse;
import com.tencentcloud.smh.model.quota.QuotaCapacityResponse;
import com.tencentcloud.smh.model.quota.QuotaCreateResponse;
import com.tencentcloud.smh.model.space.LibraryUsageResponse;
import com.tencentcloud.smh.model.space.SpaceCreateResponse;
import com.tencentcloud.smh.model.space.SpaceExtensionResponse;
import com.tencentcloud.smh.model.space.SpaceResponse;
import com.tencentcloud.smh.model.space.SpaceSizeResponse;
import com.tencentcloud.smh.model.stats.StatsResponse;
import com.tencentcloud.smh.model.task.TaskResponse;
import com.tencentcloud.smh.model.token.TokenResponse;
import com.tencentcloud.smh.utils.IOUtils;
import com.tencentcloud.smh.utils.Jackson;
import com.tencentcloud.smh.utils.UrlEncoderUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class SMHClient implements SMH {

    private static final Logger log = LoggerFactory.getLogger(SMHClient.class);

    protected ClientConfig clientConfig;

    private SmhHttpClient smhHttpClient;
    private final VoidSmhResponseHandler voidSmhResponseHandler = new VoidSmhResponseHandler();


    public SMHClient(ClientConfig clientConfig) {
        super();

        this.clientConfig = clientConfig;
        this.smhHttpClient = new DefaultSmhHttpClient(clientConfig);
    }

    public void shutdown() {
        this.smhHttpClient.shutdown();
    }

    @Override
    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    static final String TOKEN_PATH = "/api/v1/token";
    static final String SPACE_PATH = "/api/v1/space";
    static final String SPACE_USAGE_PATH = "/api/v1/usage";
    static final String TASK_PATH = "/api/v1/task";
    static final String STATS_PATH = "/api/v1/stats";
    static final String QUOTA_PATH = "/api/v1/quota";
    static final String BATCH_PATH = "/api/v1/batch";
    static final String DIRECTORY_PATH = "/api/v1/directory";
    static final String DIRECTORY_CROSS_SPACE_PATH = "/api/v1/cross-space/directory";
    static final String FILE_PATH = "/api/v1/file";


    /**
     * <p>
     * Asserts that the specified parameter value is not <code>null</code> and if it is, throws an
     * <code>IllegalArgumentException</code> with the specified error message.
     * </p>
     *
     * @param parameterValue The parameter value being checked.
     * @param errorMessage The error message to include in the IllegalArgumentException if the
     *        specified parameter is null.
     */
    private void rejectNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    protected <X extends SmhServiceRequest> SmhHttpRequest<X> createRequest(String key,
                                                                            X originalRequest,
                                                                            HttpMethodName httpMethod) {
        SmhHttpRequest<X> httpRequest = new SmhHttpRequest<X>(originalRequest);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.setProtocol(HttpProtocol.https);
        httpRequest.setEndpoint(Constants.ENDPOINT);
        httpRequest.setResourcePath(key);
        httpRequest.setProgressListener(originalRequest.getGeneralProgressListener());
        return httpRequest;
    }

    protected <X extends SmhServiceRequest> SmhHttpRequest<X> createCosRequest(String domain,
                                                                               String path,
                                                                               X originalRequest,
                                                                               HttpMethodName httpMethod) {
        SmhHttpRequest<X> httpRequest = new SmhHttpRequest<X>(originalRequest);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.setProtocol(HttpProtocol.https);
        httpRequest.setEndpoint(domain);
        httpRequest.setResourcePath(path);
        httpRequest.setProgressListener(originalRequest.getGeneralProgressListener());
        return httpRequest;
    }

    /**
     * Adds the specified parameter to the specified request, if the parameter value is not null.
     *
     * @param request The request to add the parameter to.
     * @param paramName The parameter name.
     * @param paramValue The parameter value.
     */
    private static void addParameterIfNotNull(SmhHttpRequest<?> request,
                                              String paramName,
                                              Object paramValue) {
        if (paramValue != null) {
            request.addParameter(paramName, paramValue);
        }
    }

    private static void addPathIfNotNull(StringBuilder stringBuilder,
                                              Object paramValue) {
        if (paramValue != null && !paramValue.toString().trim().isEmpty()) {
            stringBuilder.append(UrlEncoderUtils.PATH_DELIMITER).append(UrlEncoderUtils.encode(paramValue.toString()));
        }
    }

    private <X, Y extends SmhServiceRequest> X invoke(SmhHttpRequest<Y> request,
                                                      Unmarshaller<X, InputStream> unmarshaller)
            throws SmhClientException, SmhServiceException {
        return invoke(request,new SmhJsonResponseHandler<>(unmarshaller));
    }

    private <X, Y extends SmhServiceRequest> X invoke(SmhHttpRequest<Y> request,
                                                      HttpResponseHandler<SmhServiceResponse<X>> responseHandler)
            throws SmhClientException, SmhServiceException {

        return this.smhHttpClient.exeute(request, responseHandler);
    }

    @Override
    public TokenResponse getAccessToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The tokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The tokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibrarySecret(),
                "The tokenRequest.LibrarySecret parameter must be specified setting the object tags");
        SmhHttpRequest<TokenRequest> request = createRequest(TOKEN_PATH, tokenRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "library_id", tokenRequest.getLibraryId());
        addParameterIfNotNull(request, "library_secret", tokenRequest.getLibrarySecret());
        addParameterIfNotNull(request, "space_id", tokenRequest.getSpaceId());
        addParameterIfNotNull(request, "user_id", tokenRequest.getUserId());
        addParameterIfNotNull(request, "client_id", tokenRequest.getClientId());
        addParameterIfNotNull(request, "session_id", tokenRequest.getSessionId());
        addParameterIfNotNull(request, "local_sync_id", tokenRequest.getLocalSyncId());
        addParameterIfNotNull(request, "period", tokenRequest.getPeriod());
        addParameterIfNotNull(request, "allow_space_tag", tokenRequest.getAllowSpaceTag());
        addParameterIfNotNull(request, "grant", tokenRequest.getGrant());
        return this.invoke(request, new Unmarshallers.AccessTokenUnmarshaller());
    }

    @Override
    public TokenResponse postCreateAccessToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The tokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The tokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibrarySecret(),
                "The tokenRequest.LibrarySecret parameter must be specified setting the object tags");

        SmhHttpRequest<TokenRequest> request = createRequest(TOKEN_PATH, tokenRequest, HttpMethodName.POST);
        addParameterIfNotNull(request, "library_id", tokenRequest.getLibraryId());
        addParameterIfNotNull(request, "library_secret", tokenRequest.getLibrarySecret());
        addParameterIfNotNull(request, "space_id", tokenRequest.getSpaceId());
        addParameterIfNotNull(request, "user_id", tokenRequest.getUserId());
        addParameterIfNotNull(request, "client_id", tokenRequest.getClientId());
        addParameterIfNotNull(request, "local_sync_id", tokenRequest.getLocalSyncId());
        addParameterIfNotNull(request, "period", tokenRequest.getPeriod());
        addParameterIfNotNull(request, "allow_space_tag", tokenRequest.getAllowSpaceTag());
        addParameterIfNotNull(request, "grant", tokenRequest.getGrant());
        if(tokenRequest.getTokenPostBodyRequest() != null) {
            try {
                String postBody = Jackson.toJSONString(tokenRequest.getTokenPostBodyRequest());
                this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
            } catch (JsonProcessingException e) {
                throw new SmhClientException("Unable to parse Json String.", e);
            }
        }
        return this.invoke(request, new Unmarshallers.AccessTokenUnmarshaller());
    }

    private void setContent(SmhHttpRequest<?> request, byte[] content) {
        request.setContent(new ByteArrayInputStream(content));
        request.addHeader(Headers.CONTENT_LENGTH, Integer.toString(content.length));
        request.addHeader(Headers.CONTENT_TYPE, "application/json");
    }

    @Override
    public TokenResponse extensionAccessToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The tokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The tokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getAccessToken(),
                "The tokenRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(TOKEN_PATH);
        addPathIfNotNull(path,tokenRequest.getLibraryId());
        addPathIfNotNull(path,tokenRequest.getAccessToken());
        SmhHttpRequest<TokenRequest> request = createRequest(path.toString(), tokenRequest, HttpMethodName.POST);
        return this.invoke(request, new Unmarshallers.AccessTokenUnmarshaller());
    }


    @Override
    public void deleteUserAllToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The tokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The tokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibrarySecret(),
                "The tokenRequest.LibrarySecret parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(TOKEN_PATH);
        addPathIfNotNull(path,tokenRequest.getLibraryId());
        SmhHttpRequest<TokenRequest> request = createRequest(path.toString(), tokenRequest, HttpMethodName.DELETE);
        addParameterIfNotNull(request, "library_secret", tokenRequest.getLibrarySecret());
        addParameterIfNotNull(request, "user_id", tokenRequest.getUserId());
        addParameterIfNotNull(request, "session_id", tokenRequest.getSessionId());
        this.invoke(request, voidSmhResponseHandler);
    }

    @Override
    public void deleteUserClientToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The tokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The tokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibrarySecret(),
                "The tokenRequest.LibrarySecret parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getClientId(),
                "The tokenRequest.ClientId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(TOKEN_PATH);
        addPathIfNotNull(path,tokenRequest.getLibraryId());
        SmhHttpRequest<TokenRequest> request = createRequest(path.toString(), tokenRequest, HttpMethodName.DELETE);
        addParameterIfNotNull(request, "library_secret", tokenRequest.getLibrarySecret());
        addParameterIfNotNull(request, "user_id", tokenRequest.getUserId());
        addParameterIfNotNull(request, "client_id", tokenRequest.getClientId());
        addParameterIfNotNull(request, "session_id", tokenRequest.getSessionId());
        this.invoke(request,voidSmhResponseHandler);
    }
    @Override
    public void deleteAccessToken(TokenRequest tokenRequest) {
        rejectNull(tokenRequest,
                "The accessTokenRequest parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getLibraryId(),
                "The accessTokenRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(tokenRequest.getAccessToken(),
                "The accessTokenRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(TOKEN_PATH);
        addPathIfNotNull(path,tokenRequest.getLibraryId());
        addPathIfNotNull(path,tokenRequest.getAccessToken());
        SmhHttpRequest<TokenRequest> request = createRequest(path.toString(), tokenRequest, HttpMethodName.DELETE);
        this.invoke(request, voidSmhResponseHandler);
    }


    @Override
    public SpaceCreateResponse createSpace(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getAccessToken(),
                "The spaceRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.POST);

        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());

        if(spaceRequest.getSpacePostBodyRequest() != null) {
            try {
                String postBody = Jackson.toJSONString(spaceRequest.getSpacePostBodyRequest());
                this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
            } catch (JsonProcessingException e) {
                throw new SmhClientException("Unable to parse Json String.", e);
            }
        }
        return this.invoke(request, new Unmarshallers.SpaceCreateUnmarshaller());
    }


    @Override
    public LibraryUsageResponse getLibraryUsage(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_USAGE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        return this.invoke(request, new Unmarshallers.SpaceUnmarshaller());
    }

    @Override
    public List<LibraryUsageResponse> getSpaceUsageList(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getSpaceIds(),
                "The spaceRequest.SpaceIds parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_USAGE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        addPathIfNotNull(path,spaceRequest.getSpaceIds());
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        return this.invoke(request, new Unmarshallers.SpaceListUsageUnmarshaller());
    }

    @Override
    public List<SpaceResponse> getSpaceList(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        return this.invoke(request, new Unmarshallers.SpaceListUnmarshaller());
    }

    @Override
    public SpaceSizeResponse getSpaceSize(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getAccessToken(),
                "The spaceRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getSpaceId(),
                "The spaceRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        addPathIfNotNull(path,spaceRequest.getSpaceId());
        addPathIfNotNull(path,"size");
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        return this.invoke(request, new Unmarshallers.SpaceSizeUnmarshaller());
    }

    @Override
    public void deleteSpace(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getAccessToken(),
                "The spaceRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getSpaceId(),
                "The spaceRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        addPathIfNotNull(path,spaceRequest.getSpaceId());
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.DELETE);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        this.invoke(request, voidSmhResponseHandler);
    }

    @Override
    public SpaceExtensionResponse getSpaceExtension(SpaceRequest spaceRequest) {
        rejectNull(spaceRequest,
                "The spaceRequest parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getLibraryId(),
                "The spaceRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getAccessToken(),
                "The spaceRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(spaceRequest.getSpaceId(),
                "The spaceRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(SPACE_PATH);
        addPathIfNotNull(path,spaceRequest.getLibraryId());
        addPathIfNotNull(path,spaceRequest.getSpaceId());
        addPathIfNotNull(path,"extension");
        SmhHttpRequest<SpaceRequest> request = createRequest(path.toString(), spaceRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", spaceRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", spaceRequest.getUserId());
        return this.invoke(request, new Unmarshallers.SpaceExtensionUnmarshaller());
    }

    @Override
    public List<TaskResponse> getTask(TaskRequest taskRequest) {
        rejectNull(taskRequest,
                "The taskRequest parameter must be specified setting the object tags");
        rejectNull(taskRequest.getLibraryId(),
                "The taskRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(taskRequest.getAccessToken(),
                "The taskRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(taskRequest.getSpaceId(),
                "The taskRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(TASK_PATH);
        addPathIfNotNull(path,taskRequest.getLibraryId());
        addPathIfNotNull(path,taskRequest.getSpaceId());
        addPathIfNotNull(path,taskRequest.getTaskIdList());

        SmhHttpRequest<TaskRequest> request = createRequest(path.toString(), taskRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", taskRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", taskRequest.getUserId());
        return this.invoke(request, new Unmarshallers.TaskListUnmarshaller());
    }

    @Override
    public List<StatsResponse> getStats(StatsRequest statsRequest) {
        rejectNull(statsRequest,
                "The statsRequest parameter must be specified setting the object tags");
        rejectNull(statsRequest.getLibraryId(),
                "The statsRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(statsRequest.getAccessToken(),
                "The statsRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(STATS_PATH);
        addPathIfNotNull(path,statsRequest.getLibraryId());
        addPathIfNotNull(path,"space-size");
        SmhHttpRequest<StatsRequest> request = createRequest(path.toString(), statsRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", statsRequest.getAccessToken());
        addParameterIfNotNull(request, "space_tag", statsRequest.getSpaceTag());
        return this.invoke(request, new Unmarshallers.StatsListUnmarshaller());
    }

    @Override
    public  QuotaCreateResponse createQuota(QuotaRequest quotaRequest) {
        rejectNull(quotaRequest,
                "The quotaRequest parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getLibraryId(),
                "The quotaRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getAccessToken(),
                "The quotaRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(QUOTA_PATH);
        addPathIfNotNull(path,quotaRequest.getLibraryId());
        SmhHttpRequest<QuotaRequest> request = createRequest(path.toString(), quotaRequest, HttpMethodName.POST);

        addParameterIfNotNull(request, "access_token", quotaRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", quotaRequest.getUserId());

        if(quotaRequest.getQuotaBodyRequest()!=null) {
            try {
                String postBody = Jackson.toJSONString(quotaRequest.getQuotaBodyRequest());
                this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
            } catch (JsonProcessingException e) {
                throw new SmhClientException("Unable to parse Json String.", e);
            }
        }
        return this.invoke(request, new Unmarshallers.QuotaCreateUnmarshaller());
    }

    @Override
    public QuotaCapacityResponse getQuotaCapacity(QuotaRequest quotaRequest) {
        rejectNull(quotaRequest,
                "The quotaRequest parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getLibraryId(),
                "The quotaRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getSpaceId(),
                "The quotaRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getAccessToken(),
                "The quotaRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(QUOTA_PATH);
        addPathIfNotNull(path,quotaRequest.getLibraryId());
        addPathIfNotNull(path,quotaRequest.getSpaceId());
        SmhHttpRequest<QuotaRequest> request = createRequest(path.toString(), quotaRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "access_token", quotaRequest.getAccessToken());
        addParameterIfNotNull(request, "space_tag", quotaRequest.getUserId());
        return this.invoke(request, new Unmarshallers.QuotaCapacityUnmarshaller());
    }

    @Override
    public void updateQuotaCapacity(QuotaRequest quotaRequest) {
        rejectNull(quotaRequest,
                "The quotaRequest parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getLibraryId(),
                "The quotaRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getAccessToken(),
                "The quotaRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(quotaRequest.getSpaceId(),
                "The quotaRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(QUOTA_PATH);
        addPathIfNotNull(path,quotaRequest.getLibraryId());
        addPathIfNotNull(path,quotaRequest.getSpaceId());
        SmhHttpRequest<QuotaRequest> request = createRequest(path.toString(), quotaRequest, HttpMethodName.PUT);

        addParameterIfNotNull(request, "access_token", quotaRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", quotaRequest.getUserId());

        if(quotaRequest.getQuotaBodyRequest()!=null) {
            try {
                String postBody = Jackson.toJSONString(quotaRequest.getQuotaBodyRequest());
                this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
            } catch (JsonProcessingException e) {
                throw new SmhClientException("Unable to parse Json String.", e);
            }
        }
        this.invoke(request, voidSmhResponseHandler);
    }

    @Override
    public BatchResponse batchCopy(BatchRequest batchRequest) {
        rejectNull(batchRequest,
                "The batchRequest parameter must be specified setting the object tags");
        rejectNull(batchRequest.getLibraryId(),
                "The batchRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getAccessToken(),
                "The batchRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(batchRequest.getSpaceId(),
                "The batchRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getBatchCopyRequests(),
                "The batchRequest.BatchCopyRequests parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(BATCH_PATH);
        addPathIfNotNull(path,batchRequest.getLibraryId());
        addPathIfNotNull(path,batchRequest.getSpaceId());
        SmhHttpRequest<BatchRequest> request = createRequest(path.toString(), batchRequest, HttpMethodName.POST);
        request.addParameter("copy",null);
        addParameterIfNotNull(request, "share_access_token", batchRequest.getShareAccessToken());
        addParameterIfNotNull(request, "access_token", batchRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", batchRequest.getUserId());
        String postBody = Jackson.toJsonString(batchRequest.getBatchCopyRequests());
        this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        return this.invoke(request, new Unmarshallers.BatchUnmarshaller());
    }

    @Override
    public BatchResponse batchMove(BatchRequest batchRequest) {
        rejectNull(batchRequest,
                "The batchRequest parameter must be specified setting the object tags");
        rejectNull(batchRequest.getLibraryId(),
                "The batchRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getAccessToken(),
                "The batchRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(batchRequest.getSpaceId(),
                "The batchRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getBatchMoveRequests(),
                "The batchRequest.BatchMoveRequests parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(BATCH_PATH);
        addPathIfNotNull(path,batchRequest.getLibraryId());
        addPathIfNotNull(path,batchRequest.getSpaceId());
        SmhHttpRequest<BatchRequest> request = createRequest(path.toString(), batchRequest, HttpMethodName.POST);
        request.addParameter("move",null);
        addParameterIfNotNull(request, "access_token", batchRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", batchRequest.getUserId());

        try {
            String postBody = Jackson.toJSONString(batchRequest.getBatchMoveRequests());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }

        return this.invoke(request, new Unmarshallers.BatchUnmarshaller());
    }

    @Override
    public  BatchResponse batchDelete(BatchRequest batchRequest) {
        rejectNull(batchRequest,
                "The batchRequest parameter must be specified setting the object tags");
        rejectNull(batchRequest.getLibraryId(),
                "The batchRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getAccessToken(),
                "The batchRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(batchRequest.getSpaceId(),
                "The batchRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(batchRequest.getBatchDeleteRequests(),
                "The batchRequest.BatchDeleteRequests parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(BATCH_PATH);
        addPathIfNotNull(path,batchRequest.getLibraryId());
        addPathIfNotNull(path,batchRequest.getSpaceId());
        SmhHttpRequest<BatchRequest> request = createRequest(path.toString(), batchRequest, HttpMethodName.POST);
        request.addParameter("delete",null);
        addParameterIfNotNull(request, "access_token", batchRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", batchRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(batchRequest.getBatchDeleteRequests());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }

        return this.invoke(request, new Unmarshallers.BatchUnmarshaller());
    }

    @Override
    public DirectoryCreateResponse createDirectory(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getAccessToken(),
                "The directoryRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(), directoryRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request, "conflict_resolution_strategy",
                directoryRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());

        return this.invoke(request, new Unmarshallers.DirectoryCreateUnmarshaller());
    }

    @Override
    public DirectoryInfoResponse getDirectoryInfo(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(), directoryRequest, HttpMethodName.GET);
        request.addParameter("info",null);
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());

        return this.invoke(request, new Unmarshallers.DirectoryUnmarshaller());
    }

    @Override
    public DirectoryCopyResponse copyDirectory(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryCopyRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryCopyRequest(),
                "The directoryRequest.DirectoryCopyRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryCopyRequest().getCopyFrom(),
                "The directoryRequest.DirectoryCopyRequest.CopyFrom parameter " +
                        "must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(), directoryRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request, "conflict_resolution_strategy",
                directoryRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(directoryRequest.getDirectoryCopyRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.DirectoryCopyUnmarshaller());
    }

    @Override
    public DirectoryCopyResponse copyDirectoryCrossSpace(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirPath(),
                "The directoryRequest.DirPath parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryCopyRequest(),
                "The directoryRequest.DirectoryCopyRequest parameter " +
                        "must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryCopyRequest().getCopyFromSpaceId(),
                "The directoryRequest.DirectoryCopyRequest.CopyFromSpaceId parameter" +
                        " must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryCopyRequest().getCopyFrom(),
                "The directoryRequest.DirectoryCopyRequest.CopyFrom parameter" +
                        " must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_CROSS_SPACE_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,"copy");
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(), directoryRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request, "conflict_resolution_strategy",
                directoryRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(directoryRequest.getDirectoryCopyRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.DirectoryCopyUnmarshaller());
    }

    @Override
    public boolean doesDirectoryExist(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(),
                directoryRequest, HttpMethodName.HEAD);
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());
        try {
            this.invoke(request,voidSmhResponseHandler);
            return true;
        } catch (SmhServiceException sse){
            if (HttpStatus.SC_NOT_FOUND == sse.getStatusCode()) {
                return false;
            }
            throw sse;
        }
    }

    @Override
    public DirectoryDeleteResponse deleteDirectory(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirPath(),
                "The directoryRequest.DirPath parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getAccessToken(),
                "The directoryRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request =
                createRequest(path.toString(), directoryRequest, HttpMethodName.DELETE);
        addParameterIfNotNull(request, "permanent",  directoryRequest.getPermanent());
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());
        return this.invoke(request, new Unmarshallers.DirectoryDeleteUnmarshaller());
    }

    @Override
    public DirectoryMoveResponse moveDirectory(DirectoryRequest directoryRequest) {
        rejectNull(directoryRequest,
                "The directoryRequest parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getLibraryId(),
                "The directoryMoveRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getSpaceId(),
                "The directoryMoveRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirPath(),
                "The directoryMoveRequest.DirPath parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getAccessToken(),
                "The directoryMoveRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryMoveRequest(),
                "The directoryMoveRequest.DirectoryMoveRequest parameter " +
                        "must be specified setting the object tags");
        rejectNull(directoryRequest.getDirectoryMoveRequest().getFrom(),
                "The directoryMoveRequest.DirectoryMoveRequest.From parameter " +
                        "must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryRequest.getLibraryId());
        addPathIfNotNull(path,directoryRequest.getSpaceId());
        addPathIfNotNull(path,directoryRequest.getDirPath());
        SmhHttpRequest<DirectoryRequest> request = createRequest(path.toString(), directoryRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request,"conflict_resolution_strategy",directoryRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request,"move_authority",directoryRequest.getMoveAuthority());
        addParameterIfNotNull(request, "permanent",  directoryRequest.getPermanent());
        addParameterIfNotNull(request, "access_token", directoryRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(directoryRequest.getDirectoryMoveRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.DirectoryMoveUnmarshaller());
    }

    @Override
    public DirectoryContentsResponse getDirectoryContents(DirectoryDetailRequest directoryDetailRequest) {
        rejectNull(directoryDetailRequest,
                "The directoryDetailRequest parameter must be specified setting the object tags");
        rejectNull(directoryDetailRequest.getLibraryId(),
                "The directoryDetailRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(directoryDetailRequest.getSpaceId(),
                "The directoryDetailRequest.SpaceId parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,directoryDetailRequest.getLibraryId());
        addPathIfNotNull(path,directoryDetailRequest.getSpaceId());
        addPathIfNotNull(path,directoryDetailRequest.getDirPath());
        SmhHttpRequest<DirectoryDetailRequest> request =
                createRequest(path.toString(), directoryDetailRequest, HttpMethodName.GET);
        addParameterIfNotNull(request,"marker",directoryDetailRequest.getMarker());
        addParameterIfNotNull(request,"limit",directoryDetailRequest.getLimit());
        addParameterIfNotNull(request, "page",  directoryDetailRequest.getPage());
        addParameterIfNotNull(request,  "page_size",directoryDetailRequest.getPageSize());
        addParameterIfNotNull(request,  "order_by",directoryDetailRequest.getOrderBy());
        addParameterIfNotNull(request,  "order_by_type",directoryDetailRequest.getOrderByType());
        addParameterIfNotNull(request,  "filter",directoryDetailRequest.getDirectoryFilter());
        addParameterIfNotNull(request,  "sort_type",directoryDetailRequest.getSortType());
        addParameterIfNotNull(request, "access_token", directoryDetailRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", directoryDetailRequest.getUserId());
        return this.invoke(request, new Unmarshallers.DirectoryDetailUnmarshaller());
    }


    @Override
    public FileConfirmResponse putFile(PutFileRequest putFileRequest) {
        rejectNull(putFileRequest,
                "The putFileRequest parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getLibraryId(),
                "The putFileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getSpaceId(),
                "The putFileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getFilePath(),
                "The putFileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getAccessToken(),
                "The putFileRequest.AccessToken parameter must be specified setting the object tags");
        if (putFileRequest.getFile() == null && putFileRequest.getInputStream()==null){
            throw new IllegalArgumentException("unable to find file to upload");
        }
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,putFileRequest.getLibraryId());
        addPathIfNotNull(path,putFileRequest.getSpaceId());
        addPathIfNotNull(path,putFileRequest.getFilePath());
        SmhHttpRequest<PutFileRequest> request = createRequest(path.toString(), putFileRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request,"filesize",putFileRequest.getFileSize());
        addParameterIfNotNull(request,"conflict_resolution_strategy",putFileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", putFileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", putFileRequest.getUserId());
        ObjectMetadata metadata = putFileRequest.getMetadata();

        populateRequestMetadata(request, metadata);

        UploadFileResponse uploadFileResponse = this.invoke(request, new Unmarshallers.UploadFileUnmarshaller());
        SmhHttpRequest<PutFileRequest> requestCos =
                createCosRequest(uploadFileResponse.getDomain(),
                        uploadFileResponse.getPath(),
                        putFileRequest, HttpMethodName.PUT);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setUserMetadata(uploadFileResponse.getHeaders());
        populateCosRequestMetadata(requestCos, objectMetadata);
        final File file = putFileRequest.getFile();
        final InputStream isOrig = putFileRequest.getInputStream();
        InputStream input = isOrig;

        try {
            if (file == null) {
                // When input is a FileInputStream, this wrapping enables
                // unlimited mark-and-reset
                input = ReleasableInputStream.wrap(input);
            } else {
                input = ResettableInputStream.newResettableInputStream(file,
                        "Unable to find file to upload");
            }
            requestCos.setContent(input);
            this.invoke(requestCos, voidSmhResponseHandler);
        }catch (Throwable t){
            throw new SmhServiceException(t.getMessage());
        } finally {
            SmhDataSource.Utils.cleanupDataSource(putFileRequest, file, isOrig, input, log);
        }
        // 完成上传文件
        FileRequest fileRequest=new FileRequest();
        fileRequest.setLibraryId(putFileRequest.getLibraryId());
        fileRequest.setSpaceId(putFileRequest.getSpaceId());
        fileRequest.setConfirmKey(uploadFileResponse.getConfirmKey());
        fileRequest.setAccessToken(putFileRequest.getAccessToken());
        fileRequest.setConflictResolutionStrategy(putFileRequest.getConflictResolutionStrategy());
        fileRequest.setUserId(putFileRequest.getUserId());
        FileConfirmBodyRequest fileConfirmBodyRequest = new FileConfirmBodyRequest();
        fileConfirmBodyRequest.setCrc64(putFileRequest.getCrc64());
        fileRequest.setFileConfirmBodyRequest(fileConfirmBodyRequest);
        return confirmPutFile(fileRequest);
    }

    @Override
    public  UploadFileResponse initiateMultipartUpload(PutFileRequest putFileRequest) {
        rejectNull(putFileRequest,
                "The putFileRequest parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getLibraryId(),
                "The putFileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getSpaceId(),
                "The putFileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getFilePath(),
                "The putFileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(putFileRequest.getAccessToken(),
                "The putFileRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,putFileRequest.getLibraryId());
        addPathIfNotNull(path,putFileRequest.getSpaceId());
        addPathIfNotNull(path,putFileRequest.getFilePath());
        SmhHttpRequest<PutFileRequest> request = createRequest(path.toString(), putFileRequest, HttpMethodName.POST);
        request.addParameter("multipart",null);
        addParameterIfNotNull(request,"conflict_resolution_strategy",putFileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request,"filesize",putFileRequest.getFileSize());
        addParameterIfNotNull(request, "access_token", putFileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", putFileRequest.getUserId());
        ObjectMetadata metadata = putFileRequest.getMetadata();

        populateRequestMetadata(request, metadata);

        return this.invoke(request, new Unmarshallers.UploadFileUnmarshaller());
    }

    @Override
    public  UploadFileResponse putPartFileReNewal(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getConfirmKey(),
                "The fileRequest.ConfirmKey parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getConfirmKey());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.POST);
        request.addParameter("renew",null);
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        return this.invoke(request, new Unmarshallers.UploadFileUnmarshaller());
    }
    @Override
    public void uploadPart(PutFilePartRequest putFilePartRequest) {
        rejectNull(putFilePartRequest,
                "The putFilePartRequest parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getDomain(),
                "The putFilePartRequest.Domain parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getPath(),
                "The putFileRequest.Path parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getPartNumber(),
                "The putFileRequest.PartNumber parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getUploadId(),
                "The putFileRequest.UploadId parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getMetadata(),
                "The putFileRequest.Metadata parameter must be specified setting the object tags");
        rejectNull(putFilePartRequest.getMetadata().getUserMetadata(),
                "The putFileRequest.Metadata.UserMetadata parameter " +
                        "must be specified setting the object tags");
        if (putFilePartRequest.getFile() == null && putFilePartRequest.getInputStream() == null){
            throw new IllegalArgumentException("unable to find file to upload");
        }
        SmhHttpRequest<PutFilePartRequest> requestCos = createCosRequest(putFilePartRequest.getDomain(),
                putFilePartRequest.getPath(), putFilePartRequest, HttpMethodName.PUT);
        populateCosRequestMetadata(requestCos, putFilePartRequest.getMetadata());
        addParameterIfNotNull(requestCos,"uploadId",putFilePartRequest.getUploadId());
        addParameterIfNotNull(requestCos, "partNumber", putFilePartRequest.getPartNumber());
        final File file = putFilePartRequest.getFile();
        final InputStream isOrig = putFilePartRequest.getInputStream();
        InputStream input = isOrig;
        try {
            if (file == null) {
                // When input is a FileInputStream, this wrapping enables
                // unlimited mark-and-reset
                input = ReleasableInputStream.wrap(input);
            } else {
                input = ResettableInputStream.newResettableInputStream(file,
                        "Unable to find file to upload");
            }requestCos.setContent(input);
           this.invoke(requestCos, voidSmhResponseHandler);

        }catch (SmhServiceException e){
            throw e;
        } finally {
            SmhDataSource.Utils.cleanupDataSource(putFilePartRequest, file, isOrig, input, log);
        }
    }

    @Override
    public FileConfirmResponse confirmPutFile(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getConfirmKey(),
                "The fileRequest.ConfirmKey parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getConfirmKey());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.POST);
        request.addParameter("confirm", null);
        addParameterIfNotNull(request,"conflict_resolution_strategy",fileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        try {
            if(fileRequest.getFileConfirmBodyRequest() != null &&
                    fileRequest.getFileConfirmBodyRequest().getCrc64() != null) {
                String postBody = Jackson.toJSONString(fileRequest.getFileConfirmBodyRequest());
                this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
            }
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        FileConfirmResponse fileConfirmResponse = this.invoke(request, new Unmarshallers.ConfirmPutFileUnmarshaller());
        fileConfirmResponse.setConfirmKey(fileRequest.getConfirmKey());
        return fileConfirmResponse;
    }

    @Override
    public FileInfoResponse getFileInfo(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(DIRECTORY_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.GET);
        request.addParameter("info", null);
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        return this.invoke(request, new Unmarshallers.GetFileInfoUnmarshaller());
    }

    @Override
    public FileLinkToResponse linkFile(FileLinkRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLinkBodyRequest(),
                "The fileRequest.LinkBodyRequest must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLinkBodyRequest().getLinkTo(),
                "The fileRequest.LinkBodyRequest.LinkTo parameter must be specified setting the object tags");

        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.PUT);
        addParameterIfNotNull(request,"conflict_resolution_strategy",fileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(fileRequest.getLinkBodyRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.LinkFileUnmarshaller());
    }

    /**
     * <p>
     * Populates the specified request object with the appropriate headers from the
     * {@link Map<String,String>} object.
     * </p>
     *
     * @param request The request to populate with headers.
     * @param metadata The metadata containing the header information to include in the request.
     */
    protected static void populateRequestMetadata(SmhHttpRequest<?> request,
                                                  ObjectMetadata metadata) {

        if(metadata == null || metadata.getUserMetadata() == null){
            return;
        }
        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata != null) {
            for (Map.Entry<String, String> entry : userMetadata.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null)
                    key = key.trim();
                if (value != null)
                    value = value.trim();
                request.addHeader(Headers.SMH_USER_METADATA_PREFIX + key, value);
            }
        }
    }
    /**
     * <p>
     * Populates the specified request object with the appropriate headers from the
     * {@link Map<String,String>} object.
     * </p>
     *
     * @param request The request to populate with headers.
     * @param metadata The metadata containing the header information to include in the request.
     */
    protected static void populateCosRequestMetadata(SmhHttpRequest<?> request,
                                                  ObjectMetadata metadata) {
        if (metadata == null || metadata.getUserMetadata() == null) {
            return;
        }
        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata != null) {
            for (Map.Entry<String, String> entry : userMetadata.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null)
                    key = key.trim();
                if (value != null)
                    value = value.trim();
                request.addHeader(key, value);
            }
        }
    }

    @Override
    public FileCopyResponse copyFile(FileCopyRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFileCopyBodyRequest(),
                "The fileRequest.FileCopyBodyRequest parameter must be specified setting the object tags");

        rejectNull(fileRequest.getFileCopyBodyRequest().getCopyFrom(),
                "The fileRequest.FileCopyBodyReques.CopyFrom parameter " +
                        "must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());

        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.PUT);

        addParameterIfNotNull(request,"conflict_resolution_strategy",fileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(fileRequest.getFileCopyBodyRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.CopyFileUnmarshaller());
    }

    @Override
    public  FileUploadStatusResponse getFileUploadStatus(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getConfirmKey(),
                "The fileRequest.ConfirmKey parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getConfirmKey());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.GET);

        request.addParameter("upload",null);
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());

        return this.invoke(request, new Unmarshallers.FileUploadStatusUnmarshaller());
    }

    @Override
    public  FileUrlInfoResponse getFileUrlInfo(FileUrlInfoRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.GET);
        request.addParameter("info",null);
        addParameterIfNotNull(request,"history_id",fileRequest.getHistoryId());
        addParameterIfNotNull(request,"content_disposition",fileRequest.getContentDisposition());
        addParameterIfNotNull(request,"purpose",fileRequest.getPurpose());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());

        return this.invoke(request, new Unmarshallers.FileUrlInfoUnmarshaller());
    }

    @Override
    public void cancelUploadFile(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getConfirmKey(),
                "The fileRequest.ConfirmKey parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getConfirmKey());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.DELETE);
        request.addParameter("upload",null);
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());

        this.invoke(request, voidSmhResponseHandler);
    }

    @Override
    public FileDeleteResponse deleteFile(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.DELETE);

        addParameterIfNotNull(request,"permanent",fileRequest.getPermanent());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());

        return this.invoke(request, new Unmarshallers.FileDeleteUnmarshaller());
    }

    @Override
    public SMHFile getFileThumbnail(FileThumbnailRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.GET);
        request.addParameter("preview",null);
        addParameterIfNotNull(request,"size",fileRequest.getSize());
        addParameterIfNotNull(request,"scale",fileRequest.getScale());
        addParameterIfNotNull(request,"width_size",fileRequest.getWidthSize());
        addParameterIfNotNull(request,"height_size",fileRequest.getHeightSize());
        addParameterIfNotNull(request,"frame_number",fileRequest.getFrameNumber());
        addParameterIfNotNull(request,"purpose",fileRequest.getPurpose());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());

        SMHFile smhFile = this.invoke(request, new SMHFileResponseHandler());
        if(fileRequest.getLocalFile() != null) {
            File parentDirectory = fileRequest.getLocalFile().getParentFile();
            if ( parentDirectory != null && !parentDirectory.exists() ) {
                if (!(parentDirectory.mkdirs())) {
                    throw new SmhClientException(
                            "Unable to create directory in the path"
                                    + parentDirectory.getAbsolutePath());
                }
            }
            if (!FileLocks.lock(fileRequest.getLocalFile())) {
                throw new FileLockException("Fail to lock " + fileRequest.getLocalFile());
            }
            OutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        fileRequest.getLocalFile()));
                byte[] buffer = new byte[1024 * 10];
                int bytesRead;
                while ((bytesRead = smhFile.getObjectContent().read(buffer)) > -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }catch (Exception e){
                smhFile.getObjectContent().abort();
                throw new SmhClientException(
                        "Unable to input the locationFile"
                                +e);
            }finally {
                IOUtils.closeQuietly(outputStream,log);
                FileLocks.unlock(fileRequest.getLocalFile());
                IOUtils.closeQuietly(smhFile.getObjectContent(),log);
            }
        }
        return smhFile;
    }

    @Override
    public SMHFile downloadFile(FileUrlInfoRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(fileRequest.getPurpose(),
                "The fileRequest.Purpose parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.GET);

        addParameterIfNotNull(request,"history_id",fileRequest.getHistoryId());
        addParameterIfNotNull(request,"purpose",fileRequest.getPurpose());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        SMHFile smhFile =  this.invoke(request, new SMHFileResponseHandler());
        if(fileRequest.getLocalFile() != null) {
            File parentDirectory = fileRequest.getLocalFile().getParentFile();
            if ( parentDirectory != null && !parentDirectory.exists() ) {
                if (!(parentDirectory.mkdirs())) {
                    throw new SmhClientException(
                            "Unable to create directory in the path"
                                    + parentDirectory.getAbsolutePath());
                }
            }
            if (!FileLocks.lock(fileRequest.getLocalFile())) {
                throw new FileLockException("Fail to lock " + fileRequest.getLocalFile());
            }
            OutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        fileRequest.getLocalFile()));
                byte[] buffer = new byte[1024 * 10];
                int bytesRead;
                while ((bytesRead = smhFile.getObjectContent().read(buffer)) > -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }catch (Exception e){
                smhFile.getObjectContent().abort();
                throw new SmhClientException(
                        "Unable to input the locationFile"
                                +e);
            }finally {
                IOUtils.closeQuietly(outputStream,log);
                FileLocks.unlock(fileRequest.getLocalFile());
                IOUtils.closeQuietly(smhFile.getObjectContent(),log);
            }
        }
        return smhFile;
    }

    @Override
    public FileMoveResponse moveFile(FileRequest fileRequest) {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        rejectNull(fileRequest.getAccessToken(),
                "The fileRequest.AccessToken parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFileMoveBodyRequest(),
                "The fileRequest.FileMoveBodyRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFileMoveBodyRequest().getFrom(),
                "The fileRequest.FileMoveBodyRequest.From parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.PUT);

        addParameterIfNotNull(request,"conflict_resolution_strategy",fileRequest.getConflictResolutionStrategy());
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        try {
            String postBody = Jackson.toJSONString(fileRequest.getFileMoveBodyRequest());
            this.setContent(request, postBody.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new SmhClientException("Unable to parse Json String.", e);
        }
        return this.invoke(request, new Unmarshallers.FileMoveUnmarshaller());
    }

    @Override
    public boolean doesFileExist(FileUrlInfoRequest fileRequest)throws SmhClientException,SmhServiceException {
        rejectNull(fileRequest,
                "The fileRequest parameter must be specified setting the object tags");
        rejectNull(fileRequest.getLibraryId(),
                "The fileRequest.LibraryId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getSpaceId(),
                "The fileRequest.SpaceId parameter must be specified setting the object tags");
        rejectNull(fileRequest.getFilePath(),
                "The fileRequest.FilePath parameter must be specified setting the object tags");
        StringBuilder path = new StringBuilder();
        path.append(FILE_PATH);
        addPathIfNotNull(path,fileRequest.getLibraryId());
        addPathIfNotNull(path,fileRequest.getSpaceId());
        addPathIfNotNull(path,fileRequest.getFilePath());
        SmhHttpRequest<FileUrlInfoRequest> request = createRequest(path.toString(), fileRequest, HttpMethodName.HEAD);
        addParameterIfNotNull(request, "access_token", fileRequest.getAccessToken());
        addParameterIfNotNull(request, "user_id", fileRequest.getUserId());
        addParameterIfNotNull(request,"history_id",fileRequest.getHistoryId());
        try {
            this.invoke(request,voidSmhResponseHandler);
            return true;
        } catch (SmhServiceException sse){
            if (HttpStatus.SC_NOT_FOUND == sse.getStatusCode()) {
                return false;
            }
            throw sse;
        }

    }
}

