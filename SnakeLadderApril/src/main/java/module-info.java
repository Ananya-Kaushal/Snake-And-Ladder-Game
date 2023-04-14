module com.example.snakeladderapril {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakeladderapril to javafx.fxml;
    exports com.example.snakeladderapril;
}