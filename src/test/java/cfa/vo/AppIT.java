
package cfa.vo;

import org.junit.Test;
import org.uispec4j.*;
import org.uispec4j.interception.WindowInterceptor;
import org.uispec4j.utils.MainClassTrigger;

import javax.swing.*;

/**
 * Created by Omar on 7/3/2015.
 */
public class AppIT extends UISpecTestCase {
    private static Window mainWindow;
    private Desktop desktop;
    private static Trigger trigger;
    private static Window samphub;


    public void setUp() throws Exception {
        super.setUp();
        setAdapter(new AppAdapter(App.class, new String[0]));
        mainWindow = getMainWindow();
        desktop = mainWindow.getDesktop();
        samphub.titleEquals("SAMP Hub");
    }

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

    private static class AppAdapter implements UISpecAdapter {

        public AppAdapter(Class mainClass, String... args) {
            if(trigger == null)
                trigger = new MainClassTrigger(mainClass, args);
        }

        public Window getMainWindow() {
            if (mainWindow == null) {
                mainWindow = WindowInterceptor.run(trigger);
                samphub = WindowInterceptor.run(new Trigger() {
                    @Override
                    public void run() throws Exception {

                    }
                });
            }
            return mainWindow;
        }

    }

}
