package cfa.vo.sandbox.gui.samples;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.ListSelectionModel;

import cfa.vo.sandbox.gui.stil.StilPrototype;
import com.jidesoft.swing.JideScrollPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;

public class MetadataBrowserView extends JInternalFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private StilPrototype stil;
    private JTable metadataTable;
    private JTable segmentTable;
    
    public Object[][] getDataValues(int rows) {
        Object[][] values = new Object[rows][stil.getTable().getColumnCount()];
        
        for (int i=0; i<rows; ++i) {
            try {
                values[i] = stil.getTable().getRow(i);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
             
        return values;      
    };
    
    public String[] getColumnNames() {  
        int c = stil.getTable().getColumnCount();
        String[] columns = new String[c];
        for (int i=0; i<c; ++i) {
            columns[i] = stil.getTable().getColumnInfo(i).getName();
        }
        return columns;
    }
       

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MetadataBrowserView frame = new MetadataBrowserView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws Exception 
     */
    public MetadataBrowserView() throws Exception {
        setToolTipText("View and browse metadata for existing SEDs in the plotting window");
        setTitle("Metadata Browser");
        setSelected(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        
        try {
            stil = new StilPrototype("resources/data/SEDSample1");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        getContentPane().setForeground(Color.WHITE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setBackground(Color.LIGHT_GRAY);
        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            }
        });
        setBounds(100, 100, 1013, 599);
        
        textField = new JTextField();
        textField.setColumns(10);
        
        JButton btnFilter = new JButton("Select Points");
        btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        
        JButton button = new JButton("Apply Mask");
        
        JScrollPane dataScrollPane = new JScrollPane();
        dataScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        dataScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        dataScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        metadataTable = new JTable(getDataValues(100), getColumnNames());
        dataScrollPane.setViewportView(metadataTable);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        segmentTable = new JTable(
                new Object[][] {
                    {"Segment 1"},
                    {"Segment 2"},
                    {"Subset 1"},
                    {"SED 2"}
                }, new String[] {"Object Names"});
        
        scrollPane.setViewportView(segmentTable);
        
        JButton button_1 = new JButton("Select All");
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(12)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                            .addGap(7)
                            .addComponent(dataScrollPane, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(button_1)
                            .addGap(117)
                            .addComponent(textField, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                            .addGap(6)
                            .addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                            .addGap(6)
                            .addComponent(button, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
                    .addGap(12))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(12)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                        .addComponent(dataScrollPane, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                    .addGap(11)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(2)
                            .addComponent(button_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(button, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addGap(10))
        );
        getContentPane().setLayout(groupLayout);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu extractMenu = new JMenu("Extract\n");
        menuBar.add(extractMenu);
        
        JMenuItem mntmExtraeextractlkj = new JMenuItem("Extract to new SED");
        extractMenu.add(mntmExtraeextractlkj);
        
        JMenuItem mntmSendToSamp = new JMenuItem("Broadcast to SAMP");
        extractMenu.add(mntmSendToSamp);
        
        JMenuItem mntmCreate = new JMenuItem("Create Subset");
        extractMenu.add(mntmCreate);
        
        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);
        
        JMenuItem menuItem_1 = new JMenuItem("Create New Colum");
        mnEdit.add(menuItem_1);
        
        JMenuItem menuItem_2 = new JMenuItem("Restore Set");
        mnEdit.add(menuItem_2);
        
        JMenu mnSelect = new JMenu("Select");
        menuBar.add(mnSelect);
        
        JMenuItem menuItem = new JMenuItem("Invert Selection");
        mnSelect.add(menuItem);
        
        JMenuItem mntmApplyMask = new JMenuItem("Apply Mask");
        mnSelect.add(mntmApplyMask);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
        mnSelect.add(mntmNewMenuItem);

    }
}
