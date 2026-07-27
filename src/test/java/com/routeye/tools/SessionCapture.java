package com.routeye.tools;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.core.DriverFactory;
import com.routeye.framework.utils.CookieManager;
import org.openqa.selenium.WebDriver;

import java.util.Scanner;

/**
 * NOT a TestNG test - a one-off manual tool.
 *
 * Run this as a plain Java application (Run As -> Java Application), then:
 *   1. A Chrome window opens on the routeye.com login page.
 *   2. YOU log in by hand and solve the Cloudflare "Verify you are human"
 *      check yourself, same as any normal visitor.
 *   3. Once you're past login and see the dashboard, come back to this
 *      console and press ENTER.
 *   4. Your session cookies get saved to src/test/resources/session-cookies.dat
 *
 * From then on, TestNG tests that use SessionAwareLogin will reuse this saved
 * session instead of hitting the login page fresh - because it's presenting
 * a session a real human already established, not re-solving anything itself.
 *
 * Re-run this whenever the saved session expires (Cloudflare's clearance
 * cookie and/or the app's own session cookie are time-limited).
 */
public class SessionCapture {

    private static final String COOKIE_FILE = "src/test/resources/session-cookies.dat";

    public static void main(String[] args) {
        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();

        try {
        	driver.get(ConfigReader.get("base.url") + "/auth/login");

            System.out.println("=================================================");
            System.out.println(" Log in manually in the Chrome window that just opened.");
            System.out.println(" Solve the Cloudflare check yourself if it appears.");
            System.out.println(" Once you see the dashboard, come back here and press ENTER.");
            System.out.println("=================================================");

            new Scanner(System.in).nextLine();

            CookieManager.saveCookies(driver, COOKIE_FILE);
            System.out.println("Session captured. Tests can now reuse it via SessionAwareLogin.");
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
