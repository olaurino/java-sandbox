package cfa.vo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.uispec4j.Desktop;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowInterceptor;

import javax.swing.*;

/**
 * Created by Omar on 7/3/2015.
 */
public class AppTest extends UISpecTestCase {
    Window mainWindow;
    Desktop desktop;

    @BeforeClass
    protected void setUpClass() throws Exception {
        System.setProperty("uispec4j.test.library", "testng");
        setAdapter(new MainClassAdapter(App.class, new String[0]));
        mainWindow = getMainWindow();
        desktop = mainWindow.getDesktop();

        // Note:
        //   I haven't dug too much into this - these tests fail intermittently with the
        // error message listed below. It looks related to race conditions between
        // starting the samp server and the spec4j window interceptor, probably fixable 
        // by waiting for a successful startup/shutdown - but definitely something to look
        // more into for setting up Iris GUI tests (I would have a dummy service under the 
        // GUI so we don't have to worry about the actual setup).
      
//        Aug 24, 2015 11:56:54 AM org.astrogrid.samp.httpd.HttpServer start
//        INFO: Server http://127.0.0.1:43889 starting
//        Tests run: 5, Failures: 1, Errors: 0, Skipped: 4, Time elapsed: 12.887 sec <<< FAILURE!
//        setUpClass on instance null(cfa.vo.AppTest)(cfa.vo.AppTest)  Time elapsed: 12.839 sec  <<< FAILURE!
//        java.lang.AssertionError: No window was shown (timeout expired)
//            at org.testng.Assert.fail(Assert.java:89)
//            at org.uispec4j.assertion.testlibrairies.TestNGLibrary.fail(TestNGLibrary.java:7)
//            at org.uispec4j.assertion.testlibrairies.AssertAdapter.fail(AssertAdapter.java:11)
//            at org.uispec4j.interception.handlers.ShownInterceptionDetectionHandler.waitWindow(ShownInterceptionDetectionHandler.java:39)
//            at org.uispec4j.interception.WindowInterceptor.run(WindowInterceptor.java:324)
//            at org.uispec4j.interception.WindowInterceptor.run(WindowInterceptor.java:290)
//            at cfa.vo.AppTest.setUpClass(AppTest.java:54)

        Window samphub = WindowInterceptor.run(new Trigger() {
            @Override
            public void run() throws Exception {
            }
        });

        samphub.titleEquals("SAMP Hub");
    }

    @Test
    public void testBasic() throws Exception {
        assertEquals("SandBox", mainWindow.getTitle());

        mainWindow.getMenuBar()
                .getMenu("Tools")
                .getSubMenu("Plugin Manager")
                .getSubMenu("Plugin Manager")
                .click();

        desktop.containsWindow("Plugin Manager");

        Window pmanager = desktop.getWindow("Plugin Manager");

        pmanager.dispose();
    }

    @Test
    public void testJFreeDemo() throws Exception {

        mainWindow.getMenuBar()
                .getMenu("Tools")
                .getSubMenu("JFreeChartDemo")
                .getSubMenu("JFreeDemo")
                .click();

        desktop.containsWindow("JFreeDemo");

        Window demo = desktop.getWindow("JFreeDemo");
        demo.isVisible();
        JInternalFrame demoFrame = (JInternalFrame) demo.getAwtComponent();
        assertTrue("Frame is not closable", demoFrame.isClosable());
        assertTrue("Frame is not maximizable", demoFrame.isMaximizable());
        assertTrue("Frame is not iconifiable", demoFrame.isIconifiable());
        assertTrue("Frame is not resizable", demoFrame.isResizable());
        assertTrue("Frame's default close operation is not HIDE", demoFrame.getDefaultCloseOperation() == JInternalFrame.HIDE_ON_CLOSE);

        demo.dispose();
    }
    
    @Test
    public void testStiltsDemo() throws Exception {

        mainWindow.getMenuBar()
                .getMenu("Tools")
                .getSubMenu("StiltsDemo")
                .getSubMenu("StiltsDemo")
                .click();

        desktop.containsWindow("Stilts");

        Window demo = desktop.getWindow("StiltsDemo");
        demo.isVisible();
        JInternalFrame demoFrame = (JInternalFrame) demo.getAwtComponent();
        assertTrue("Frame is not closable", demoFrame.isClosable());
        assertTrue("Frame is not maximizable", demoFrame.isMaximizable());
        assertTrue("Frame is not iconifiable", demoFrame.isIconifiable());
        assertTrue("Frame is not resizable", demoFrame.isResizable());
        assertTrue("Frame's default close operation is not HIDE", demoFrame.getDefaultCloseOperation() == JInternalFrame.HIDE_ON_CLOSE);

        demo.dispose();
    }

    @Test
    public void testGroovyConsole() {
        Window groovyConsole = WindowInterceptor.run(
                mainWindow.getMenuBar()
                .getMenu("Tools")
                .getSubMenu("GroovyConsole")
                .getSubMenu("Groovy Console")
                .triggerClick()
        );

        groovyConsole.titleEquals("Groovy Console");

    }

}
