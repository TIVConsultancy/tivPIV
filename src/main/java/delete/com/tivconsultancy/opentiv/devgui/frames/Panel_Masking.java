/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.frames;

import delete.com.tivconsultancy.opentiv.devgui.mvc_model.StandardEnum;
import delete.com.tivconsultancy.opentiv.devgui.main.StandardPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Thomas Ziegenhein
 */
public class Panel_Masking extends JPanel {  
    
    public StandardPanel oSimpleMasking;
    Frame_Main MainFrame;
    
    JLabel a = new JLabel("");
    
    public Panel_Masking(String sType, Frame_Main MainFrame){
        super();
        this.MainFrame = MainFrame;
        oSimpleMasking = new StandardPanel("Simple Shapes", StandardEnum.Mask, sType, MainFrame);
        this.add(oSimpleMasking);
        this.add(a);
        setLayout();   
    }
    
    public void setLayout(){
        this.setLayout(new GridBagLayout());        
        GridBagConstraints c;
                        
        
        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1; //GridBagConstraints.REMAINDER;
        c.gridheight = 1; //GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        
        ((GridBagLayout) this.getLayout()).setConstraints(oSimpleMasking, c);        
        
        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1; //GridBagConstraints.REMAINDER;
        c.gridheight = 1; //GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTH;
        
        ((GridBagLayout) this.getLayout()).setConstraints(a, c);
        
    }
    
        public void cleanup(){
        oSimpleMasking.cleanList();
    }

}
