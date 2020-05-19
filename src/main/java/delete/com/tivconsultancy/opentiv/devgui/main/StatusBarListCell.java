/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;

/**
 *
 * @author Thomas Ziegenhein
 */
public class StatusBarListCell extends JLabel {
    
    public StatusBarListCell() {
        super();        
    }
    
    public StatusBarListCell(JLabel o) {
        super(o.getText());
        super.setBackground(o.getBackground());
        super.setForeground(o.getForeground());
//        super.get
//        super = o;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(47, 135, 215));
        g.drawRect(0, 0, getWidth()/2, getHeight()-1);
        g.fillRect(0, 0, getWidth()/2, getHeight()-1);
        super.paintComponent(g);
        g.dispose();
    }
    
}
