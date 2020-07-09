open module pl.comp.model {
    requires org.apache.commons.lang3;
    requires com.google.common;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires java.persistence;
requires org.hibernate.orm.core;
    exports pl.comp.model;
    exports pl.comp.model.exceptions;
}