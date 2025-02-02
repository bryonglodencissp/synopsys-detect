/**
 * detect-configuration
 *
 * Copyright (C) 2019 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.detect.help.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.detect.help.DetectOption;

public class HelpHtmlDataBuilder {

    private final Map<String, HelpHtmlGroup> groupsByName = new HashMap<>();

    public HelpHtmlDataBuilder addDetectOption(final DetectOption option) {
        final String groupName = StringUtils.capitalize(option.getDetectOptionHelp().primaryGroup);
        if (!groupsByName.containsKey(groupName)) {
            final HelpHtmlGroup group = new HelpHtmlGroup();
            group.groupName = groupName;
            group.options = new ArrayList<>();
            groupsByName.put(groupName, group);
        }

        final HelpHtmlGroup group = groupsByName.get(groupName);

        final HelpHtmlOption htmlOption = option.createHtmlOption();
        group.options.add(htmlOption);
        return this;
    }

    public HelpHtmlData build() {
        final List<HelpHtmlGroup> sortedOptions = groupsByName.values().stream().sorted((o1, o2) -> o1.groupName.compareTo(o2.groupName)).collect(Collectors.toList());
        sortedOptions.forEach(it -> it.getOptions().sort((o1, o2) -> o1.key.compareTo(o2.key)));
        return new HelpHtmlData(sortedOptions);
    }
}
