package cfa.vo.sandbox.gui.jfree;

import org.jfree.ui.RefineryUtilities;

import cfa.vo.sandbox.gui.stil.StilPrototype;
import cfa.vo.sandbox.gui.stil.StiltsPrototype;

public class Prototype {


    /**
     * Kickoff the demo app.
     * @throws Exception 
     * 
     */
    public static void main(final String[] args) throws Exception {
        final StilPrototype stil = new StilPrototype("/export/eholum/notes/SEDSample");
        
        final StiltsPrototype jstilts = new StiltsPrototype("Prototype", stil);
        jstilts.pack();
        RefineryUtilities.centerFrameOnScreen(jstilts);
        jstilts.setVisible(true);

        final JFreePrototype jfree = new JFreePrototype("Prototype", stil);
        jfree.pack();
        RefineryUtilities.centerFrameOnScreen(jfree);
        jfree.setVisible(true);
    }
}
