package be.pekket.cocubo.repository;

import be.pekket.cocubo.exception.ConnectorException;

public interface MenuConnector {

    void getMenu( String url ) throws ConnectorException;
}
