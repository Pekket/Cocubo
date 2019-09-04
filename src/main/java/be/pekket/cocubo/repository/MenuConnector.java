package be.pekket.cocubo.repository;

import be.pekket.cocubo.exception.ConnectorException;

public interface MenuConnector {
    String getMenuUrl() throws ConnectorException;

    void getMenu( String url ) throws ConnectorException;
}
