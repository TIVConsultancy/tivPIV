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
import com.tivconsultancy.opentiv.helpfunctions.strings.StringWorker;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerSQL;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;

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
//        hints.removeSettings("sql_experimentident");
//        hints.removeSettings("sql_evalsettingspiv");
//        List<String> availExperiments = getColumnEntries("piv", "experiment", "ident");
//        for (String s : availExperiments) {
//            hints.addSettingsObject(new SettingObject("Experiment", "sql_experimentident", s, SettingObject.SettingsType.String));
//        }
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

//    public void importCSVfile(String sDir) {
//        File f = new File(sDir);
//        for (File af : f.listFiles()) {
//            if (af.getName().contains("Complete") && af.getName().contains(".csv")) {
//                int iAffectedRows = getDatabase(null).performStatement("COPY piv.liqvelo (experiment, settings, timestampexp, posx, posy, posz, velox, veloy) FROM '" + af.toPath() + "' CSV HEADER;");
//                System.out.println(iAffectedRows);
//            }
//        }
//    }
    public BufferedImage readIMGFromSQL(String experiment, String ident) throws SQLException, IOException {
        InputStream is = sqlData.getBinaryStream(getreadEntryPic(ident, experiment));
        BufferedImage img = ImageIO.read(is);
        return img;
    }

    public List<String> getFileNamesFromSQL() {
        return sqlData.getColumnEntries("pivexp", "pictures", "ident", "WHERE experiment = " + ((PIVMethod) StaticReferences.controller.getCurrentMethod()).experimentSQL);
    }

    public List<String> getAvailExperiments() {
        return getColumnEntries("piv", "experiment", "ident");
    }
    
    public List<String> getAvailSettings(String experiment) {
        return sqlData.getColumnEntries("pivexp", "settings", "ident", "WHERE experiment = '" + experiment+"'");
    }
    
    public List<String[]> getSettings(String experiment, String ident) {
        String settingString = sqlData.getColumnEntries("pivexp", "settings", "settingstring", "WHERE experiment = '" + experiment + "' AND ident ='" +ident+"'").get(0);
        List<String[]> settingsSplit = new ArrayList<>();
        for(String line: settingString.split("\\r?\\n")){
            settingsSplit.add(StringWorker.cutElements(";", line).toArray(new String[4]));
        }
        return settingsSplit;
    }

    public String getreadEntryPic(String ident, String experiment) {
        String sqlStatement = "SELECT picture FROM pivexp.pictures WHERE ident = '" + ident + "' AND experiment = '" + experiment + "'";
        return sqlStatement;
    }

    public void settingsToSQL(String experiment, String ident) {
        List<String> allSettings = new ArrayList<>();
        for (Protocol p : StaticReferences.controller.getCurrentMethod().getProtocols()) {
            allSettings.addAll(p.getForFile());
        }

        String sout = "";
        for (Object s : allSettings) {

            sout = sout + (s.toString());
            sout = sout + ("\n");
        }

        writeSettingsToSQL(experiment, ident, sout);
    }

    public boolean writeSettingsToSQL(String experiment, String ident, String settingsString) {
        try {
            if ("adminpiv".equalsIgnoreCase(StaticReferences.controller.getSQLControler(null).getUser())) {
                getDatabase(null).performStatement(getupserEntrySettings(experiment, ident, settingsString));
            } else {
                getDatabase(null).performStatement(getinsertEntrySettings(experiment, ident, settingsString));
            }
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot insert Settings", e);
        }

        return true;
    }

    public String getinsertEntrySettings(String experiment, String ident, String settingsString) {
        String sqlStatement = "INSERT INTO pivexp.settings (experiment, ident, settingstring)" + " VALUES('" + experiment + "','" + ident + "','" + settingsString + "')";
        return sqlStatement;
    }

    public String getupserEntrySettings(String experiment, String ident, String settingsString) {
        String sqlStatement = "INSERT INTO pivexp.settings (experiment, ident, settingstring)" + " VALUES('" + experiment + "','" + ident + "','" + settingsString + "')"
                + "ON CONFLICT (experiment, ident) DO UPDATE SET "
                + "experiment = EXCLUDED.experiment, "
                + "ident = EXCLUDED.ident,"
                + "settingstring = EXCLUDED.settingstring";
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
