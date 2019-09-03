package be.pekket.cocubo.service;

import be.pekket.cocubo.model.Menu;

import java.util.List;

public interface MenuBuilderService {

    Menu build( List<String> menuResult );
}
