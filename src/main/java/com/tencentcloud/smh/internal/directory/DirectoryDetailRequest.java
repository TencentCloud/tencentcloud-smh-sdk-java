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

package com.tencentcloud.smh.internal.directory;

public class DirectoryDetailRequest extends DirectoryRequest {
    /**
     * 用于顺序列出分页的标识，可选参数，不能与 page 和 page_size 参数同时使用；
     */
    private String marker;
    /**
     * 用于顺序列出分页时本地列出的项目数限制，可选参数，不能与 page 和 page_size 参数同时使用；
     */
    private String limit;
    /**
     * 分页码，默认第一页，可选参数，不能与 marker 和 limit 参数同时使用；
     */
    private String page;
    /**
     * 分页大小，默认 20，可选参数，不能与 marker 和 limit 参数同时使用；
     */
    private String pageSize;
    /**
     * 排序字段，按名称排序为 name，按修改时间排序为 modificationTime，按文件大小排序为 size，按创建时间排序为 creationTime；
     */
    private String orderBy;
    /**
     * 排序方式，升序为 asc，降序为 desc；
     */
    private String orderByType;
    /**
     * 筛选方式，可选，不传返回全部，onlyDir 只返回文件夹，onlyFile 只返回文件；
     */
    private String directoryFilter;
    /**
     *  排序方式，可选，不传则文件和文件夹单独排序，先返回文件夹，后返回文件。union 文件和文件夹拉通排序；
     */
    private String sortType;


    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

    public String getDirectoryFilter() {
        return directoryFilter;
    }

    public void setDirectoryFilter(String directoryFilter) {
        this.directoryFilter = directoryFilter;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }


}
