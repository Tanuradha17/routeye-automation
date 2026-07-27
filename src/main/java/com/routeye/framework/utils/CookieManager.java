package com.routeye.framework.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Date;
import java.util.Set;

/**
 * Persists a browser session's cookies to disk and restores them later.
 *
 * Intended use: run SessionCapture.java ONCE, log in manually in the opened
 * browser (including solving any Cloudflare check yourself), then every
 * automated test run after that loads the saved cookies instead of hitting
 * the login page fresh - because it's presenting a session a real human
 * already established, not trying to defeat the challenge programmatically.
 *
 * NOTE: cf_clearance and session cookies are time-limited. When they expire,
 * re-run SessionCapture.java to refresh the file.
 */
public class CookieManager {

    private static final String FIELD_SEPARATOR = "\\|\\|\\|";

    public static void saveCookies(WebDriver driver, String filePath) {
        Set<Cookie> cookies = driver.manage().getCookies();
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Cookie c : cookies) {
                String line = String.join("|||",
                        nullToEmpty(c.getName()),
                        nullToEmpty(c.getValue()),
                        nullToEmpty(c.getDomain()),
                        nullToEmpty(c.getPath()),
                        c.getExpiry() == null ? "" : String.valueOf(c.getExpiry().getTime()),
                        String.valueOf(c.isSecure()),
                        String.valueOf(c.isHttpOnly()));
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Saved " + cookies.size() + " cookies to " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save cookies to " + filePath, e);
        }
    }

    /**
     * Loads cookies from disk into the given driver's current domain.
     * The driver MUST already have navigated to the target domain
     * (e.g. driver.get("https://routeye.com")) before calling this -
     * Selenium refuses to set a cookie for a domain you're not on.
     *
     * @return true if at least one non-expired cookie was loaded
     */
    public static boolean loadCookies(WebDriver driver, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        boolean loadedAny = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(FIELD_SEPARATOR, -1);
                if (parts.length < 7) continue;

                String name = parts[0];
                String value = parts[1];
                String domain = parts[2];
                String path = parts[3].isEmpty() ? "/" : parts[3];
                Long expiryMillis = parts[4].isEmpty() ? null : Long.parseLong(parts[4]);

                if (expiryMillis != null && expiryMillis < System.currentTimeMillis()) {
                    continue; // skip expired cookies (e.g. cf_clearance from a stale run)
                }

                Cookie.Builder builder = new Cookie.Builder(name, value)
                        .domain(domain)
                        .path(path)
                        .isSecure(Boolean.parseBoolean(parts[5]))
                        .isHttpOnly(Boolean.parseBoolean(parts[6]));
                if (expiryMillis != null) {
                    builder.expiresOn(new Date(expiryMillis));
                }

                try {
                    driver.manage().addCookie(builder.build());
                    loadedAny = true;
                } catch (Exception e) {
                    // A single bad/mismatched-domain cookie shouldn't abort the whole load
                    System.err.println("Skipped cookie '" + name + "': " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read cookies from " + filePath, e);
        }
        return loadedAny;
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
