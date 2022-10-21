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

import java.io.Serializable;

public class AuthorityResponse implements Serializable {

    /**
     * 可查看，非必返；
     */
    private boolean canView;
    /**
     * 可预览，非必返；
     */
    private boolean canPreview;
    /**
     * 可下载，非必返；
     */
    private boolean canDownload;

    private boolean canPrint;
    /**
     * 可上传，非必返；
     */
    private boolean canUpload;
    /**
     * 可删除，非必返；
     */
    private boolean canDelete;
    /**
     * 可修改，非必返；
     */
    private boolean canModify;
    /**
     * 可共享，非必返；
     */
    private boolean canAuthorize;
    /**
     * 可分享，非必返；
     */
    private boolean canShare;
    /**
     * 自己可预览，非必返；
     */
    private boolean canPreviewSelf;
    /**
     * 自己可下载，非必返；
     */
    private boolean canDownloadSelf;


    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public boolean isCanPreview() {
        return canPreview;
    }

    public void setCanPreview(boolean canPreview) {
        this.canPreview = canPreview;
    }

    public boolean isCanDownload() {
        return canDownload;
    }

    public void setCanDownload(boolean canDownload) {
        this.canDownload = canDownload;
    }

    public boolean isCanPrint() {
        return canPrint;
    }

    public void setCanPrint(boolean canPrint) {
        this.canPrint = canPrint;
    }

    public boolean isCanUpload() {
        return canUpload;
    }

    public void setCanUpload(boolean canUpload) {
        this.canUpload = canUpload;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean isCanAuthorize() {
        return canAuthorize;
    }

    public void setCanAuthorize(boolean canAuthorize) {
        this.canAuthorize = canAuthorize;
    }

    public boolean isCanShare() {
        return canShare;
    }

    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }

    public boolean isCanPreviewSelf() {
        return canPreviewSelf;
    }

    public void setCanPreviewSelf(boolean canPreviewSelf) {
        this.canPreviewSelf = canPreviewSelf;
    }

    public boolean isCanDownloadSelf() {
        return canDownloadSelf;
    }

    public void setCanDownloadSelf(boolean canDownloadSelf) {
        this.canDownloadSelf = canDownloadSelf;
    }

    @Override
    public String toString() {
        return "AuthorityResponse{" +
                "canView=" + canView +
                ", canPreview=" + canPreview +
                ", canDownload=" + canDownload +
                ", canPrint=" + canPrint +
                ", canUpload=" + canUpload +
                ", canDelete=" + canDelete +
                ", canModify=" + canModify +
                ", canAuthorize=" + canAuthorize +
                ", canShare=" + canShare +
                ", canPreviewSelf=" + canPreviewSelf +
                ", canDownloadSelf=" + canDownloadSelf +
                '}';
    }

}
