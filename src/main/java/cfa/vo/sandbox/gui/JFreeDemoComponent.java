package cfa.vo.sandbox.gui;

import cfa.vo.iris.*;
import cfa.vo.iris.gui.GUIUtils;
import org.astrogrid.samp.client.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 7/19/2015.
 */
public class JFreeDemoComponent implements IrisComponent {
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
        return "JFreeChartDemo";
    }

    @Override
    public String getDescription() {
        return "A JFreeChart prototype";
    }

    @Override
    public ICommandLineInterface getCli() {
        return new NullCommandLineInterface("jfreedemo");
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
        private JFreeDemoView view;

        public MenuItems() {
            super();
            add(new AbstractDesktopItem("JFreeDemo", "JFreeChart Demo", "/tool.png", "/tool_tiny.png") {
                @Override
                public void onClick() {
                    if (view == null) {
                        view = new JFreeDemoView("JFreeDemo");
                        ws.getDesktop().add(view);
                    }
                    GUIUtils.moveToFront(view);
                }
            });
        }
    }
}
