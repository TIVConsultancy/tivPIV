/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.dialogs;

import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class DialogSQLToSettings extends Dialog<Map<Enum, String>> {

    private ComboBox combExperiments;
    private ComboBox ident;
    private ButtonType processButton = new ButtonType("Import", ButtonBar.ButtonData.OK_DONE);

    public DialogSQLToSettings() {
        setTitle("Insert Settings into SQL database");
//        setHeaderText("Specify Experimnent");

        getDialogPane().getButtonTypes().addAll(processButton, ButtonType.CANCEL);

        List<String> availExperiments = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).getAvailExperiments();
        ObservableList<String> availExp = FXCollections.observableArrayList(availExperiments);
        combExperiments = new ComboBox(availExp);
        combExperiments.setPromptText("Experiments in database");
        combExperiments.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                ident.setItems(getAvailIdents(String.valueOf(newValue)));
            }
        });

        Label expName = new Label("Experiment: ");
        HBox expCB = new HBox(expName, combExperiments);
        expCB.setSpacing(5);
        expCB.setAlignment(Pos.BASELINE_RIGHT);

        Label identName = new Label("Settings Name: ");
        ident = new ComboBox(getAvailIdents(null));
        ident.setPromptText("Settings in database");
        HBox settingsCB = new HBox(identName, ident);
        settingsCB.setSpacing(5);
        settingsCB.setAlignment(Pos.BASELINE_RIGHT);

        VBox vBox = new VBox();
        vBox.getChildren().add(expCB);
        vBox.getChildren().add(settingsCB);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.BASELINE_RIGHT);

        vBox.setSpacing(5);

        getDialogPane().setContent(vBox);

        Platform.runLater(() -> combExperiments.requestFocus());

        ((Button) getDialogPane().lookupButton(processButton)).addEventFilter(ActionEvent.ACTION, ae -> {
                                                                          if (null == combExperiments.getValue() || null == ident.getValue()) {
                                                                              ae.consume(); //When no exp is selected 
                                                                          }
                                                                      });

        setResultConverter(dialogButton -> {
            if (dialogButton == processButton) {
                Map<Enum, String> entries = new HashMap<>();
                entries.put(fieldNames.EXP, String.valueOf(combExperiments.getValue()));
                entries.put(fieldNames.IDENT, String.valueOf(ident.getValue()));
                return entries;
            }
            return null;
        });
    }

    public ObservableList<String> getAvailIdents(String experiment) {
        List<String> availIdent = new ArrayList<>();
        try {           
            availIdent = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).getAvailSettings(experiment);
        } catch (Exception e) {
        }

        return FXCollections.observableArrayList(availIdent);
    }

    public enum fieldNames {
        EXP, IDENT
    }

}
