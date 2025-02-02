/**
 * synopsys-detect
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
package com.synopsys.integration.detect.detector.nuget.inspector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.detect.util.executable.Executable;
import com.synopsys.integration.detect.util.executable.ExecutableOutput;
import com.synopsys.integration.detect.util.executable.ExecutableRunner;
import com.synopsys.integration.detect.util.executable.ExecutableRunnerException;

public class DotNetCoreNugetInspector implements NugetInspector {

    private String dotnetExe;
    private String inspectorDll;
    private ExecutableRunner executableRunner;

    public DotNetCoreNugetInspector(String dotnetExe, String inspectorDll, ExecutableRunner executableRunner) {
        this.dotnetExe = dotnetExe;
        this.inspectorDll = inspectorDll;
        this.executableRunner = executableRunner;
    }

    @Override
    public ExecutableOutput execute(File workingDirectory, List<String> arguments) throws ExecutableRunnerException {
        List<String> dotnetArguments = new ArrayList<String>();
        dotnetArguments.add(inspectorDll);
        dotnetArguments.addAll(arguments);

        final Executable hubNugetInspectorExecutable = new Executable(workingDirectory, dotnetExe, dotnetArguments);
        final ExecutableOutput executableOutput = executableRunner.execute(hubNugetInspectorExecutable);
        return executableOutput;
    }
}