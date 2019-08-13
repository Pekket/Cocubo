package be.pekket.cocubo.repository;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.model.Menu;

public interface JsonDataRepository {

    Menu fetchData() throws CocuboException;

    void storeData( Menu menu ) throws CocuboException;

    void removeData();
}
