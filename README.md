# Routeye Selenium Automation Framework

Java + Selenium 4 + TestNG framework for testing https://routeye.com, built around
the Page Object Model (POM).

## Stack
- **Java 17**, **Maven**
- **Selenium 4.21** – browser automation
- **TestNG 7.10** – test runner, assertions, parallel execution
- **WebDriverManager** – auto-downloads the right chromedriver/geckodriver, no manual PATH setup
- **ExtentReports** – HTML report with pass/fail status and failure screenshots
- **Log4j2** – logging to console + `test-output/automation.log`

## Project structure
```
routeye-automation/
├── pom.xml
├── testng.xml                     # suite definition, listeners, parallel config
├── src/main/java/com/routeye/framework/
│   ├── config/ConfigReader.java   # reads config.properties (+ -D overrides)
│   ├── core/DriverFactory.java    # creates/quits WebDriver (thread-safe)
│   ├── pages/BasePage.java        # common actions all page objects share
│   ├── pages/HomePage.java        # sample page object for routeye.com
│   ├── utils/WaitUtils.java       # centralized explicit waits
│   ├── utils/ScreenshotUtil.java  # screenshot capture on failure
│   └── listeners/TestListener.java# ExtentReports + screenshot-on-fail wiring
└── src/test/java/com/routeye/tests/
    ├── BaseTest.java              # @BeforeMethod/@AfterMethod driver lifecycle
    ├── HomePageTest.java          # sample test class
    └── TripCreationTest.java      # end-to-end: login -> nav -> 4-step trip wizard
```

## Trip creation flow (converted from a single script)

The full "create a trip" flow — login, navigate to Trips, and walk through the
4-step wizard (Trip & locations → Vehicle assignment → Alerts → Payments) — is
now modeled as page objects instead of one long script:

```
pages/LoginPage.java              # login form
pages/NavigationBar.java          # top nav (TRIPS link)
pages/trip/TripListPage.java      # Trips list -> "Create Trip"
pages/trip/TripCreationPage.java  # the 4-step wizard, one method per step
models/TripRequest.java           # all trip test data in one object
tests/TripCreationTest.java       # orchestrates the steps, reads data from config
```

`BasePage` also gained two helpers pulled out of the repeated patterns in the
original script:
- `selectFromSearchableDropdown(...)` — the "click combobox → type search → click match" pattern used for geofences and devices
- `typeWithoutClear(...)` — for fields where clearing first would break the intended input (e.g. comma-separated numbers)

Run just this test:
```bash
mvn test -Dtest=TripCreationTest
```

All the test data (trip name, geofence names, device search term, phone
numbers, overspeed limit, receipt number) and login credentials live in
`config.properties` — override any of them from the command line, e.g.:
```bash
mvn test -Dtest=TripCreationTest -Dtest.username=harshprod -Dtest.password=Harshprod@123
```

**One thing carried over as-is, worth revisiting:** the secondary contact
number field in `TripCreationPage` still uses the original script's
positional locator (`//div[6]//div[2]//input[1]`) because no stable
attribute was available. It's marked with a `TODO` in the code — ask a dev
for an `id`/`name`/`data-testid` on that field and swap it in; positional
XPath like this breaks the moment the page layout changes.

## Handling Cloudflare's bot check via saved session cookies

routeye.com sits behind Cloudflare's bot-challenge page ("Verify you are
human"), which a fresh automated browser session will always be shown. The
supported way to handle this without fighting Cloudflare on every run:

1. **Run `SessionCapture.java` once** (`src/test/java/com/routeye/tools/` →
   right-click → **Run As → Java Application**, not TestNG).
2. A Chrome window opens on the login page. **Log in yourself, by hand**,
   including solving the Cloudflare check if it appears.
3. Once you see the dashboard, go back to the console and press **Enter**.
4. Your session cookies are saved to `src/test/resources/session-cookies.dat`
   (excluded from git via `.gitignore` — it's live session data).

From then on, `TripCreationTest` calls
`loginUsingSavedSessionOrCredentials(...)`, which:
- loads the saved cookies and checks whether they're still valid
- if valid → skips the login page entirely, no challenge encountered
- if expired/missing → falls back to a normal credential login (which will
  hit the challenge page again if Cloudflare isn't otherwise allowlisted)

**This only works because a human solved the check once** — the framework
reuses that already-established session, it doesn't solve or bypass
anything itself. Cloudflare's clearance cookie is time-limited (commonly a
few hours), so re-run `SessionCapture.java` whenever the saved session
expires. For a permanent fix instead of periodic re-capture, ask whoever
manages the Cloudflare dashboard for routeye.com to allowlist your test
machine's IP or a custom header — that's the standard setup real QA/CI
pipelines use for apps behind Cloudflare.

## Design principles this framework follows
1. **Page Object Model** – one class per page/component, tests never call `driver.findElement` directly.
2. **No hardcoded waits** – `WaitUtils` wraps `WebDriverWait`; no `Thread.sleep()` anywhere.
3. **Thread-safe driver** – `DriverFactory` uses `ThreadLocal<WebDriver>`, so `parallel="methods"` in testng.xml is safe.
4. **Config over hardcoding** – browser, URLs, timeouts live in `config.properties`, overridable from the command line for CI.
5. **Automatic evidence on failure** – any failing test gets a screenshot attached to the HTML report automatically, no extra code needed per test.

## Setup
1. Install **JDK 17+** and **Maven 3.8+**.
2. Install Chrome (or Firefox/Edge) — WebDriverManager handles the driver binaries automatically, you don't need to download chromedriver manually.
3. Clone/copy this project, then from the project root:
   ```bash
   mvn clean install -DskipTests
   ```

## Running tests
```bash
# run the full suite (uses testng.xml)
mvn test

# override browser / headless mode from the command line (handy for CI)
mvn test -Dbrowser=chrome -Dheadless=true

# run a single test class
mvn test -Dtest=HomePageTest
```

Report: open `test-output/ExtentReport.html` after the run.
Logs: `test-output/automation.log`.
Screenshots (failures only): `test-output/screenshots/`.

## First thing to fix before this actually passes
`HomePage.java` locators are **placeholders** — I wasn't able to load routeye.com directly to
inspect the real DOM (bot protection blocked the fetch). Open the site, inspect the real
elements (logo, nav, login button, cookie banner), and swap in the real `By` locators. Everything
else — driver setup, waits, reporting, config — works out of the box.

## How to extend this framework
- **New page** → create `SomethingPage extends BasePage` in `pages/`, add locators + methods that return meaningful state (not raw WebElements) to tests.
- **New test** → create a class `extends BaseTest`, use `@Test` methods, get `driver` for free.
- **New environment** (staging/prod) → add another `base.url` value and switch via `-Dbase.url=...`, or duplicate `config.properties` per env and select via `-Dconfig.env=staging` (extend `ConfigReader` to support this if you need more than one env file).
- **API + UI hybrid tests** → add a `com.routeye.framework.api` package with a REST client (e.g. RestAssured) alongside the UI layer.
- **CI** → run `mvn test -Dheadless=true` in GitHub Actions/Jenkins; publish `test-output/ExtentReport.html` as a build artifact.

## Suggested next test cases for routeye.com
Once locators are filled in, natural candidates for the suite:
- Navigation: every header/footer link resolves (no 404s)
- Forms: contact/signup form validation (required fields, invalid email, success state)
- Auth flow: login with valid/invalid credentials, logout
- Responsive smoke check: key elements visible at mobile viewport size
