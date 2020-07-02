/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.dialogs;

import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class DialogSettingsToSQL extends Dialog<Map<Enum, String>> {

    private ComboBox combExperiments;
    private TextField ident;
    private ButtonType processButton = new ButtonType("Export", ButtonBar.ButtonData.OK_DONE);

    public DialogSettingsToSQL() {
        setTitle("Insert Settings into SQL database");
//        setHeaderText("Specify Experimnent");
        List<String> availExperiments = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).getAvailExperiments();

        ObservableList<String> availExp = FXCollections.observableArrayList(availExperiments);
        combExperiments = new ComboBox(availExp);
        combExperiments.setPromptText("Experiments in database");

        getDialogPane().getButtonTypes().addAll(processButton, ButtonType.CANCEL );

        Label expName = new Label("Experiment: ");
        HBox expCB = new HBox(expName, combExperiments);
        expCB.setSpacing(5);
        expCB.setAlignment(Pos.BASELINE_RIGHT);
        
        Label identName = new Label("Settings Name: ");
        ident = new TextField();
        ident.setText("MySettings1");
        HBox nameChoice = new HBox(identName, ident);
        nameChoice.setSpacing(5);
        nameChoice.setAlignment(Pos.BASELINE_RIGHT);

        VBox vBox = new VBox();
        vBox.getChildren().add(expCB);
        vBox.getChildren().add(nameChoice);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.BASELINE_RIGHT);

        vBox.setSpacing(5);

        getDialogPane().setContent(vBox);

        Platform.runLater(() -> combExperiments.requestFocus());

        ((Button) getDialogPane().lookupButton(processButton)).addEventFilter(ActionEvent.ACTION, ae -> {
                                                                          if (null == combExperiments.getValue()) {
                                                                              ae.consume(); //When no exp is selected 
                                                                          }
                                                                      });

        setResultConverter(dialogButton -> {
            if (dialogButton == processButton) {
                Map<Enum, String> entries = new HashMap<>();
                entries.put(fieldNames.EXP, String.valueOf(combExperiments.getValue()));
                entries.put(fieldNames.IDENT, String.valueOf(ident.getText()));
                return entries;
            }
            return null;
        });
    }

    public enum fieldNames {
        EXP, IDENT
    }

}
