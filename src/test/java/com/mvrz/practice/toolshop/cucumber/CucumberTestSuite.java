package com.mvrz.practice.toolshop.cucumber;


import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/catalog")
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty," +
                "html:target/cucumber-reports/cucumber.html"
)
public class CucumberTestSuite {}
