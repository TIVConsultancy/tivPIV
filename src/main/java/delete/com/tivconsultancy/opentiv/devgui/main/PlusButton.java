/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import delete.com.tivconsultancy.opentiv.devgui.feature.Feature;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

/**
 *
 * @author Thomas Ziegenhein
 */
public class PlusButton extends JButton implements CallingFeatureWindow{
    
    Enum sCategory;
    DefaultListModel oMasterList;
    
    public PlusButton(String sText, Enum sCategory, DefaultListModel oMaster){
        super(sText);
        this.sCategory = sCategory;
        this.oMasterList = oMaster;
        this.setBorder(null);
        this.setFont(javax.swing.UIManager.getDefaults().getFont("Button.font").deriveFont(20f));
        this.setPreferredSize(new Dimension(30, 30));
        this.setText("+");
    }    
    

    @Override
    public void addFeature(Feature oF) {
        oMasterList.addElement(oF);        
    }

    @Override
    public Enum getCategory() {
        return sCategory;
    }
    
    
    
}
