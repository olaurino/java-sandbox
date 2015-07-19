package cfa.vo;

import cfa.vo.iris.*;
import cfa.vo.iris.sed.SedlibSedManager;
import groovy.ui.Console;
import org.astrogrid.samp.client.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaurino on 7/2/15.
 */
public class GroovyConsoleComponent implements IrisComponent {
    private SedlibSedManager manager;
    private MenuItems menuItems = new MenuItems();

    @Override
    public void init(IrisApplication irisApplication, IWorkspace iWorkspace) {
        this.manager = (SedlibSedManager) iWorkspace.getSedManager();
    }

    @Override
    public String getName() {
        return "GroovyConsole";
    }

    @Override
    public String getDescription() {
        return "Integrated Groovy Console";
    }

    @Override
    public ICommandLineInterface getCli() {
        return new NullCommandLineInterface("groovy");
    }

    @Override
    public void initCli(IrisApplication irisApplication) {

    }

    @Override
    public List<IMenuItem> getMenus() {
        return menuItems;
    }

    @Override
    public List<MessageHandler> getSampHandlers() {
        return new ArrayList<MessageHandler>();
    }

    @Override
    public void shutdown() {
        if (menuItems.console != null) {
            menuItems.console.exit();
        }
    }

    private class MenuItems extends ArrayList<IMenuItem> {
        private Console console;

        public MenuItems() {
            super();
            add(new AbstractDesktopItem("Groovy Console",
                    "Groovy Console",
                    "/tool.png",
                    "/tool_tiny.png") {
                @Override
                public void onClick() {
                    if (console == null) {
                        console = new Console();
                        console.setVariable("manager", GroovyConsoleComponent.this.manager);
                        console.run();
                    }

                }
            });
        }
    }
}
