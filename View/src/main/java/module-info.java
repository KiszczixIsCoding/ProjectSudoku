module pl.comp {
    requires javafx.controls;
    requires javafx.fxml;
    requires pl.comp.model;
    requires org.apache.logging.log4j;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    opens pl.comp to javafx.fxml;
    exports pl.comp;
}