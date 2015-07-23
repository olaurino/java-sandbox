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
