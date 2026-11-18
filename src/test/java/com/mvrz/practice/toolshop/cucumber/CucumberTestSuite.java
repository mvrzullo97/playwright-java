package com.mvrz.practice.toolshop.cucumber;


import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
//per il momento non serve
@ConfigurationParameter(
        key = "cucumber.plugin",
        //value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        value = "pretty," +
                "html:target/cucumber-reports/cucumber.html"
)
public class CucumberTestSuite {

}
