
package cfa.vo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.uispec4j.Desktop;
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

        Window console = WindowInterceptor.run(
                window.getMenuBar()
                        .getMenu("Tools")
                        .getSubMenu("GroovyConsole")
                        .getSubMenu("Groovy Console")
                        .triggerClick()
        );

        assertEquals("GroovyConsole", console.getTitle());

        console.dispose();
    }

}
