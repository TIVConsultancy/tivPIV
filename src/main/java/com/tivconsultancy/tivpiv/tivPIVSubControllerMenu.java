/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv;

import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.tivGUI.Dialogs.Data.DialogNote;
import com.tivconsultancy.tivGUI.Dialogs.Data.DialogSQL;
import com.tivconsultancy.tivGUI.Dialogs.Tools.DialogCutImage;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.controller.ControllerUI;
import com.tivconsultancy.tivGUI.controller.subControllerMenu;
import com.tivconsultancy.tivGUI.controller.subControllerSQL;
import com.tivconsultancy.tivGUI.menue.tivMenuBar;
import com.tivconsultancy.tivpiv.dialogs.DialogSQLToSettings;
import com.tivconsultancy.tivpiv.dialogs.DialogSQLtoPic;
import com.tivconsultancy.tivpiv.dialogs.DialogSettingsToSQL;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialog;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class tivPIVSubControllerMenu implements subControllerMenu {

    LookUp<EventHandler<ActionEvent>> actionEvents;
    LookUp<List<String>> subMenuEntries;
    LookUp<ImageView> icons;
    List<String> mainMenu;
    private EventHandler<ActionEvent> sql;

    public tivPIVSubControllerMenu() {
        StaticReferences.initIcons(this);
        initMainItems();
        initMainEntries();
        initIcons();
        initActionEvents();
        initGlobalEventHandlers();
    }

    private void initGlobalEventHandlers() {
        sql = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Dialog dialSQL = new DialogSQL();
                StaticReferences.controller.setDialog(ControllerUI.DialogNames_Default.SQL, dialSQL);
                Optional<Map<Enum, String>> retrunSQLDialog = dialSQL.showAndWait();
                retrunSQLDialog.ifPresent(Map -> {
                    subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
                    controllerSQL.connect(Map.get(DialogSQL.fieldNames.user), Map.get(DialogSQL.fieldNames.password), Map.get(DialogSQL.fieldNames.database), Map.get(DialogSQL.fieldNames.hostname));
                });

            }
        };
    }

    private void initMainItems() {
        mainMenu = new ArrayList<>();
        mainMenu.add(dictionary(MainItems.Session));
        mainMenu.add(dictionary(MainItems.Run));
        mainMenu.add(dictionary(MainItems.Data));
        mainMenu.add(dictionary(MainItems.Tools));
    }

    private void initMainEntries() {
        subMenuEntries = new LookUp<>();
        List<String> SessionEntries = new ArrayList<>();
        SessionEntries.add(dictionary(MenuEntries.New));
        SessionEntries.add(dictionary(MenuEntries.Load));
        SessionEntries.add(dictionary(MenuEntries.ImportSettings));
        SessionEntries.add(dictionary(MenuEntries.ExportSettings));
        SessionEntries.add(tivMenuBar.tivSpecialMenue.SEP.toString());
        SessionEntries.add(dictionary(MenuEntries.LoadSQL));
        SessionEntries.add(dictionary(MenuEntries.ImportSettingsSQL));
        SessionEntries.add(dictionary(MenuEntries.ExportSettingsSQL));
        subMenuEntries.add(new NameObject<>(dictionary(MainItems.Session), SessionEntries));

        List<String> RunEntries = new ArrayList<>();
        RunEntries.add(dictionary(MenuEntries.OneStep));
        RunEntries.add(dictionary(MenuEntries.RunAll));
        subMenuEntries.add(new NameObject<>(dictionary(MainItems.Run), RunEntries));

        List<String> dataEntries = new ArrayList<>();
        dataEntries.add(dictionary(MenuEntries.SQL));
//        dataEntries.add(dictionary(MenuEntries.ImportCSVtoSQL));
        subMenuEntries.add(new NameObject<>(dictionary(MainItems.Data), dataEntries));

        List<String> toolsEntries = new ArrayList<>();
        toolsEntries.add(dictionary(MenuEntries.CutImage));
        subMenuEntries.add(new NameObject<>(dictionary(MainItems.Tools), toolsEntries));

    }

    private void initIcons() {
        icons = new LookUp<>();
        try {
            icons.add(new NameObject<>(dictionary(MenuEntries.New), StaticReferences.standardIcons.get("plus2.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.Load), StaticReferences.standardIcons.get("folderOpen2.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.LoadSQL), StaticReferences.standardIcons.get("cloud-Border.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.ImportSettings), StaticReferences.standardIcons.get("export2.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.ImportSettingsSQL), StaticReferences.standardIcons.get("support.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.ExportSettings), StaticReferences.standardIcons.get("import2.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.ExportSettingsSQL), StaticReferences.standardIcons.get("settingsCloud.png")));

            icons.add(new NameObject<>(dictionary(MenuEntries.OneStep), StaticReferences.standardIcons.get("walking.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.RunAll), StaticReferences.standardIcons.get("runningMult.png")));

            icons.add(new NameObject<>(dictionary(MenuEntries.SQL), StaticReferences.standardIcons.get("sql.png")));
//        icons.add(new NameObject<>(dictionary(MenuEntries.ImportCSVtoSQL), StaticReferences.standardIcons.get("export.png")));
            icons.add(new NameObject<>(dictionary(MenuEntries.CutImage), StaticReferences.standardIcons.get("scissors.png")));
        } catch (IOException ex) {
            Logger.getLogger(tivPIVSubControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initActionEvents() {
        actionEvents = new LookUp<>();
        EventHandler<ActionEvent> newSession = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                DirectoryChooser direcChooser = new DirectoryChooser();
                direcChooser.setTitle("Start New Session");
                File selectedFile = direcChooser.showDialog(StaticReferences.controller.getMainWindows());
                StaticReferences.controller.startNewSession(selectedFile);
            }
        };

        EventHandler<ActionEvent> loadSession = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Load Session");
                File selectedFile = fileChooser.showDialog(StaticReferences.controller.getMainWindows());
                StaticReferences.controller.loadSession(selectedFile);
            }
        };

        EventHandler<ActionEvent> importSettings = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Import Settings");
                File selectedFile = fileChooser.showOpenDialog(StaticReferences.controller.getMainWindows());
                StaticReferences.controller.importSettings(selectedFile);
            }
        };

        EventHandler<ActionEvent> exportSettings = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Export Settings");
                File selectedFile = fileChooser.showSaveDialog(StaticReferences.controller.getMainWindows());
                StaticReferences.controller.exportSettings(selectedFile);
            }
        };

        EventHandler<ActionEvent> runOneStep = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                StaticReferences.controller.runCurrentStep();
            }
        };

        EventHandler<ActionEvent> runAll = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                StaticReferences.controller.run();
            }
        };

        EventHandler<ActionEvent> loadSQL = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
                if (controllerSQL.getDatabase(null) == null) {
                    sql.handle(t);
                }
                Dialog dialSQLEXP = new DialogSQLtoPic();
                StaticReferences.controller.setDialog(PIVController.DialogNames.SQLTOPIC, dialSQLEXP);
                Optional<Map<Enum, String>> retrunSQLDialog = dialSQLEXP.showAndWait();
                retrunSQLDialog.ifPresent(Map -> {
                    ((PIVMethod) StaticReferences.controller.getCurrentMethod()).bReadFromSQL = true;
                    ((PIVMethod) StaticReferences.controller.getCurrentMethod()).experimentSQL = Map.get(DialogSQLtoPic.fieldNames.EXP);
                    StaticReferences.controller.loadSession(null);
                });

            }
        };

        EventHandler<ActionEvent> exportSettingsSQL = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
                if (controllerSQL.getDatabase(null) == null) {
                    sql.handle(t);
                }
                Dialog dialSETSQL = new DialogSettingsToSQL();
                StaticReferences.controller.setDialog(PIVController.DialogNames.SETSQL, dialSETSQL);
                Optional<Map<Enum, String>> retrunDialog = dialSETSQL.showAndWait();
                retrunDialog.ifPresent(Map -> {
                    String experiment = Map.get(DialogSettingsToSQL.fieldNames.EXP);
                    String ident = Map.get(DialogSettingsToSQL.fieldNames.IDENT);
                    try {
                        ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).settingsToSQL(experiment, ident);
                        StaticReferences.getlog().log(Level.INFO, "Settings Exported");
                        Dialog dialOK = new DialogNote("Settings Exported");
                        dialOK.showAndWait();
                    } catch (Exception e) {
                        StaticReferences.getlog().log(Level.SEVERE, "Cannot export settings", e);
                        Dialog dialFail = new DialogNote("Export Failed");
                        dialFail.showAndWait();
                    }
                });

            }
        };

        EventHandler<ActionEvent> importSettingsSQL = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
                if (controllerSQL.getDatabase(null) == null) {
                    sql.handle(t);
                }
                Dialog dialSQLSET = new DialogSQLToSettings();
                StaticReferences.controller.setDialog(PIVController.DialogNames.SQLTOSET, dialSQLSET);
                Optional<Map<Enum, String>> retrunDialog = dialSQLSET.showAndWait();
                retrunDialog.ifPresent(Map -> {
                    String experiment = Map.get(DialogSQLToSettings.fieldNames.EXP);
                    String ident = Map.get(DialogSQLToSettings.fieldNames.IDENT);
                    try {
                        List<String[]> ls = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).getSettings(experiment, ident);
                        ((PIVController) StaticReferences.controller).importSettings(ls);
                        StaticReferences.getlog().log(Level.INFO, "Settings Imported");
//                        Dialog dialOK = new DialogNote("Settings Imported");
//                        dialOK.showAndWait(); 
                    } catch (Exception e) {
                        StaticReferences.getlog().log(Level.SEVERE, "Cannot import settings", e);
                        Dialog dialFail = new DialogNote("Import Failed");
                        dialFail.showAndWait();
                    }
                });

            }
        };

        EventHandler<ActionEvent> sql = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Dialog dialSQL = new DialogSQL();
                StaticReferences.controller.setDialog(ControllerUI.DialogNames_Default.SQL, dialSQL);
                Optional<Map<Enum, String>> retrunSQLDialog = dialSQL.showAndWait();
                retrunSQLDialog.ifPresent(Map -> {
                    subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
                    controllerSQL.connect(Map.get(DialogSQL.fieldNames.user), Map.get(DialogSQL.fieldNames.password), Map.get(DialogSQL.fieldNames.database), Map.get(DialogSQL.fieldNames.hostname));
                });

            }
        };
        //        EventHandler<ActionEvent> importcsvtosql = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent t) {
