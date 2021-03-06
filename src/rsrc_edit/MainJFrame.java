/*

Copyright (C) 2013-2016, Securifera, Inc 

All rights reserved. 

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
	this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
	this list of conditions and the following disclaimer in the documentation 
	and/or other materials provided with the distribution.

    * Neither the name of Securifera, Inc nor the names of its contributors may be 
	used to endorse or promote products derived from this software without specific
	prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER 
OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

================================================================================

The copyright on this package is held by Securifera, Inc

*/
package rsrc_edit;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import rsrc_edit.codec.Codec;
import rsrc_edit.codec.Xor;
import rsrc_edit.pe.DosHeader;
import rsrc_edit.pe.ImageDataDirectory;
import rsrc_edit.pe.ImageSectionHeader;
import rsrc_edit.pe.NtHeader;
import rsrc_edit.pe.PeHeadersJDialog;
import rsrc_edit.resource.ResourceDataEntry;
import rsrc_edit.resource.ResourceDirectoryTable;
import rsrc_edit.settings.Settings;


/**
 *
 * @author user
 */
public class MainJFrame extends javax.swing.JFrame implements TreeSelectionListener, JDialogListener {

    private JFileChooser theFileChooser = null;
    private final ResourceJTree mainJTree;
    
    public static final int IMAGE_DIRECTORY_ENTRY_RESOURCE = 2;
    public static Map<String, String> theResourceMap = new HashMap<>();
   
    public static final String RESOURCE_IMG_STR = "rsrc.png";    
    private File userSelectedFile;
    
    //Savable settings
    private Settings theSettings = null;
    
    //Current binary headers
    private DosHeader theDosHeader = null;
    private NtHeader theNtHeader = null;
    
     
    
      
    /**
     * Creates new form ConfigJFrame
     */
    public MainJFrame() {
        initComponents();
        mainJTree = ( ResourceJTree )genJTree;
        initializeComponents();
        
        //Set the icon
        setAppIconImage(RESOURCE_IMG_STR);
        
        //Center
        setLocationRelativeTo(null);
    }
    
     //===============================================================
    /**
     *  Initialize the panel components
     */
    private void initializeComponents() {
        
        theSettings = instantiateSettings();
        
        theFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        theFileChooser.setMultiSelectionEnabled( false ); 
        
        ResourceJTreeModel theModel = new ResourceJTreeModel( new DefaultMutableTreeNode( new String("Root"), true ) );
        mainJTree.setRootVisible(false);
        mainJTree.setModel( theModel );
        mainJTree.setShowsRootHandles(true);
        mainJTree.setRowHeight(20);
                 
        //Set the resource map
        theResourceMap.put("9", "Accelerator Table");
        theResourceMap.put("21", "Animated Cursor");
        theResourceMap.put("22", "Animated Icon");
        theResourceMap.put("2", "Bitmap Resource");
        theResourceMap.put("1", "Cursor Resource");
        theResourceMap.put("5", "Dialog Box");
        theResourceMap.put("17", "RC File Name");
        theResourceMap.put("8", "Font Resource");
        theResourceMap.put("7", "Font Dir Resource");
        theResourceMap.put("23", "HTML Resource");
        theResourceMap.put("3", "Icon Resource");
        theResourceMap.put("24", "SxS Manifest");
        theResourceMap.put("4", "Menu Resource");
        theResourceMap.put("11", "Message-table Entry");
        theResourceMap.put("19", "Plug&Play Resource");
        theResourceMap.put("10", "Raw Data");
        theResourceMap.put("6", "String Table");
        theResourceMap.put("16", "Version Resource");
        theResourceMap.put("20", "VXD Resource");
               
        xorKeyValue.setVisible(false);
        xorKeyLabel.setVisible(false);
        
        Settings appSettings = getSettings();
        String encoding = appSettings.getDefaultStringEncoding();
        Codec defaultCodec = null;
        List<Codec> codecList = Codec.getCodecs();
        for( Codec acodec : codecList ){
            encodingComboBox.addItem(acodec);
            if( acodec.toString().equals(encoding))
                defaultCodec = acodec;
            
        }
        
        //Get encoding
        encodingComboBox.setSelectedItem(defaultCodec);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        typeLabelVal = new javax.swing.JLabel();
        sizeLabelVal = new javax.swing.JLabel();
        encodingLabel = new javax.swing.JLabel();
        encodingComboBox = new javax.swing.JComboBox<>();
        xorKeyLabel = new javax.swing.JLabel();
        xorKeyValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dataScrollPane = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        genJTree = new ResourceJTree();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        settingsMenu = new javax.swing.JMenu();
		peheadersItem = new javax.swing.JMenuItem();
        loadSettings = new javax.swing.JMenuItem();
        saveSettings = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Resources");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Size:");

        typeLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        typeLabel.setText("Type:");

        typeLabelVal.setBackground(new java.awt.Color(255, 255, 255));
        typeLabelVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typeLabelVal.setOpaque(true);

        sizeLabelVal.setBackground(new java.awt.Color(255, 255, 255));
        sizeLabelVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sizeLabelVal.setOpaque(true);

        encodingLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        encodingLabel.setText("Encoding:");

        encodingComboBox.setDoubleBuffered(true);
        encodingComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                encodingComboBoxItemStateChanged(evt);
            }
        });

        xorKeyLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        xorKeyLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        xorKeyLabel.setText("Key (Hex):");

        xorKeyValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                xorKeyValueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(encodingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeLabelVal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xorKeyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xorKeyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeLabelVal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sizeLabelVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeLabelVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(encodingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xorKeyValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xorKeyLabel))
                .addContainerGap())
        );

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Data");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        genJTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(genJTree);

        fileMenu.setText("File");

        loadMenuItem.setText("Load Binary");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        peheadersItem.setText("PE Headers");
        peheadersItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peheadersItemActionPerformed(evt);
            }
        });
        settingsMenu.add(peheadersItem);

        settingsMenu.setText("Settings");

        loadSettings.setText("Load");
        loadSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSettingsActionPerformed(evt);
            }
        });
        settingsMenu.add(loadSettings);

        saveSettings.setText("Save");
        saveSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSettingsActionPerformed(evt);
            }
        });
        settingsMenu.add(saveSettings);

        mainMenuBar.add(settingsMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dataScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encodingComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_encodingComboBoxItemStateChanged

        if(evt.getStateChange() == ItemEvent.SELECTED){

            Codec theCodec = (Codec) encodingComboBox.getSelectedItem();            
            if(theCodec.toString().equals(Codec.XOR)){
                xorKeyLabel.setVisible(true);
                xorKeyValue.setVisible(true);
                //Resize appropriately
                pack();
                
            } else {
                xorKeyLabel.setVisible(false);
                xorKeyValue.setVisible(false);
                //Resize appropriately
                pack();
            }
            
            //Returns the last path element of the selection.
            //Note: This method is useful only when the selection model allows only a single selection.
            DefaultMutableTreeNode theSelectedNode = (DefaultMutableTreeNode)mainJTree.getLastSelectedPathComponent();

            if ( theSelectedNode != null ) {

                TreePath[] theSelNodes = mainJTree.getSelectionPaths();
                if(theSelNodes != null && theSelNodes.length == 1){                
                    ResourceController aResourceController = (ResourceController)theSelectedNode.getUserObject();
                    JPanel thePanel = aResourceController.getRootPanel();
                    if( thePanel instanceof Codecable ){
                        Codecable anInst = (Codecable)thePanel;
                        anInst.encodingChanged(theCodec);                        
                    }

                }

            }            

        }
    }//GEN-LAST:event_encodingComboBoxItemStateChanged

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        loadBinary();
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void loadSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSettingsActionPerformed
//        SettingsJDialog settingsDialog = new SettingsJDialog( this, true);
//        settingsDialog.setVisible(true); // This blocks...(evt);
        loadSettings();
    }//GEN-LAST:event_loadSettingsActionPerformed

    private void xorKeyValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xorKeyValueKeyReleased
        Codec selectedCodec = getSelectedCodec();
        if( selectedCodec instanceof Xor ){
            Xor xorCodec = (Xor)selectedCodec;
            String curText = xorKeyValue.getText();
            //Set the key
            xorCodec.setKey(curText);
        }
    }//GEN-LAST:event_xorKeyValueKeyReleased

    private void peheadersItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peheadersItemActionPerformed
        PeHeadersJDialog headerDialog = new PeHeadersJDialog( this, true);
        headerDialog.setVisible(true); // This blocks...(evt);
    }//GEN-LAST:event_peheadersItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed

        AboutJDialog aboutDialog = new AboutJDialog( null );
        aboutDialog.setVisible( true ); 
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void saveSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSettingsActionPerformed
        saveSettings();
    }//GEN-LAST:event_saveSettingsActionPerformed

	
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                           
        System.exit(0);
    }   
                                          
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
        
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
      
        final MainJFrame theFrame = new MainJFrame();
        theFrame.setDefaultLookAndFeelDecorated(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                theFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JScrollPane dataScrollPane;
    private javax.swing.JComboBox<Codec> encodingComboBox;
    private javax.swing.JLabel encodingLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTree genJTree;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuItem loadSettings;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenuItem saveSettings;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem peheadersItem;
    private javax.swing.JLabel sizeLabelVal;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JLabel typeLabelVal;
    private javax.swing.JLabel xorKeyLabel;
    private javax.swing.JTextField xorKeyValue;
    // End of variables declaration//GEN-END:variables
	
     //======================================================================
    /**
    *   Populates the tree model
    */
    private boolean populateTreeModel( List<ResourceDataEntry> resourceList ) {

        setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        try {
            
            mainJTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
            mainJTree.setScrollsOnExpand( true );

            // Only add a TreeListener if one has not already been added
            if( mainJTree.getTreeSelectionListeners().length == 0) {
                mainJTree.addTreeSelectionListener( this );
            }
             //Clear the model
            ResourceJTreeModel theModel = new ResourceJTreeModel(new DefaultMutableTreeNode( new String("Root"), true ));
            mainJTree.setModel(theModel);
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) theModel.getRoot();
            
            for( ResourceDataEntry aRDE : resourceList ){
                
                //Get an entry
                ResourceController aController = new ResourceController( this, aRDE );
                int index = parentNode.getChildCount();
                mainJTree.addObjectToTree( aController, parentNode, index );
                
            }

            mainJTree.setRootVisible( false );
            mainJTree.getModel().reload();
            mainJTree.setSelectionRow(0);

            //Pack the componenents
            pack();

        } finally {
            setCursor( null );
        }

        return true;

    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    public File getLoadedFile(){
        return userSelectedFile;
    }
    
      //Load binary from disk
    private void loadSettings() {
        
        //JFileChooser settingsFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        theFileChooser.setMultiSelectionEnabled( false ); 
        
        File loadFile = null;
        int returnVal = theFileChooser.showDialog( this, "Select Settings File" ); //Show the dialog
        switch( returnVal ) {

          case JFileChooser.CANCEL_OPTION: //If the user canceled the selecting...
          case JFileChooser.ERROR_OPTION: //If the dialog was dismissed or an error occurred...
            break; //Do nothing

          case JFileChooser.APPROVE_OPTION: //If the user approved the selection...
            loadFile = theFileChooser.getSelectedFile(); //Get the files the user selected
            break;
          default:
            break;

        }

        //Open file and read it
        if(loadFile != null){            
            InputStream file = null;
            try{
                //use buffering
                if( loadFile.exists() ){
                    file = new FileInputStream(loadFile);
                    InputStream buffer = new BufferedInputStream(file);
                    ObjectInput input = new ObjectInputStream (buffer);
                    try{
                        //deserialize the List
                        theSettings = (Settings)input.readObject();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } finally{
                        input.close();
                    }
                    
                    //Refresh panel
                    final TreePath aPath = mainJTree.getSelectionPath();
                    if( aPath != null )
                    SwingUtilities.invokeLater(new Runnable(){
                        @Override
                        public void run() {
                            mainJTree.setSelectionPath(null);
                            mainJTree.setSelectionPath(aPath);
                        }
                    
                    });
                    
                }

            } catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {

                    if( file != null )
                        file.close();

                } catch (IOException ex) {
                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Load binary from disk
    private void loadBinary() {
        
        int returnVal = theFileChooser.showDialog( this, "Select File" ); //Show the dialog
        switch( returnVal ) {

          case JFileChooser.CANCEL_OPTION: //If the user canceled the selecting...
          case JFileChooser.ERROR_OPTION: //If the dialog was dismissed or an error occurred...
            break; //Do nothing

          case JFileChooser.APPROVE_OPTION: //If the user approved the selection...
            userSelectedFile = theFileChooser.getSelectedFile(); //Get the files the user selected
            break;
          default:
            break;

        }

        //Open file and read it
        if(userSelectedFile != null){
            
            FileInputStream aFIS = null;
            FileChannel currentBinaryFileChannel = null;
            try {
                
                //Open file
                aFIS = new FileInputStream(userSelectedFile);
                currentBinaryFileChannel = aFIS.getChannel();
                                
                //Allocate space for dos header
                ByteBuffer buf = ByteBuffer.allocate(DosHeader.DOS_HEADER_SIZE);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf);
                buf.flip();
                
                //Read the DOS header
                theDosHeader = new DosHeader(buf);
                currentBinaryFileChannel.position(theDosHeader.e_lfanew);
              
                //Read the NT header
                buf = ByteBuffer.allocate(0x2000);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf);
                buf.flip();
                
                //Get the NT header
                theNtHeader = new NtHeader(buf, theDosHeader.e_lfanew);
                
                //Get Sections
                HashMap<String, ImageSectionHeader> sectionHeaderMap = new HashMap<>();
                for( int i =0; i < theNtHeader.FileHeader.NumberOfSections; i++ ){
                    ImageSectionHeader aSectionHeader = new ImageSectionHeader(buf);
                    sectionHeaderMap.put( aSectionHeader.Name, aSectionHeader);                                        
                }
                
                ImageDataDirectory relocDataDir = theNtHeader.OptionalHeader.DataDirectory[IMAGE_DIRECTORY_ENTRY_RESOURCE];
                long resourceVitualAddr = relocDataDir.VirtualAddress;
                
                //Get the resource section
                ImageSectionHeader resourceSectionHeader = sectionHeaderMap.get(".rsrc");
                long size = resourceSectionHeader.SizeOfRawData;
                long rawResourceAddr = resourceSectionHeader.PointerToRawData;
                 
                //Get the resource data
                buf = ByteBuffer.allocate((int) size);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf, rawResourceAddr);
                buf.flip();
                         
                //Parse resource table
                ResourceDirectoryTable mainTable = new ResourceDirectoryTable(buf, rawResourceAddr - resourceVitualAddr, 0 );
                List<ResourceDataEntry> resourceList = mainTable.getResources();
                
                 //Go get data
                for( ResourceDataEntry aRDS : resourceList ) {
                    
                    if( aRDS.data == null ){
                        try {

                            ByteBuffer aBB = ByteBuffer.allocate((int) aRDS.Size);
                            currentBinaryFileChannel.position(aRDS.DataVirtualAddr);
                            currentBinaryFileChannel.read(aBB);
                            aBB.flip();

                            aRDS.data = new byte[(int)aRDS.Size];
                            aBB.get(aRDS.data);

                        } catch (IOException ex) {
                            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                //Add to the tree map
                populateTreeModel(resourceList);
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if( currentBinaryFileChannel != null )
                    try { 
                        currentBinaryFileChannel.close(); 
                    } catch (IOException ex) { }
            }
        
        }
    }

    //==========================================================================
    /**
     * 
     * @param evt 
     */
    @Override
    public void valueChanged(TreeSelectionEvent evt) {
        
        //Returns the last path element of the selection.
        //Note: This method is useful only when the selection model allows only a single selection.
        DefaultMutableTreeNode theSelectedNode = (DefaultMutableTreeNode)mainJTree.getLastSelectedPathComponent();

        if ( theSelectedNode != null ) {

            TreePath[] theSelNodes = mainJTree.getSelectionPaths();

            if(theSelNodes != null && theSelNodes.length == 1){
                
                setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
                
                ResourceController selectedObject = (ResourceController)theSelectedNode.getUserObject();
                ResourceDataEntry aRDS = selectedObject.getObject();
                sizeLabelVal.setText( String.valueOf( aRDS.Size) );                
                typeLabelVal.setText( aRDS.getType());
                
                //Set the panel
                JPanel objPanel = selectedObject.getRootPanel();
                if( objPanel != null )
                    if( objPanel instanceof StringResourceJPanel){
                        ((StringResourceJPanel)objPanel).refresh();
                    }
                    dataScrollPane.setViewportView(objPanel);

                setCursor( null );
            }
        }
    
    }
    
    //======================================================================
    /**
    * Sets the icon to be used for the application (on the top left of the windows
    * and on the taskbar).
    *
    * @param imageName the name of the image to be used as the application icon
    */
    private void setAppIconImage( String imageName ) {
        Image appIcon = Utilities.loadImageFromJar( imageName );
        if( appIcon != null ) {
            setIconImage( appIcon );
        }
    }    

    //======================================================================
    /**
     * 
     * @return 
     */
    @Override
    public MainJFrame getParentJFrame() {
        return this;
    }

    //======================================================================
    /**
     * 
     * @return 
     */
    public Settings getSettings() {
        return theSettings;
    }
    
    //======================================================================
    /**
     * 
     * @return 
     */
    private Settings instantiateSettings() {
         
        Settings localSettings = new Settings();   
        return localSettings;
    }
    
    //======================================================================
    /**
     * 
     */
    public void saveSettings() {
        
        File saveFile = null;
        //JFileChooser theSettingsFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        theFileChooser.setMultiSelectionEnabled( false ); 
        theFileChooser.setSelectedFile(new File("settings.dat"));
        int returnVal = theFileChooser.showDialog( this, "Save File" ); //Show the dialog
        switch( returnVal ) {

          case JFileChooser.CANCEL_OPTION: //If the user canceled the selecting...
          case JFileChooser.ERROR_OPTION: //If the dialog was dismissed or an error occurred...
            break; //Do nothing

          case JFileChooser.APPROVE_OPTION: //If the user approved the selection...
            saveFile = theFileChooser.getSelectedFile(); //Get the files the user selected
            break;
          default:
            break;

        }

        //Open file and read it
        if(saveFile != null){     
         
            String encoding = theSettings.getDefaultStringEncoding();
            Codec theCodec = Codec.getCodec(encoding);
            encodingComboBox.setSelectedItem(theCodec);        

            OutputStream filestream = null;
            try{
                //use buffering
                //serialize the List
                //note the use of abstract base class references           
                //use buffering
                filestream = new FileOutputStream(saveFile);
                OutputStream buffer = new BufferedOutputStream(filestream);
                ObjectOutput output = new ObjectOutputStream(buffer);
                output.writeObject(theSettings);
                output.flush();
                output.close();

            } catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if( filestream != null ){
                    try {
                        filestream.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    //========================================================================
    /**
     * 
     * @return 
     */
    public Codec getSelectedCodec() {
         return (Codec) encodingComboBox.getSelectedItem();  
    }
    
    //========================================================================
    /**
     * 
     * @param passedCodec 
     */
    public void setSelectedCodec( Codec passedCodec ) {
         encodingComboBox.setSelectedItem(passedCodec);
	}
	
    //========================================================================
    /**
     * @return 
     */
    public DosHeader getDosHeader() {
        return theDosHeader;
    }

    //========================================================================
    /**
     * 
     * @return 
     */
    public NtHeader getNtHeader() {
        return theNtHeader;
    }

}