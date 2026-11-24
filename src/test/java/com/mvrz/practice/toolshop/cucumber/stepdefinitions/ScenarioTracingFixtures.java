package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScenarioTracingFixtures {
    @Before
    public void setupTracing() {
        BrowserContext context = PlaywrightCucumberFixtures.getBrowserContext();
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
    }

    @After
    public void recordTraces(Scenario scenario) throws IOException {
        String traceName = scenario.getName()
                .replace(" ", "-")
                .toLowerCase();
        BrowserContext context = PlaywrightCucumberFixtures.getBrowserContext();
        String featureName = scenario.getUri().toString()
                .substring(scenario.getUri().toString().lastIndexOf('/') + 1)
                .replace(".feature", "");
        Path traceDirectory = Paths.get("target/traces/cucumber-traces/" + featureName);
        Files.createDirectories(traceDirectory);
        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(traceDirectory.resolve("trace-" + traceName + ".zip"))
        );
    }



/*
    @After
    public void recordTraces(Scenario scenario) throws IOException {
        String traceName = scenario.getName().replace(" ", "-").toLowerCase();
        BrowserContext context = PlaywrightCucumberFixtures.getBrowserContext();

        String featureName = Paths.get(scenario.getUri())
                .getFileName().toString().replace(".feature", "");

        Path traceDirectory = Paths.get(
                "target/traces/cucumber-traces/" + featureName
        );
        Files.createDirectories(traceDirectory);

        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(traceDirectory.resolve("trace-" + traceName + ".zip"))
        );
    }

 */
}