//                subControllerSQL controllerSQL = StaticReferences.controller.getSQLControler(null);
//                if (controllerSQL.getDatabase(null) == null) {
//                    sql.handle(t);
//                }
//                DirectoryChooser fileChooser = new DirectoryChooser();
//                fileChooser.setTitle("Import CSV Files");
//                File selectedFile = fileChooser.showDialog(StaticReferences.controller.getMainWindows());
//                controllerSQL.importCSVfile(selectedFile.getAbsolutePath());
//            }
//        };
        EventHandler<ActionEvent> imageTools_Cut = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Dialog dialogCutImage = new DialogCutImage();
                StaticReferences.controller.setDialog(ControllerUI.DialogNames_Default.CUT, dialogCutImage);
                dialogCutImage.show();

            }
        };

        actionEvents.add(new NameObject<>(dictionary(MenuEntries.New), newSession));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.Load), loadSession));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.LoadSQL), loadSQL));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.ImportSettings), importSettings));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.ImportSettingsSQL), importSettingsSQL));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.ExportSettings), exportSettings));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.ExportSettingsSQL), exportSettingsSQL));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.OneStep), runOneStep));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.RunAll), runAll));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.SQL), sql));
        actionEvents.add(new NameObject<>(dictionary(MenuEntries.CutImage), imageTools_Cut));
