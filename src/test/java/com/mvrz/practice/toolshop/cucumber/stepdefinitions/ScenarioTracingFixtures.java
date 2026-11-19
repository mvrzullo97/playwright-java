package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
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
    public void recordTraces(Scenario scenario) {
        String traceName = scenario.getName().replace(" ", "-").toLowerCase();
        BrowserContext context = PlaywrightCucumberFixtures.getBrowserContext();

        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("target/traces/cucumber-traces/trace-" + traceName + ".zip"))
        );
    }
}

