/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.controller.subControllerSQL;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVDataHandling extends PIVProtocol {
  
    
    private String name = "DataExport";

    public Prot_PIVDataHandling() {
        super();
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return new NameSpaceProtocolResults1D[0];
    }

    @Override
    public List<String> getIdentForViews() {
        return Arrays.asList(new String[]{});
    }
    
    @Override
    public void setImage(BufferedImage bi){
        buildLookUp();
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        if(Boolean.valueOf(this.getSettingsValue("sql_activation").toString())){
            tivPIVSubControllerSQL sql_Control = (tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null);
//            sql_Control.
        }
    }

    @Override
    public Object[] getResults() {
        return new Object[0];
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
        this.loSettings.add(new SettingObject("Export->SQL", "sql_activation", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Experiment", "sql_experimentident", "NabilColumnTergitol1p0", SettingObject.SettingsType.String));
    }

    @Override
    public void buildClusters() {
        SettingsCluster IMGFilter = new SettingsCluster("SQL",
                                                        new String[]{"sql_activation", "sql_experimentident"}, this);
        IMGFilter.setDescription("Handles the export to the SQL database");
        lsClusters.add(IMGFilter);
    }

}
