/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Thomas Ziegenhein
 */
public class CheckButton extends JButton {
    
    public CheckButton() {
        super();
        this.setBorder(null);
        this.setFont(javax.swing.UIManager.getDefaults().getFont("Button.font").deriveFont(20f));
        this.setPreferredSize(new Dimension(30, 30));
        this.setText("\u2713");
    }
        
    
}
