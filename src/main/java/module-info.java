module co.edu.uniquindio.poo.templatemetthod {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens co.edu.uniquindio.poo.templatemetthod to javafx.fxml;
    exports co.edu.uniquindio.poo.templatemetthod;
    exports co.edu.uniquindio.poo.templatemetthod.Controllers;
    opens co.edu.uniquindio.poo.templatemetthod.Controllers to javafx.fxml;
}