/*
 * Copyright 2020 TIVConsultancy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tivconsultancy.tivpiv;

import com.tivconsultancy.opentiv.datamodels.SQL.PostgreSQL;
import com.tivconsultancy.opentiv.datamodels.SQL.SQLDatabase;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.controller.subControllerSQL;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerSQL;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class tivPIVSubControllerSQL extends StartUpSubControllerSQL {
    
    private double t = 0.0;
    
    @Override
    public String connect(String user, String password, String database, String host) {
        sqlData = new PostgreSQL("jdbc:postgresql://"+host+"/" + database, user, password);
        sqlData.connect();
        if( sqlData.getStatus() == null){
            StaticReferences.getlog().log(Level.SEVERE, "SQL Status: " + sqlData.getStatus(), new Throwable());
        }else{
            StaticReferences.getlog().log(Level.INFO, "SQL Status: " + sqlData.getStatus());
        }
        Settings hints = ((PIVController) StaticReferences.controller).getHintsSettings();
        hints.removeSettings("sql_experimentident");
        List<String> availExperiments = getColumnEntries("piv", "experiment", "ident");
        for(String s : availExperiments){
            hints.addSettingsObject(new SettingObject("Experiment", "sql_experimentident", s, SettingObject.SettingsType.String));
        }
        ((PIVController) StaticReferences.controller).refreshSettings();
        return sqlData.getStatus();
    }
    
    public void setTimeStamp(double t){
        this.t = t;
    }
    
    public int writeEntry(String experiment, double posX, double posY, double posZ, double vX, double vY, double vZ){
        String sqlStatement = "INSERT INTO piv.liqvelo (experiment, timestampexp, posx, posy, posz, velox, veloy, veloz) "
                + "VALUES('"+experiment+"', "+t+", "+posX+", "+posY+", "+posZ+", "+vX+", "+vY+", "+vY+")";
        return getDatabase(null).addColumnValue(sqlStatement);
    }
    

}
