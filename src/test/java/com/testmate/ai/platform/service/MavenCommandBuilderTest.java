package com.testmate.ai.platform.service;

import com.testmate.ai.platform.model.TestRunRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MavenCommandBuilderTest {

    private final MavenCommandBuilder commandBuilder = new MavenCommandBuilder();

    @Test
    public void webRemoteExecutionPassesPlaywrightModeToMaven() {
        TestRunRequest request = new TestRunRequest();
        request.setSuite("web");
        request.setTags("@parabank");
        request.setExecutionMode("remote");

        List<String> command = commandBuilder.build(request);

        Assert.assertTrue(command.contains("-Pweb"));
        Assert.assertTrue(command.contains("-Dcucumber.filter.tags=@parabank"));
        Assert.assertTrue(command.contains("-Dexecution.mode=remote"));
        Assert.assertTrue(command.contains("-Dweb.execution.mode=remote"));
    }

    @Test
    public void cdpExecutionModeIsAllowedForWebRuns() {
        TestRunRequest request = new TestRunRequest();
        request.setSuite("web");
        request.setTags("@parabank");
        request.setExecutionMode("cdp");

        List<String> command = commandBuilder.build(request);

        Assert.assertTrue(command.contains("-Dweb.execution.mode=cdp"));
    }

    @Test
    public void webMockExecutionModeFallsBackToLocalPlaywrightDefaults() {
        TestRunRequest request = new TestRunRequest();
        request.setSuite("web");
        request.setTags("@parabank");
        request.setExecutionMode("mock");

        List<String> command = commandBuilder.build(request);

        Assert.assertTrue(command.contains("-Dexecution.mode=mock"));
        Assert.assertFalse(command.contains("-Dweb.execution.mode=mock"));
    }
}
