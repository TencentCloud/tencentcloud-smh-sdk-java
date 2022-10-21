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

package com.tencentcloud.smh.internal.space;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SpacePostBodyRequest implements Serializable {

    /**
     * 布尔型，是否为公有读，不指定默认为 false，当租户空间设置为公有读时，部分读接口无需指定访问令牌，
     * 请参阅具体的 API 说明，该参数可随时修改，可选参数；
     */

    private boolean isPublicRead;
    /**
     * 布尔型，是否为多相簿空间，不指定默认为 false，此时租户空间不允许创建目录或相簿，
     * 该参数仅允许在创建租户空间时指定，后续无法修改，可选参数；
     */
    private boolean isMultiAlbum;
    /**
     * 布尔型，是否允许上传照片，不指定默认为 false，该参数可随时修改，可选参数；
     */
    private boolean allowPhoto;
    /**
     * 布尔型，是否允许上传视频，不指定默认为 false，该参数可随时修改，可选参数
     */
    private boolean allowVideo;
    /**
     * 字符串数组，默认为空数组，当该参数不为空数组时，
     * 仅当上传的文件扩展名存在于该列表中才允许上传且认定为照片，
     * 当该参数为空数组时则根据上传的文件扩展名动态判断是否为照片类型，该参数可随时修改，可选参数；
     */
    private String[] allowPhotoExtname;
    /**
     * 字符串数组，默认为空数组，当该参数不为空数组时，
     * 仅当上传的文件扩展名存在于该列表中才允许上传且认定为视频，
     * 当该参数为空数组时则根据上传的文件扩展名动态判断是否为视频类型，该参数可随时修改，可选参数；
     */
    private String[] allowVideoExtname;

    /**
     * 字符串数组，默认为空数组，当该参数不为空数组时，
     * 仅当上传的文件扩展名存在于该列表中才允许上传且认定为文件，
     * 当该参数为空数组时则根据上传的文件扩展名动态判断是否为文件类型，该参数可随时修改，可选参数；
     */
    private String[] allowFileExtname;
    /**
     * 布尔型，是否检测敏感内容，不指定默认为 false，可选参数；
     */
    private boolean recognizeSensitiveContent;

    /**
     * 字符串，空间标识，用于区分个人空间和团队空间，可选参数；
     */
    private String spaceTag;


    public boolean isPublicRead() {
        return isPublicRead;
    }

    public void setPublicRead(boolean publicRead) {
        isPublicRead = publicRead;
    }

    public boolean isMultiAlbum() {
        return isMultiAlbum;
    }

    public void setMultiAlbum(boolean multiAlbum) {
        isMultiAlbum = multiAlbum;
    }

    public boolean isAllowPhoto() {
        return allowPhoto;
    }

    public void setAllowPhoto(boolean allowPhoto) {
        this.allowPhoto = allowPhoto;
    }

    public boolean isAllowVideo() {
        return allowVideo;
    }

    public void setAllowVideo(boolean allowVideo) {
        this.allowVideo = allowVideo;
    }

    public String[] getAllowPhotoExtname() {
        return allowPhotoExtname;
    }

    public void setAllowPhotoExtname(String[] allowPhotoExtname) {
        this.allowPhotoExtname = allowPhotoExtname;
    }

    public String[] getAllowVideoExtname() {
        return allowVideoExtname;
    }

    public void setAllowVideoExtname(String[] allowVideoExtname) {
        this.allowVideoExtname = allowVideoExtname;
    }

    public String[] getAllowFileExtname() {
        return allowFileExtname;
    }

    public void setAllowFileExtname(String[] allowFileExtname) {
        this.allowFileExtname = allowFileExtname;
    }

    public boolean isRecognizeSensitiveContent() {
        return recognizeSensitiveContent;
    }

    public void setRecognizeSensitiveContent(boolean recognizeSensitiveContent) {
        this.recognizeSensitiveContent = recognizeSensitiveContent;
    }

    public String getSpaceTag() {
        return spaceTag;
    }

    public void setSpaceTag(String spaceTag) {
        this.spaceTag = spaceTag;
    }

    @Override
    public String toString() {
        return "SpacePostBodyRequest{" +
                "isPublicRead=" + isPublicRead +
                ", isMultiAlbum=" + isMultiAlbum +
                ", allowPhoto=" + allowPhoto +
                ", allowVideo=" + allowVideo +
                ", allowPhotoExtname=" + Arrays.toString(allowPhotoExtname) +
                ", allowVideoExtname=" + Arrays.toString(allowVideoExtname) +
                ", allowFileExtname=" + Arrays.toString(allowFileExtname) +
                ", recognizeSensitiveContent=" + recognizeSensitiveContent +
                ", spaceTag='" + spaceTag + '\'' +
                '}';
    }

}
