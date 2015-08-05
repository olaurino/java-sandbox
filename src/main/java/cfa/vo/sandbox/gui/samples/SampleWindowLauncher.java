package cfa.vo.sandbox.gui.samples;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import javax.swing.JDesktopPane;

public class SampleWindowLauncher {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SampleWindowLauncher window = new SampleWindowLauncher();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @throws Exception 
     */
    public SampleWindowLauncher() throws Exception {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * @throws Exception 
     */
    private void initialize() throws Exception {
        frame = new JFrame();
        frame.setBounds(100, 100, 1146, 794);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JDesktopPane desktopPane = new JDesktopPane();
        frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
        
        MetadataBrowserView metadataBrowserView = new MetadataBrowserView();
        metadataBrowserView.setBounds(12, 12, 1049, 685);
        metadataBrowserView.setVisible(true);
        desktopPane.add(metadataBrowserView);
    }
}
