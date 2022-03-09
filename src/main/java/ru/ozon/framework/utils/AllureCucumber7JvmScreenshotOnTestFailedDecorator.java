package ru.ozon.framework.utils;


import io.cucumber.plugin.event.EventPublisher;
import io.qameta.allure.Allure;
import io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.ozon.framework.managers.DriverManager;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.Status;

import java.util.Date;

public class AllureCucumber7JvmScreenshotOnTestFailedDecorator extends AllureCucumber7Jvm {
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinished -> {
            if(testStepFinished.getResult().getStatus().is(Status.FAILED)) {
                Allure.getLifecycle().addAttachment(
                        "Screnshot" + new Date(),
                        "image/png",
                        "png",
                        ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES));
            }
        });
        super.setEventPublisher(publisher);
    }

}
