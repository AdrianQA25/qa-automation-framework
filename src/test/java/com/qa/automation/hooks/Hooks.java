package com.qa.automation.hooks;

import com.qa.automation.config.ConfigurationManager;
import com.qa.automation.config.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        DriverManager.initBrowser();
        DriverManager.getPage().navigate(ConfigurationManager.getBaseUrl());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!ConfigurationManager.isTakeScreenshots()) return;

        try {
            byte[] screenshot = DriverManager.getPage().screenshot();
            Allure.addAttachment(
                    "Paso - " + scenario.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );
        } catch (Exception e) {
            logger.warn("No se pudo adjuntar screenshot del paso", e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && ConfigurationManager.isTakeScreenshots()) {
            try {
                byte[] screenshot = DriverManager.getPage().screenshot();
                Allure.addAttachment(
                        "Fallo - " + scenario.getName(),
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );
            } catch (Exception e) {
                logger.warn("No se pudo adjuntar screenshot del fallo", e);
            }
        }

        DriverManager.clearBrowserData();
        DriverManager.closeBrowser();
    }
}