//        actionEvents.add(new NameObject<>(dictionary(MenuEntries.ImportCSVtoSQL), importcsvtosql));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMainItems() {
        return mainMenu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventHandler<ActionEvent> getActionEvent(String ident) {
        return actionEvents.get(ident);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMenuEntries(String ident) {
        return subMenuEntries.get(ident) == null ? new ArrayList<>() : subMenuEntries.get(ident);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageView getIcon(String ident) {
        return icons.get(ident);
    }

    private enum MainItems {
        Session, Run, Data, Tools
    }

    private enum MenuEntries {
        New, Load, OneStep, RunAll, ImportSettings, ImportSettingsSQL, ExportSettings, ExportSettingsSQL, SQL, CutImage, LoadSQL
    }

    private String dictionary(Enum e) {
        if (e == MenuEntries.New) {
            return "New Session";
        }
        if (e == MenuEntries.Load) {
            return "Load Session";
        }
        if (e == MenuEntries.OneStep) {
            return "One Step";
        }
        if (e == MenuEntries.RunAll) {
            return "All";
        }
        if (e == MenuEntries.CutImage) {
            return "Cut Image";
        }
        if (e == MenuEntries.ImportSettings) {
            return "Import Settings";
        }
        if (e == MenuEntries.ExportSettings) {
            return "Export Settings";
        }
        if (e == MenuEntries.ImportSettingsSQL) {
            return "Settings from SQL";
        }
        if (e == MenuEntries.ExportSettingsSQL) {
            return "Settings to SQL";
        }
        if (e == MenuEntries.LoadSQL) {
            return "Images from SQL";
        }
        if (e == MenuEntries.SQL) {
            return "Connect to SQL";
        }

//        if (e == MenuEntries.ImportCSVtoSQL) {
//            return "CSV to SQL";
//        }
        return e.toString();
    }

    private Enum dictionary(String s) {
        if (s.equals("New Session")) {
            return MenuEntries.New;
        }
        for (Enum e : MainItems.values()) {
            if (e.toString().equals(e)) {
                return e;
            }
        }
        for (Enum e : MenuEntries.values()) {
            if (e.toString().equals(e)) {
                return e;
            }
        }
        return null;
    }

}
