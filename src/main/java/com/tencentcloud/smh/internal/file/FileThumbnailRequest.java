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

import java.io.File;

public class FileThumbnailRequest extends FileRequest {

    /**
     * 缩放大小，可选参数，相关说明参阅接口说明；
     */
    private String size;
    /**
     * 缩放宽度，不传高度时，高度按等比例缩放，不传 Size 和 Scale 时生效；
     */
    private String widthSize;
    /**
     * 等比例缩放百分比，可选参数，不传 Size 时生效；
     */
    private String scale;

    /**
     * 缩放高度，不传宽度时，宽度按等比例缩放，不传 Size 和 Scale 时生效；
     */
    private String heightSize;
    /**
     * 帧数，针对 gif 的降帧处理；
     */
    private String frameNumber;
    /**
     *  用途，可选参数，列表页传 list、预览页传 preview、用于下载传 download，默认 null；
     */
    private String purpose;

    /**
     * 要写入的文件路径
     */
    private File localFile;


    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWidthSize() {
        return widthSize;
    }

    public void setWidthSize(String widthSize) {
        this.widthSize = widthSize;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getHeightSize() {
        return heightSize;
    }

    public void setHeightSize(String heightSize) {
        this.heightSize = heightSize;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
