package cfa.vo.sandbox.gui;

import cfa.vo.iris.*;
import cfa.vo.iris.gui.GUIUtils;
import org.astrogrid.samp.client.MessageHandler;

import java.util.ArrayList;
import java.util.List;

public class StiltsDemoComponent implements IrisComponent {
    private IrisApplication app;
    private IWorkspace ws;
    private List<IMenuItem> menuItems = new MenuItems();

    @Override
    public void init(IrisApplication irisApplication, IWorkspace iWorkspace) {
        this.app = irisApplication;
        this.ws = iWorkspace;
    }

    @Override
    public String getName() {
        return "StiltsDemo";
    }

    @Override
    public String getDescription() {
        return "A Stilts prototype";
    }

    @Override
    public ICommandLineInterface getCli() {
        return new NullCommandLineInterface("stiltsdemo");
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

    }

    private class MenuItems extends ArrayList<IMenuItem> {
        private PlotterView view;

        public MenuItems() {
            super();
            add(new AbstractDesktopItem("StiltsDemo", "Stilts Demo", "/tool.png", "/tool_tiny.png") {
                @Override
                public void onClick() {
                    if (view == null) {
                        try {
                            view = new PlotterView("StiltsDemo", app, ws);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        ws.getDesktop().add(view);
                    }
                    GUIUtils.moveToFront(view);
                }
            });
        }
    }
}
