package cfa.vo.sandbox.gui.stil;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.ui.ApplicationFrame;

import uk.ac.starlink.table.gui.StarJTable;

public class StiltsPrototype extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    private StarJTable jtable;
    private JScrollPane scrollPane;
    private JPanel topPanel;
    
    public StiltsPrototype(String title, StilPrototype stil) {
        super(title);
        setSize(800, 600);
        setBackground(Color.gray);
        
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        
        jtable = new StarJTable(stil.getTable(), true);
        jtable.configureColumnWidths(30, 10);
        
        scrollPane = new JScrollPane(jtable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
    }
}
