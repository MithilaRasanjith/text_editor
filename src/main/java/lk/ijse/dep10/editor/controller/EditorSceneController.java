package lk.ijse.dep10.editor.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class EditorSceneController {

    public MenuItem mnSaveAs;
    public String text;
    public boolean isSave;
    public File file;
    public File previousSaveFile;
    @FXML
    private SeparatorMenuItem MenuItem;
    @FXML
    private MenuItem mnAbout;
    @FXML
    private MenuItem mnClose;
    @FXML
    private MenuItem mnNew;
    @FXML
    private MenuItem mnOpen;
    @FXML
    private MenuItem mnPrint;
    @FXML
    private MenuItem mnSave;
    @FXML
    private HTMLEditor txtEditor;
    private Stage newStage;

    @FXML
    void mnAboutOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/AboutScene.fxml")).load()));
        stage.setTitle("About");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(txtEditor.getScene().getWindow());
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() {
        text = txtEditor.getHtmlText();
        Platform.runLater(() -> {
            newStage = (Stage) txtEditor.getScene().getWindow();
            newStage.setOnCloseRequest(windowEvent -> {
                try {
                    mnCloseOnAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        });


    }

    @FXML
    void mnCloseOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        String title = stage.getTitle() + "";
        if (title.startsWith("*")) {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "The file hasn't been saved, Do you want to save ?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.isEmpty()) return;
            else if (buttonType.get() == ButtonType.NO) {
                stage.close();
            } else {
                isSave = false;
                mnSaveOnAction(event);
            }

        } else{
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to exit?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) stage.close();
        }
    }

    @FXML
    void mnNewOnAction(ActionEvent event) {
        isSave = false;
        file = null;
        txtEditor.setHtmlText("");
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void mnOpenOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Input a text file");
        File file = fileChooser.showOpenDialog(txtEditor.getScene().getWindow());
        if (file == null) return;

        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        txtEditor.setHtmlText(new String(bytes));
    }

    @FXML
    void mnPrintOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.ERROR, "Printer ekk nane mahaththayoo").showAndWait();
    }

    @FXML
    void mnSaveOnAction(ActionEvent event) throws IOException {
        if (isSave) {
            FileOutputStream fos = new FileOutputStream(file);

            String text = txtEditor.getHtmlText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);

            fos.close();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save a text File");
            file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
            if (file == null) return;

            FileOutputStream fos = new FileOutputStream(file);

            String text = txtEditor.getHtmlText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);

            fos.close();
            isSave = true;
        }
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stage.setTitle(file.getName());
        previousSaveFile = file;
    }

    public void mnSaveAsOnAction(ActionEvent event) throws IOException {
        if (isSave) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save a text File");
            file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
            if (file == null) return;

            FileOutputStream fos = new FileOutputStream(file);

            String text = txtEditor.getHtmlText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);

            fos.close();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save a text File");
            file = fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
            if (file == null) return;

            FileOutputStream fos = new FileOutputStream(file);

            String text = txtEditor.getHtmlText();
            byte[] bytes = text.getBytes();
            fos.write(bytes);

            fos.close();
            isSave = true;
        }
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        stage.setTitle(file.getName());
        previousSaveFile = file;

    }

    public void rootOnDragOver(DragEvent dragEvent) {

    }

    public void rootOnDragDropped(DragEvent dragEvent) throws IOException {
        File droppedFile = dragEvent.getDragboard().getFiles().get(0);

        FileInputStream fis = new FileInputStream(droppedFile);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        txtEditor.setHtmlText(new String(bytes));
    }

    public void isTyped() {
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        if (!text.equals(txtEditor.getHtmlText())) {
            stage.setTitle("*Untitled Document");
        } else {
            stage.setTitle("Untitled Document");
        }
    }

    public void txtEditorOnKeyPressed(KeyEvent keyEvent) {
        Stage stage = (Stage) txtEditor.getScene().getWindow();
        if (file != null) {
            stage.setTitle("*" + file.getName());
        } else {
            stage.setTitle("*Untitled Document");
        }

    }

    public void txtEditorOnKeyReleased(KeyEvent keyEvent) {

    }

    public void txtEditorOnDragDropped(DragEvent dragEvent) throws IOException {
        File droppedFile = dragEvent.getDragboard().getFiles().get(0);

        FileInputStream fis = new FileInputStream(droppedFile);
        byte[] bytes = fis.readAllBytes();
        fis.close();

        txtEditor.setHtmlText(new String(bytes));
    }

}
