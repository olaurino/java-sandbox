/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.sandbox.gui.fitter;

import cfa.vo.interop.SAMPFactory;
import cfa.vo.iris.events.SedCommand;
import cfa.vo.iris.events.SedEvent;
import cfa.vo.iris.events.SedListener;
import cfa.vo.iris.gui.GUIUtils;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.sherpa.CompositeModel;
import cfa.vo.sherpa.CompositeModelTreeModel;
import cfa.vo.sherpa.Model;
import cfa.vo.sherpa.Parameter;
import cfa.vo.sherpa.UserModel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import spv.controller.SpectrumContainer;

/**
 *
 * @author jbudynk
 */
public class BuildModelPanel extends javax.swing.JPanel implements SedListener {

    /**
     * Creates new form BuildModelPanel
     */
    public BuildModelPanel() {
	initComponents();
    }

    private final String[] paramValues = new String[]{"Val", "Min", "Max", "Frozen"};
    private final String[] modelValues = new String[]{"Color", "Show","Frozen"};

    public BuildModelPanel(ExtSed sed) {
        initComponents();
        setSed(sed);
        SedEvent.getInstance().add(this);
        jTree1.setPreferredSize(null);
        jTree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TreePath selPath = jTree1.getPathForLocation(e.getX(), e.getY());
                if (selPath != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                    if (node.isLeaf()) {
                        Parameter par = (Parameter) node.getUserObject();
                        setSelectedParameter(par);
                    } else {
			UserModel model = (UserModel) node.getUserObject();
			setSelectedModel(model);
		    }
                }

            }
        });
    }

    private java.util.List<UserModel> userModels;

    private void setSed(ExtSed sed) {
        try {
	    CompositeModel m = (CompositeModel) sed.getAttachment("fit.model");
            userModels = smm.getUserModels();
            setModel(m);
            setExpression(m.getName());
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            setModel((CompositeModel) SAMPFactory.get(CompositeModel.class));
            setExpression("No Model");
            setSelectedParameter(null);
        }
    }

    private String getValue(Parameter par, String name) {
        try {
            Method m = Parameter.class.getMethod("get" + name);
            String typeStr = "%s";
            Object value = m.invoke(par);
            if (name.equals("Frozen")) {
                Integer v = (Integer) value;
                value = v == 0 ? false : true;
            }
            return String.format(typeStr, value);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            return "";
        }
    }
    
    private Parameter selectedParameter;
    public static final String PROP_SELECTEDPARAMETER = "selectedParameter";

    /**
     * Get the value of selectedParameter
     *
     * @return the value of selectedParameter
     */
    public Parameter getSelectedParameter() {
        return selectedParameter;
    }

    /**
     * Set the value of selectedParameter
     *
     * @param selectedParameter new value of selectedParameter
     */
    public void setSelectedParameter(final Parameter selectedParameter) {
        Parameter oldSelectedParameter = this.selectedParameter;
        this.selectedParameter = selectedParameter;
        firePropertyChange(PROP_SELECTEDPARAMETER, oldSelectedParameter, selectedParameter);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JPanel panel;

                if (selectedParameter != null) {

                    panel = new JPanel(new SpringLayout());


                    for (String name : paramValues) {
                        JLabel l = new JLabel(name, JLabel.TRAILING);
                        panel.add(l);
                        JTextField textField = new JTextField();
                        l.setLabelFor(textField);
                        textField.setEditable(true);
                        textField.setText(getValue(selectedParameter, name));
                        panel.add(textField);
                    }


                    GUIUtils.makeCompactGrid(panel, paramValues.length, 2, 6, 6, 6, 6);

                } else {
                    panel = new JPanel(new GridLayout());
                    panel.add(new JLabel("No Parameter Selected."));
                }

                jSplitPane1.setBottomComponent(panel);
            }

        });
    }

    public static final String PROP_SELECTEDMODEL = "selectedModel";
    private UserModel selectedModel;
    public UserModel getSelectedModel() {
	return selectedModel;
    }
    
    public void setSelectedModel(UserModel model) {
	UserModel oldSelectedModel = this.selectedModel;
        this.selectedModel = selectedModel;
        firePropertyChange(PROP_SELECTEDMODEL, oldSelectedModel, selectedModel);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JPanel panel;

                if (selectedModel != null) {

                    panel = new JPanel(new SpringLayout());

                    for (String name : modelValues) {
			
                        JLabel l = new JLabel(name, JLabel.TRAILING);
                        panel.add(l);
			if (name.equals("Colors")) {
			    JColorChooser color = new JColorChooser();
			    color.setSize(15, 10);
			    color.setColor(255, 0, 0);
			}
                        JCheckBox checkBox = new JCheckBox();
                        l.setLabelFor(checkBox);
                        checkBox.setSelected(false);
                        panel.add(checkBox);
                    }

                    GUIUtils.makeCompactGrid(panel, modelValues.length, 2, 6, 6, 6, 6);

                } else {
                    panel = new JPanel(new GridLayout());
                    panel.add(new JLabel("No Parameter Selected."));
                }

                jSplitPane1.setBottomComponent(panel);
            }

        });
    }


    private CompositeModel model;
    public static final String PROP_MODEL = "model";

    /**
     * Get the value of model
     *
     * @return the value of model
     */
    public CompositeModel getModel() {
        return model;
    }

    /**
     * Set the value of model
     *
     * @param model new value of model
     */
    public void setModel(CompositeModel model) {
        CompositeModel oldModel = this.model;
        this.model = model;
        firePropertyChange(PROP_MODEL, oldModel, model);
        setTreeModel(new CompositeModelTreeModel(model, userModels));
    }
    
    private CompositeModelTreeModel treeModel;
    public static final String PROP_TREEMODEL = "treeModel";

    /**
     * Get the value of treeModel
     *
     * @return the value of treeModel
     */
    public CompositeModelTreeModel getTreeModel() {
        return treeModel;
    }

    /**
     * Set the value of treeModel
     *
     * @param treeModel new value of treeModel
     */
    public void setTreeModel(CompositeModelTreeModel treeModel) {
        CompositeModelTreeModel oldTreeModel = this.treeModel;
        this.treeModel = treeModel;
        firePropertyChange(PROP_TREEMODEL, oldTreeModel, treeModel);
    }


    private String expression;
    public static final String PROP_EXPRESSION = "expression";

    /**
     * Get the value of expression
     *
     * @return the value of expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Set the value of expression
     *
     * @param expression new value of expression
     */
    public void setExpression(String expression) {
        String oldExpression = this.expression;
        this.expression = expression;
        firePropertyChange(PROP_EXPRESSION, oldExpression, expression);
    }

    @Override
    public void process(ExtSed source, SedCommand payload) {
        setSed(source);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        modelExpressionLabel = new javax.swing.JLabel();
        modelExpressionTextField = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        modelComponentsScrollPane = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        modelPropertiesPanel = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Build Model"));

        modelExpressionLabel.setText("Model Expression:");

        jSplitPane1.setDividerLocation(150);

        modelComponentsScrollPane.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(modelComponentsScrollPane);

        javax.swing.GroupLayout modelPropertiesPanelLayout = new javax.swing.GroupLayout(modelPropertiesPanel);
        modelPropertiesPanel.setLayout(modelPropertiesPanelLayout);
        modelPropertiesPanelLayout.setHorizontalGroup(
            modelPropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        modelPropertiesPanelLayout.setVerticalGroup(
            modelPropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(modelPropertiesPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(modelExpressionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modelExpressionTextField)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelExpressionLabel)
                    .addComponent(modelExpressionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JScrollPane modelComponentsScrollPane;
    private javax.swing.JLabel modelExpressionLabel;
    private javax.swing.JTextField modelExpressionTextField;
    private javax.swing.JPanel modelPropertiesPanel;
    // End of variables declaration//GEN-END:variables
}
