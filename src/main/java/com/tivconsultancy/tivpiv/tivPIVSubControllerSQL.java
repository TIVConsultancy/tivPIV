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
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class tivPIVSubControllerSQL extends StartUpSubControllerSQL {

    protected float t = 0.0f;

    @Override
    public String connect(String user, String password, String database, String host) {
        sqlData = new PostgreSQL("jdbc:postgresql://" + host + "/" + database, user, password);
        sqlData.connect();
        if (sqlData.getStatus() == null) {
            StaticReferences.getlog().log(Level.SEVERE, "SQL Status: " + sqlData.getStatus(), new Throwable());
        } else {
            StaticReferences.getlog().log(Level.INFO, "SQL Status: " + sqlData.getStatus());
        }
        Settings hints = ((PIVController) StaticReferences.controller).getHintsSettings();
        hints.removeSettings("sql_experimentident");
        hints.removeSettings("sql_evalsettingspiv");
        List<String> availExperiments = getColumnEntries("piv", "experiment", "ident");
        for (String s : availExperiments) {
            hints.addSettingsObject(new SettingObject("Experiment", "sql_experimentident", s, SettingObject.SettingsType.String));
        }
        List<String> availSettingsPIV = getColumnEntries("piv", "evalsettingspiv", "ident");
        for (String s : availSettingsPIV) {
            hints.addSettingsObject(new SettingObject("Settings PIV", "sql_evalsettingspiv", s, SettingObject.SettingsType.String));
        }
        ((PIVController) StaticReferences.controller).refreshSettings();
        return sqlData.getStatus();
    }

    public void setTimeStamp(double t) {
        this.t = (float) t;
    }

    public Double getResolution(String experiment) {
        try {
            String schemaName = "piv";
            String tableName = "experiment";
            String columnName = "resolution";
            String stmtSQL = "SELECT * FROM " + schemaName + "." + tableName + " WHERE ident = '" + experiment + "'";
            List<String> ls = getDatabase(null).getcolumValues(stmtSQL, columnName);
            if (ls == null || ls.isEmpty()) {
                return null;
            }
            return Double.valueOf(ls.get(0));
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot read resolution from SQL database, resolution set to 10 micron/Px", e);
            return 10.0;
        }

    }

    public int insertEntry(sqlEntryPIV ent) {
        return getDatabase(null).performStatement(getinsertEntry(ent));
    }

    public void insertEntry(List<sqlEntryPIV> ent) {
        List<String> entriesSQL = new ArrayList<>();
        for (sqlEntryPIV e : ent) {
            entriesSQL.add(getinsertEntry(e));
        }
        getDatabase(null).performStatements(entriesSQL);
    }

    public int upsertEntry(sqlEntryPIV ent) {
        return getDatabase(null).performStatement(getupserEntry(ent));
    }

    public void upsertEntry(List<sqlEntryPIV> ent) {
        List<String> entriesSQL = new ArrayList<>();
        for (sqlEntryPIV e : ent) {
            entriesSQL.add(getupserEntry(e));
        }
        getDatabase(null).performStatements(entriesSQL);
    }

    public String getinsertEntry(sqlEntryPIV e) {
        String sqlStatement = "INSERT INTO piv.liqvelo (experiment, timestampexp, posx, posy, posz, velox, veloy) "
                + "VALUES('" + e.experiment + "', '" + e.settingsName + "', " + t + ", " + e.posX + ", " + e.posY + ", " + e.posZ + ", " + e.vX + ", " + e.vY + ")";
        return sqlStatement;
    }

    public String getupserEntry(sqlEntryPIV e) {
        String sqlStatement = "INSERT INTO piv.liqvelo (experiment, settings, timestampexp, posx, posy, posz, velox, veloy) "
                + "VALUES('" + e.experiment + "', '" + e.settingsName + "', " + t + ", " + e.posX + ", " + e.posY + ", " + e.posZ + ", " + e.vX + ", " + e.vY + ")"
                + "ON CONFLICT (experiment, settings, timestampexp, posx, posy, posz) DO UPDATE SET "
                + "experiment = EXCLUDED.experiment, "
                + "settings = EXCLUDED.settings,"
                + "timestampexp = EXCLUDED.timestampexp, "
                + "posx = EXCLUDED.posx, "
                + "posy = EXCLUDED.posy, "
                + "posz = EXCLUDED.posz, "
                + "velox = EXCLUDED.velox, "
                + "veloy = EXCLUDED.veloy";
        return sqlStatement;
    }

    public static class sqlEntryPIV {

        String experiment;
        String settingsName;
        double posX;
        double posY;
        double posZ;
        double vX;
        double vY;

        public sqlEntryPIV(String experiment, String settingsName, double posX, double posY, double posZ, double vX, double vY) {
            this.experiment = experiment;
            this.settingsName = settingsName;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.vX = vX;
            this.vY = vY;
        }

    }

}
