/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.frames;
;
import delete.com.tivconsultancy.opentiv.devgui.main.HistogramPane;
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
public class Panel_PreProc extends JPanel {

    public StandardPanel oHistPanel;// = new StandardPanel("Histogram", Data.CONSTANTS.PHIST);
    public StandardPanel oSmoothing;// = new StandardPanel("Smoothing", Data.CONSTANTS.PSMOTH);
    public StandardPanel oNR;// = new StandardPanel("Noise Reduction", Data.CONSTANTS.PNR);
    public HistogramPane oHist = new HistogramPane();
    
    Frame_Main MainFrame;

    JLabel a = new JLabel("");

    public Panel_PreProc(String sType, Frame_Main MainFrame) {
        super();
        this.MainFrame = MainFrame;
        oHistPanel = new StandardPanel("Histogram", StandardEnum.Hist, sType, MainFrame);
        oSmoothing = new StandardPanel("Smoothing", StandardEnum.Smoothing, sType, MainFrame);
        oNR = new StandardPanel("Noise Reduction", StandardEnum.NR, sType, MainFrame);
        this.add(oHistPanel);
        this.add(oSmoothing);
        this.add(oNR);
        this.add(oHist);
        this.add(a);
        setLayout();
    }

    public void setLayout() {
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

        ((GridBagLayout) this.getLayout()).setConstraints(oHistPanel, c);

        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1; //GridBagConstraints.REMAINDER;
        c.gridheight = 1; //GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;

        ((GridBagLayout) this.getLayout()).setConstraints(oSmoothing, c);

        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1; //GridBagConstraints.REMAINDER;
        c.gridheight = 1; //GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;

        ((GridBagLayout) this.getLayout()).setConstraints(oNR, c);

        c = new GridBagConstraints();
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1; //GridBagConstraints.REMAINDER;
        c.gridheight = 1; //GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;

        ((GridBagLayout) this.getLayout()).setConstraints(oHist, c);

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

    public void cleanup() {
        oHistPanel.cleanList();
        oSmoothing.cleanList();
        oNR.cleanList();
    }

}
