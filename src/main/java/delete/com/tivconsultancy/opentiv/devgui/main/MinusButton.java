/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Thomas Ziegenhein
 */
public class MinusButton extends JButton {
    
     DefaultListModel oMasterList;
    public MinusButton(String sText, DefaultListModel oMaster){
        super(sText);        
        this.oMasterList = oMaster;
        this.setBorder(null);
        this.setFont(javax.swing.UIManager.getDefaults().getFont("Button.font").deriveFont(20f));
        this.setPreferredSize(new Dimension(30, 30));
        this.setText("-");
        setVerticalAlignment(SwingConstants.TOP);
    }
}
