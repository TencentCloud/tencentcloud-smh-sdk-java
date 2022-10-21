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

import com.tencentcloud.smh.model.batch.BatchResponse;
import com.tencentcloud.smh.model.batch.BatchAsynResponse;
import com.tencentcloud.smh.model.batch.BatchSyncResponse;
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
import com.tencentcloud.smh.utils.Jackson;
import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/*** Collection of unmarshallers for SMH JSON responses. */

public class Unmarshallers {


    public static final class AccessTokenUnmarshaller
            implements Unmarshaller<TokenResponse, InputStream> {

        public TokenResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), TokenResponse.class);
        }
    }

    private static StringBuilder getStringBuilder(InputStream in) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        return responseStrBuilder;
    }


    public static final class SpaceUnmarshaller
            implements Unmarshaller<LibraryUsageResponse, InputStream> {

        public LibraryUsageResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), LibraryUsageResponse.class);
        }
    }

    public static final class SpaceCreateUnmarshaller
            implements Unmarshaller<SpaceCreateResponse, InputStream> {

        public SpaceCreateResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), SpaceCreateResponse.class);
        }
    }

    public static final class SpaceSizeUnmarshaller
            implements Unmarshaller<SpaceSizeResponse, InputStream> {

        public SpaceSizeResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), SpaceSizeResponse.class);
        }
    }

    public static final class SpaceExtensionUnmarshaller
            implements Unmarshaller<SpaceExtensionResponse, InputStream> {

        public SpaceExtensionResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), SpaceExtensionResponse.class);
        }
    }

    public static final class SpaceListUsageUnmarshaller
            implements Unmarshaller<List<LibraryUsageResponse>, InputStream> {

        public List<LibraryUsageResponse> unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return Jackson.fromListJsonString(responseStrBuilder.toString(), LibraryUsageResponse.class);
        }
    }

    public static final class SpaceListUnmarshaller
            implements  Unmarshaller<List<SpaceResponse>, InputStream> {
        public  List<SpaceResponse> unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return Jackson.fromListJsonString(responseStrBuilder.toString(),SpaceResponse.class);
        }
    }

    public static final class TaskListUnmarshaller
            implements  Unmarshaller<List<TaskResponse>, InputStream> {
        public  List<TaskResponse> unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return Jackson.fromListJsonString(responseStrBuilder.toString(),TaskResponse.class);
        }
    }


        public static final class StatsListUnmarshaller
            implements  Unmarshaller<List<StatsResponse>, InputStream> {
        public  List<StatsResponse> unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return Jackson.fromListJsonString(responseStrBuilder.toString(), StatsResponse.class);
        }
    }

    public static final class QuotaCreateUnmarshaller
            implements Unmarshaller<QuotaCreateResponse, InputStream> {

        public QuotaCreateResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), QuotaCreateResponse.class);
        }
    }

    public static final class QuotaCapacityUnmarshaller
            implements Unmarshaller<QuotaCapacityResponse, InputStream> {

        public QuotaCapacityResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), QuotaCapacityResponse.class);
        }
    }



    public static final class BatchUnmarshaller
            implements Unmarshaller<BatchResponse, InputStream> {

        public BatchResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            BatchResponse batchResponse =new BatchResponse();
            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_MULTI_STATUS == statusCode) {
                BatchSyncResponse syncResponse =
                        Jackson.fromJsonString(responseStrBuilder.toString(), BatchSyncResponse.class);
                batchResponse.setSyncResponse(syncResponse);
            }
            if (HttpStatus.SC_ACCEPTED == statusCode) {
                BatchAsynResponse asynResponse =
                        Jackson.fromJsonString(responseStrBuilder.toString(), BatchAsynResponse.class);
                batchResponse.setAsynResponse(asynResponse);
            }
            return  batchResponse;
        }
    }
    public static final class DirectoryCreateUnmarshaller
            implements Unmarshaller<DirectoryCreateResponse, InputStream> {

        public DirectoryCreateResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if (in == null){
                return new DirectoryCreateResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if(responseStrBuilder.length() == 0) {
                return new DirectoryCreateResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryCreateResponse.class);
        }
    }

    public static final class DirectoryUnmarshaller
            implements Unmarshaller<DirectoryInfoResponse, InputStream> {

        public DirectoryInfoResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if (in == null){
                return new DirectoryInfoResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new DirectoryInfoResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryInfoResponse.class);
        }
    }

    public static final class DirectoryCopyUnmarshaller
            implements Unmarshaller<DirectoryCopyResponse, InputStream> {

        public DirectoryCopyResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if (in == null){
                return new DirectoryCopyResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new DirectoryCopyResponse();
            }
            return Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryCopyResponse.class);
        }
    }
    public static final class DirectoryDeleteUnmarshaller
            implements Unmarshaller<DirectoryDeleteResponse, InputStream> {

        public DirectoryDeleteResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if(in == null){
                return new DirectoryDeleteResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new DirectoryDeleteResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryDeleteResponse.class);
        }
    }
    public static final class DirectoryMoveUnmarshaller
            implements Unmarshaller<DirectoryMoveResponse, InputStream> {

        public DirectoryMoveResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if(in == null){
                return new DirectoryMoveResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new DirectoryMoveResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryMoveResponse.class);
        }
    }
    public static final class DirectoryDetailUnmarshaller
            implements Unmarshaller<DirectoryContentsResponse, InputStream> {

        public DirectoryContentsResponse unmarshall(int statusCode, InputStream in) throws Exception {
            if(in == null){
                return new DirectoryContentsResponse();
            }
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new DirectoryContentsResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), DirectoryContentsResponse.class);
        }
    }

    public static final class UploadFileUnmarshaller
            implements Unmarshaller<UploadFileResponse, InputStream> {

        public UploadFileResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), UploadFileResponse.class);
        }
    }


    public static final class ConfirmPutFileUnmarshaller
            implements Unmarshaller<FileConfirmResponse, InputStream> {

        public FileConfirmResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileConfirmResponse.class);
        }
    }

    public static final class GetFileInfoUnmarshaller
            implements Unmarshaller<FileInfoResponse, InputStream> {

        public FileInfoResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileInfoResponse.class);
        }
    }


    public static final class LinkFileUnmarshaller
            implements Unmarshaller<FileLinkToResponse, InputStream> {

        public FileLinkToResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileLinkToResponse.class);
        }
    }
    public static final class CopyFileUnmarshaller
            implements Unmarshaller<FileCopyResponse, InputStream> {

        public FileCopyResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileCopyResponse.class);
        }
    }

    public static final class FileUploadStatusUnmarshaller
            implements Unmarshaller<FileUploadStatusResponse, InputStream> {

        public FileUploadStatusResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);

            FileUploadStatusResponse fileUploadStatusResponse  =
                    Jackson.fromJsonString(responseStrBuilder.toString(), FileUploadStatusResponse.class);
            Map fileUploadStatusMap = Jackson.fromJsonString(responseStrBuilder.toString(), Map.class);
            if (fileUploadStatusMap.get("parts") != null) {
                List parts = (List) fileUploadStatusMap.get("parts");
                fileUploadStatusResponse.setParts(parts);
            }
            return fileUploadStatusResponse;
        }
    }

    public static final class FileUrlInfoUnmarshaller
            implements Unmarshaller<FileUrlInfoResponse, InputStream> {

        public FileUrlInfoResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString()
                    .replace(Constants.ENCODER_0026,Constants.ENCODER_AND), FileUrlInfoResponse.class);
        }
    }

    public static final class FileDeleteUnmarshaller
            implements Unmarshaller<FileDeleteResponse, InputStream> {

        public FileDeleteResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            if (responseStrBuilder.length() == 0) {
                return new FileDeleteResponse();
            }
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileDeleteResponse.class);
        }
    }
    public static final class FileMoveUnmarshaller
            implements Unmarshaller<FileMoveResponse, InputStream> {

        public FileMoveResponse unmarshall(int statusCode, InputStream in) throws Exception {
            StringBuilder responseStrBuilder = getStringBuilder(in);
            return  Jackson.fromJsonString(responseStrBuilder.toString(), FileMoveResponse.class);
        }
    }

}

