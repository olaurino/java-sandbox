/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.sandbox.gui.fitter;

import cfa.vo.iris.AbstractDesktopItem;
import cfa.vo.iris.ICommandLineInterface;
import cfa.vo.iris.IMenuItem;
import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.NullCommandLineInterface;
import cfa.vo.iris.gui.GUIUtils;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.astrogrid.samp.client.MessageHandler;

/**
 *
 * @author jbudynk
 */
public class FittingToolComponent implements IrisComponent {
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
        return "Fitting Tool";
    }

    @Override
    public String getDescription() {
        return "A new Fitting Tool prototype";
    }

    @Override
    public ICommandLineInterface getCli() {
        return new NullCommandLineInterface("fitter");
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
        private FittingToolGUI view;

        public MenuItems() {
            super();
            add(new AbstractDesktopItem("FittingToolPrototype", "Fitting Tool Prototype", "/tool.png", "/tool_tiny.png") {
                @Override
                public void onClick() {
                    if (view == null) {
			try {
			    view = new FittingToolGUI(ws);
			    ws.getDesktop().add(view);
			} catch (Exception ex) {
			    Logger.getLogger(FittingToolComponent.class.getName()).log(Level.SEVERE, null, ex);
			}
                    }
                    GUIUtils.moveToFront(view);
                }
            });
        }
    }
}
