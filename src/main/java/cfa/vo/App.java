package cfa.vo;

import cfa.vo.iris.AbstractIrisApplication;
import cfa.vo.iris.IrisComponent;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App extends AbstractIrisApplication
{
    private List<IrisComponent> components = new ArrayList();

    public static void main( String[] args )
    {
        launch(App.class, args);
    }

    @Override
    public String getName() {
        return "SandBox";
    }

    @Override
    public String getDescription() {
        return "SandBox description";
    }

    @Override
    public URL getSAMPIcon() {
        return getClass().getResource("/iris_button_tiny.png");
    }

    @Override
    public List<IrisComponent> getComponents() throws Exception {
        return components;
    }

    @Override
    public JDialog getAboutBox() {
        return null;
    }

    @Override
    public URL getDesktopIcon() {
        return getClass().getResource("/Iris_logo.png");
    }

    @Override
    public void setProperties(List<String> strings) {
        components.add(new GroovyConsoleComponent());
    }

    @Override
    public URL getHelpURL() {
        return null;
    }
}
