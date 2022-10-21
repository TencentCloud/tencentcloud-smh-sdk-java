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


package com.tencentcloud.smh.event;

/**
 * Abstract adapter class for a progress listener that is delivered with
 * progress event synchronously. 
 */
public abstract class SyncProgressListener
    implements ProgressListener, DeliveryMode {
    /**
     * Always returns true.
     */
    @Override public boolean isSyncCallSafe() { return true; }
}
