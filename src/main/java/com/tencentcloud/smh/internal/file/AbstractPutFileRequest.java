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


import com.tencentcloud.smh.internal.SmhServiceRequest;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;


public abstract class AbstractPutFileRequest extends SmhServiceRequest implements Cloneable,
       SmhDataSource, Serializable {

    /**
     * The file containing the data to be uploaded to TencentCloud SMH. You must either specify a file or
     * an InputStream containing the data to be uploaded to TencentCloud SMH.
     */
    private File file;

    /**
     * The InputStream containing the data to be uploaded to TencentCloud SMH. You must either specify a
     * file or an InputStream containing the data to be uploaded to TencentCloud SMH.
     */
    private transient InputStream inputStream;

    /**
     * Optional metadata instructing TencentCloud SMH how to handle the uploaded data (e.g. custom user
     * metadata, hooks for specifying content type, etc.). If you are uploading from an InputStream,
     * you <bold>should always</bold> specify metadata with the content size set, otherwise the
     * contents of the InputStream will have to be buffered in memory before they can be sent to
     * TencentCloud SMH, which can have very negative performance impacts.
     */
    private ObjectMetadata metadata;

    /**
     * Constructs a new {@link AbstractPutFileRequest} object to upload a file to the specified
     * bucket and key. After constructing the request, users may optionally specify object metadata
     * or a canned ACL as well.
     *
     */
    public AbstractPutFileRequest() {
    }



    /**
     * Constructs a new {@link AbstractPutFileRequest} object to upload a stream of data to the
     * specified bucket and key. After constructing the request, users may optionally specify object
     * metadata or a canned ACL as well.
     * <p>
     * Content length for the data stream <b>must</b> be specified in the object metadata parameter;
     * TencentCloud SMH requires it be passed in before the data is uploaded. Failure to specify a content
     * length will cause the entire contents of the input stream to be buffered locally in memory so
     * that the content length can be calculated, which can result in negative performance problems.
     * </p>
     *
     * @param input The stream of data to upload to TencentCloud SMH.
     * @param metadata The object metadata. At minimum this specifies the content length for the
     *        stream of data being uploaded.
     */
    protected AbstractPutFileRequest(InputStream input,
                                     ObjectMetadata metadata) {
        this.inputStream = input;
        this.metadata = metadata;
    }




    /**
     * Gets the path and name of the file containing the data to be uploaded to TencentCloud SMH. Either
     * specify a file or an input stream containing the data to be uploaded to TencentCloud SMH; both
     * cannot be specified.
     *
     * @return The path and name of the file containing the data to be uploaded to TencentCloud SMH.
     *
     * @see AbstractPutFileRequest#setFile(File)
     * @see AbstractPutFileRequest#withFile(File)
     * @see AbstractPutFileRequest#setInputStream(InputStream)
     * @see AbstractPutFileRequest#withInputStream(InputStream)
     */
    @Override
    public File getFile() {
        return file;
    }

    /**
     * Sets the path and name of the file containing the data to be uploaded to TencentCloud SMH. Either
     * specify a file or an input stream containing the data to be uploaded to TencentCloud SMH; both
     * cannot be specified.
     *
     * @param file The path and name of the file containing the data to be uploaded to TencentCloud SMH.
     *
     * @see AbstractPutFileRequest#getFile()
     * @see AbstractPutFileRequest#withFile(File)
     * @see AbstractPutFileRequest#getInputStream()
     * @see AbstractPutFileRequest#withInputStream(InputStream)
     */
    @Override
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Sets the file containing the data to be uploaded to TencentCloud SMH. Returns this
     * {@link AbstractPutFileRequest}, enabling additional method calls to be chained together.
     * <p>
     * Either specify a file or an input stream containing the data to be uploaded to TencentCloud SMH;
     * both cannot be specified.
     *</p>
     * @param file The file containing the data to be uploaded to TencentCloud SMH.
     *
     * @return This {@link AbstractPutFileRequest}, enabling additional method calls to be chained
     *         together.
     *
     * @see AbstractPutFileRequest#getFile()
     * @see AbstractPutFileRequest#setFile(File)
     * @see AbstractPutFileRequest#getInputStream()
     * @see AbstractPutFileRequest#setInputStream(InputStream)
     *
     */
    public <T extends AbstractPutFileRequest> T withFile(File file) {
        setFile(file);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    /**
     * Gets the optional metadata instructing TencentCloud SMH how to handle the uploaded data (e.g.
     * custom user metadata, hooks for specifying content type, etc.).
     * <p>
     * If uploading from an input stream, <b>always</b> specify metadata with the content size set.
     * Otherwise the contents of the input stream have to be buffered in memory before being sent to
     * TencentCloud SMH. This can cause very negative performance impacts.
     * </p>
     *
     * @return The optional metadata instructing TencentCloud SMH how to handle the uploaded data (e.g.
     *         custom user metadata, hooks for specifying content type, etc.).
     *
     * @see AbstractPutFileRequest#setMetadata(ObjectMetadata)
     * @see AbstractPutFileRequest#withMetadata(ObjectMetadata)
     */
    public ObjectMetadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the optional metadata instructing TencentCloud SMH how to handle the uploaded data (e.g.
     * custom user metadata, hooks for specifying content type, etc.).
     * <p>
     * If uploading from an input stream, <b>always</b> specify metadata with the content size set.
     * Otherwise the contents of the input stream have to be buffered in memory before being sent to
     * TencentCloud SMH. This can cause very negative performance impacts.
     * </p>
     *
     * @param metadata The optional metadata instructing TencentCloud SMH how to handle the uploaded data
     *        (e.g. custom user metadata, hooks for specifying content type, etc.).
     *
     * @see AbstractPutFileRequest#getMetadata()
     * @see AbstractPutFileRequest#withMetadata(ObjectMetadata)
     */
    public void setMetadata(ObjectMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Sets the optional metadata instructing TencentCloud SMH how to handle the uploaded data (e.g.
     * custom user metadata, hooks for specifying content type, etc.). Returns this
     * {@link AbstractPutFileRequest}, enabling additional method calls to be chained together.
     * <p>
     * If uploading from an input stream, <b>always</b> specify metadata with the content size set.
     * Otherwise the contents of the input stream have to be buffered in memory before being sent to
     * TencentCloud SMH. This can cause very negative performance impacts.
     * </p>
     *
     * @param metadata The optional metadata instructing TencentCloud SMH how to handle the uploaded data
     *        (e.g. custom user metadata, hooks for specifying content type, etc.).
     *
     * @return This {@link AbstractPutFileRequest}, enabling additional method calls to be chained
     *         together.
     *
     * @see AbstractPutFileRequest#getMetadata()
     * @see AbstractPutFileRequest#setMetadata(ObjectMetadata)
     */
    public <T extends AbstractPutFileRequest> T withMetadata(ObjectMetadata metadata) {
        setMetadata(metadata);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }


    /**
     * Gets the input stream containing the data to be uploaded to TencentCloud SMH. The user of this
     * request must either specify a file or an input stream containing the data to be uploaded to
     * TencentCloud SMH; both cannot be specified.
     *
     * @return The input stream containing the data to be uploaded to TencentCloud SMH. Either specify a
     *         file or an input stream containing the data to be uploaded to TencentCloud SMH, not both.
     *
     * @see AbstractPutFileRequest#setInputStream(InputStream)
     * @see AbstractPutFileRequest#withInputStream(InputStream)
     * @see AbstractPutFileRequest#setFile(File)
     * @see AbstractPutFileRequest#withFile(File)
     */
    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Sets the input stream containing the data to be uploaded to TencentCloud SMH. Either specify a file
     * or an input stream containing the data to be uploaded to TencentCloud SMH; both cannot be
     * specified.
     *
     * @param inputStream The input stream containing the data to be uploaded to TencentCloud SMH. Either
     *        specify a file or an input stream containing the data to be uploaded to TencentCloud SMH,
     *        not both.
     *
     * @see AbstractPutFileRequest#getInputStream()
     * @see AbstractPutFileRequest#withInputStream(InputStream)
     * @see AbstractPutFileRequest#getFile()
     * @see AbstractPutFileRequest#withFile(File)
     */
    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Sets the input stream containing the data to be uploaded to TencentCloud SMH. Returns this
     * {@link AbstractPutFileRequest}, enabling additional method calls to be chained together.
     * <p>
     * Either specify a file or an input stream containing the data to be uploaded to TencentCloud SMH;
     * both cannot be specified.
     * </p>
     *
     * @param inputStream The InputStream containing the data to be uploaded to TencentCloud SMH.
     *
     * @return This PutObjectRequest, so that additional method calls can be chained together.
     *
     * @see AbstractPutFileRequest#getInputStream()
     * @see AbstractPutFileRequest#setInputStream(InputStream)
     * @see AbstractPutFileRequest#getFile()
     * @see AbstractPutFileRequest#setFile(File)
     */
    public <T extends AbstractPutFileRequest> T withInputStream(InputStream inputStream) {
        setInputStream(inputStream);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }


    protected final <T extends AbstractPutFileRequest> T copyPutObjectBaseTo(T target) {
        copyBaseTo(target);
        final ObjectMetadata metadata = getMetadata();
        return target
                .withInputStream(getInputStream())
                .withMetadata(metadata == null ? null : metadata.clone());
    }

}
