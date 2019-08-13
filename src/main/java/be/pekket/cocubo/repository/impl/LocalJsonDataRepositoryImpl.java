package be.pekket.cocubo.repository.impl;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.repository.JsonDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

import static be.pekket.cocubo.constant.CocuboConstant.MENU_JSON_FILE;

@Repository
public class LocalJsonDataRepositoryImpl implements JsonDataRepository {
    private static final Logger LOG = LoggerFactory.getLogger(LocalJsonDataRepositoryImpl.class);

    @Override
    public Menu fetchData() throws CocuboException {
        LOG.debug("About to fetch menu from json file");
        Menu menu = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            File menuFile = new File(MENU_JSON_FILE);
            if ( menuFile.exists() ) {
                menu = mapper.readValue(menuFile, Menu.class);
                LOG.debug("Successfully fetched menu data for week {}", menu.getWeekNumber());
            }
        } catch ( IOException e ) {
            throw new CocuboException("Error trying to fetch and map json to object " + e.getMessage());
        }
        return menu;
    }

    @Override
    public void storeData( Menu menu ) throws CocuboException {
        LOG.debug("About to save menu to json file for week {}", menu.getWeekNumber());

        try {
            ObjectMapper mapper = new ObjectMapper();
            File menuFile = new File(MENU_JSON_FILE);
            mapper.writeValue(menuFile, menu);
            LOG.debug("Successfully saved {}", MENU_JSON_FILE);

        } catch ( IOException e ) {
            throw new CocuboException("Error trying to map and save to json " + e.getMessage());
        }
    }

    @Override
    public void removeData() {
        LOG.debug("About to delete menu json data");

        File menuFile = new File(MENU_JSON_FILE);
        if ( menuFile.delete() ) {
            LOG.info("Successfully deleted menu file");
        }
    }
}
