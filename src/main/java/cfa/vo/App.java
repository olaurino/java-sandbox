package cfa.vo;

import cfa.vo.iris.AbstractIrisApplication;
import cfa.vo.iris.IrisComponent;
import cfa.vo.sandbox.gui.JFreeDemoComponent;
import cfa.vo.sandbox.gui.StiltsDemoComponent;
import cfa.vo.sandbox.gui.fitter.FittingToolComponent;

import javax.swing.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        components.add(new JFreeDemoComponent());
        components.add(new StiltsDemoComponent());
	components.add(new FittingToolComponent());
        Logger.getLogger("").setLevel(Level.ALL);
    }

    @Override
    public URL getHelpURL() {
        return null;
    }
}
