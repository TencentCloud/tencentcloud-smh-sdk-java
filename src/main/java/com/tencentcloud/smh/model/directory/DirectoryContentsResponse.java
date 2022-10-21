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

package com.tencentcloud.smh.model.directory;

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DirectoryContentsResponse extends CommonResponse implements Serializable {
    /**
     * 字符串数组，返回当前请求的目录结构，如果当前请求的是根目录，则该字段为空数组；
     */
    private String[] path;

    /**
     * 整数，当前目录中的文件数（不包含孙子级）；
     */
    private int fileCount;
    /**
     * 整数，当前目录中的子目录数（不包含孙子级）；
     */
    private int subDirCount;
    /**
     * 整数，当前目录中的所有文件和子目录数量（不包含孙子级）；
     */
    private int totalNum;
    /**
     * 字符串，当前目录的 ETag，可用于判断目录下的子目录或文件是否发生变化；
     */
    private String eTag;
    /**
     * 字符串或整数，用于顺序列出分页的标识；
     */
    private String nextMarker;
    /**
     * 对象数组，目录或相簿内的具体内容：
     */
    private List<DirectoryInfoResponse> contents;
    private LocalSyncResponse localSync;
    /**
     * authorityList
     */
    private AuthorityResponse authorityList;


    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getSubDirCount() {
        return subDirCount;
    }

    public void setSubDirCount(int subDirCount) {
        this.subDirCount = subDirCount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public List<DirectoryInfoResponse> getContents() {
        return contents;
    }

    public void setContents(List<DirectoryInfoResponse> contents) {
        this.contents = contents;
    }

    public LocalSyncResponse getLocalSync() {
        return localSync;
    }

    public void setLocalSync(LocalSyncResponse localSync) {
        this.localSync = localSync;
    }

    public AuthorityResponse getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(AuthorityResponse authorityList) {
        this.authorityList = authorityList;
    }

    @Override
    public String toString() {
        return "DirectoryDetailResponse{" +
                "path=" + Arrays.toString(path) +
                ", fileCount=" + fileCount +
                ", subDirCount=" + subDirCount +
                ", totalNum=" + totalNum +
                ", eTag='" + eTag + '\'' +
                ", nextMarker='" + nextMarker + '\'' +
                ", contents=" + contents +
                ", localSync=" + localSync +
                ", authorityList=" + authorityList +
                '}';
    }
}
