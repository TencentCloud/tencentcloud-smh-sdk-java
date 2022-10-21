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



package com.tencentcloud.smh.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tencentcloud.smh.exception.SmhServiceException;
import com.tencentcloud.smh.utils.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SmhErrorResponseHandler implements HttpResponseHandler<SmhServiceException> {

    private static final Logger log = LoggerFactory.getLogger(SmhErrorResponseHandler.class);

    @Override
    public SmhServiceException handle(SmhHttpResponse httpResponse) throws Exception {
        SmhServiceException smhServiceException = new SmhServiceException("");
        smhServiceException.setStatusCode(httpResponse.getStatusCode());
        try {
            final InputStream is = httpResponse.getContent();
            if (is == null || httpResponse.getRequest().getHttpMethod() == HttpMethodName.HEAD) {
                return smhServiceException;
            }
            String content = null;
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((content = streamReader.readLine()) != null)
                responseStrBuilder.append(content);
            boolean json = Jackson.isJson(responseStrBuilder.toString());
            if (json) {
                smhServiceException =  Jackson.fromJsonString(responseStrBuilder.toString(), SmhServiceException.class);
                return smhServiceException;
            }
            // maybe xml
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(responseStrBuilder.toString().getBytes(StandardCharsets.UTF_8));
            if (jsonNode != null) {
                if (jsonNode.get("Code") != null) {
                    smhServiceException.setCode(jsonNode.get("Code").toString());
                }
                if (jsonNode.get("Message") != null) {
                    smhServiceException.setMessage(jsonNode.get("Message").asText());
                }
                if (jsonNode.get("RequestId") != null) {
                    smhServiceException.setRequestId(jsonNode.get("RequestId").asText());
                }
            }
            return smhServiceException;
        }catch (Exception e){
            smhServiceException.setMessage(e.getMessage());
            return smhServiceException;
        }
    }



    @Override
    public boolean needsConnectionLeftOpen() {
        return false;
    }

}
