package support;

import java.io.IOException;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import controlutility.LoadDataImpl;

/**
 * JUnit extension (auto-registered via META-INF/services) that populates the
 * {@code ~/.minesweeper} directory once before any test class runs.
 *
 * The score-system tests assume that directory already exists; historically it
 * was created as a side effect of whichever test happened to run first.
 * The Gradle build points {@code user.home} at a throw-away folder, so this
 * never touches the developer's real settings.
 */
public final class TestHomeSetup implements BeforeAllCallback {

    private static boolean initialised;

    @Override
    public void beforeAll(final ExtensionContext context) throws IOException {
        synchronized (TestHomeSetup.class) {
            if (!initialised) {
                new LoadDataImpl().loadData();
                initialised = true;
            }
        }
    }
}
