module co.edu.uniquindio.techsolutions {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.techsolutions to javafx.fxml;
    opens co.edu.uniquindio.techsolutions.viewController to javafx.fxml;

    exports co.edu.uniquindio.techsolutions;
    exports co.edu.uniquindio.techsolutions.viewController;
    exports co.edu.uniquindio.techsolutions.controller;
    exports co.edu.uniquindio.techsolutions.model;
    exports co.edu.uniquindio.techsolutions.model.composite;
    exports co.edu.uniquindio.techsolutions.model.decorator;
    exports co.edu.uniquindio.techsolutions.model.factory;
    exports co.edu.uniquindio.techsolutions.model.builder;
    exports co.edu.uniquindio.techsolutions.model.adapter;
    exports co.edu.uniquindio.techsolutions.services;
}