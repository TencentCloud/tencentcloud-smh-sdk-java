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

import com.tencentcloud.smh.model.CommonResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents the object metadata that is stored with TencentCloud SMH. This includes custom user-supplied
 * metadata, as well as the standard HTTP headers that TencentCloud SMH sends and receives
 * (Content-Length, ETag, Content-MD5, etc.).
 */
public class ObjectMetadata extends CommonResponse implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Custom user metadata, represented in responses with the x-smh-meta- header prefix
     */
    private Map<String, String> userMetadata;



    /**
     * <p>
     * Gets the custom user-metadata for the associated object.
     * </p>
     * <p>
     * TencentCloud SMH can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-SMH-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in TencentCloud SMH. When setting user metadata, callers <i>should not</i> include the
     * internal "x-smh-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-smh-meta-" header prefix.
     * </p>
     * <p>
     * User-metadata keys are <b>case insensitive</b> and will be returned as lowercase strings,
     * even if they were originally specified with uppercase strings.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @return The custom user metadata for the associated object.
     *
     * @see ObjectMetadata#setUserMetadata(Map)
     * @see ObjectMetadata#addUserMetadata(String, String)
     */
    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    /**
     * <p>
     * Sets the custom user-metadata for the associated object.
     * </p>
     * <p>
     * TencentCloud SMH can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-smh-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in TencentCloud SMH. When setting user metadata, callers <i>should not</i> include the
     * internal "x-smh-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-smh-meta-" header prefix.
     * </p>
     * <p>
     * User-metadata keys are <b>case insensitive</b> and will be returned as lowercase strings,
     * even if they were originally specified with uppercase strings.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @param userMetadata The custom user-metadata for the associated object. Note that the key
     *        should not include the internal SMH HTTP header prefix.
     * @see ObjectMetadata#getUserMetadata()
     * @see ObjectMetadata#addUserMetadata(String, String)
     */
    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }


    /**
     * <p>
     * Adds the key value pair of custom user-metadata for the associated object. If the entry in
     * the custom user-metadata map already contains the specified key, it will be replaced with
     * these new contents.
     * </p>
     * <p>
     * TencentCloud SMH can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-smh-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in TencentCloud SMH. When setting user metadata, callers <i>should not</i> include the
     * internal "x-smh-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-smh-meta-" header prefix.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @param key The key for the custom user metadata entry. Note that the key should not include
     *        the internal SMH HTTP header prefix.
     * @param value The value for the custom user-metadata entry.
     *
     * @see ObjectMetadata#setUserMetadata(Map)
     * @see ObjectMetadata#getUserMetadata()
     */
    public void addUserMetadata(String key, String value) {
        this.userMetadata.put(key, value);
    }




    /**
     * Returns the value of the specified user meta datum.
     */
    public String getUserMetaDataOf(String key) {
        return userMetadata == null ? null : userMetadata.get(key);
    }



    public ObjectMetadata() {
        userMetadata = new HashMap<String, String>();
    }

    private ObjectMetadata(ObjectMetadata from) {
        // shallow clone the internal hash maps
        userMetadata =
                from.userMetadata == null ? null : new HashMap<String, String>(from.userMetadata);
    }

    public ObjectMetadata clone() {
        return new ObjectMetadata(this);
    }

}
