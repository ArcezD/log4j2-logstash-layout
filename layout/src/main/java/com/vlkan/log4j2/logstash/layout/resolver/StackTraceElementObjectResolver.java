/*
 * Copyright 2017-2020 Volkan Yazıcı
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permits and
 * limitations under the License.
 */

package com.vlkan.log4j2.logstash.layout.resolver;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

class StackTraceElementObjectResolver implements TemplateResolver<StackTraceElement> {

    private static final TemplateResolver<StackTraceElement> CLASS_NAME_RESOLVER =
            (stackTraceElement, jsonGenerator) -> jsonGenerator.writeString(stackTraceElement.getClassName());

    private static final TemplateResolver<StackTraceElement> METHOD_NAME_RESOLVER =
            (stackTraceElement, jsonGenerator) -> jsonGenerator.writeString(stackTraceElement.getMethodName());

    private static final TemplateResolver<StackTraceElement> FILE_NAME_RESOLVER =
            (stackTraceElement, jsonGenerator) -> jsonGenerator.writeString(stackTraceElement.getFileName());

    private static final TemplateResolver<StackTraceElement> LINE_NUMBER_RESOLVER =
            (stackTraceElement, jsonGenerator) -> jsonGenerator.writeNumber(stackTraceElement.getLineNumber());

    private final TemplateResolver<StackTraceElement> internalResolver;

    StackTraceElementObjectResolver(String key) {
        this.internalResolver = createInternalResolver(key);
    }

    private TemplateResolver<StackTraceElement> createInternalResolver(String key) {
        switch (key) {
            case "className": return CLASS_NAME_RESOLVER;
            case "methodName": return METHOD_NAME_RESOLVER;
            case "fileName": return FILE_NAME_RESOLVER;
            case "lineNumber": return LINE_NUMBER_RESOLVER;
        }
        throw new IllegalArgumentException("unknown key: " + key);
    }

    static String getName() {
        return "stackTraceElement";
    }

    @Override
    public void resolve(StackTraceElement stackTraceElement, JsonGenerator jsonGenerator) throws IOException {
        internalResolver.resolve(stackTraceElement, jsonGenerator);
    }

}
