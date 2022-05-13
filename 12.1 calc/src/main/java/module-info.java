module fi.tuni.prog3.calc {
    // requires javafx.controls;

    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens fi.tuni.prog3.calc to javafx.fxml;
    exports fi.tuni.prog3.calc;
}