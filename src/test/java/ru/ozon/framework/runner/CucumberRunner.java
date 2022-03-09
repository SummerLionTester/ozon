package ru.ozon.framework.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/scenario"},
        glue = {"ru.ozon.framework.steps"},
        plugin = {"ru.ozon.framework.utils.AllureCucumber7JvmScreenshotOnTestFailedDecorator"}
)
public class CucumberRunner {
}












