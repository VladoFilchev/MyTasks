package com.example.tasksmanagementsystem;

import com.example.tasksmanagementsystem.stage.ProgramScreens;
import javafx.application.Application;
import javafx.stage.Stage;

public class TMSRun extends Application {
    ProgramScreens programScreens=new ProgramScreens();
    @Override
    public void start(Stage stage) throws Exception {
programScreens.showRegistration();
    }
}
