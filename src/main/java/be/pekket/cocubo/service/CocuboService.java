package be.pekket.cocubo.service;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.model.Menu;

public interface CocuboService {

    Menu getMenu() throws CocuboException;

    void handleNewMenu();
}
