package cfa.vo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.uispec4j.Desktop;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowInterceptor;

/**
 * Created by Omar on 7/3/2015.
 */
public class AppTest extends UISpecTestCase {

    @BeforeClass
    protected void setUp() throws Exception {
        System.setProperty("uispec4j.test.library", "testng");
        setAdapter(new MainClassAdapter(App.class, new String[0]));
    }

    @Test
    public void testBasic() throws Exception {
        Window window = getMainWindow();
        assertEquals("SandBox", window.getTitle());
        Desktop desktop = window.getDesktop();

        Window samphub = WindowInterceptor.run(new Trigger() {
            @Override
            public void run() throws Exception {

            }
        });

        samphub.titleEquals("SAMP Hub");

        window.getMenuBar()
                .getMenu("Tools")
                .getSubMenu("Plugin Manager")
                .getSubMenu("Plugin Manager")
                .click();

        desktop.containsWindow("Plugin Manager");

        Window pmanager = desktop.getWindow("Plugin Manager");

        pmanager.dispose();
    }

}
