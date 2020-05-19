/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author Thomas Ziegenhein
 */
public class StatusBar extends JLabel {
    
    double dNumber = 0;
    String sPostText = "%";
    
    public StatusBar(String sText){
        super(sText);
        this.setPreferredSize(new Dimension(100, 20));
        this.setMinimumSize(new Dimension(100, 20));
    }
    
    private void setStatus(double dFraction){
        dNumber = (double)((int) (dFraction *1000.0))/10.0;        
    }
    
    public double getStatus(){
        return dNumber*10.0/1000.0;
    }
    
    public void updateStatus(double dFraction){
        setStatus(dFraction);
        this.repaint();
    }
    
    public void reset(){
        dNumber = 0.0;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(47, 135, 215));
        g.drawRect(0, 0, (int)(dNumber), getHeight()-1);
        g.fillRect(0, 0, (int)(dNumber), getHeight()-1);
        this.setText(dNumber + sPostText);
        super.paintComponent(g);
        g.dispose();
    }

}
