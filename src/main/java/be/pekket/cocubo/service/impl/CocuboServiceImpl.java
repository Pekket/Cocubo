package be.pekket.cocubo.service.impl;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.exception.ImageProcessingException;
import be.pekket.cocubo.image.processor.ImageProcessor;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.repository.JsonDataRepository;
import be.pekket.cocubo.service.CocuboService;
import be.pekket.cocubo.service.MenuBuilderService;
import be.pekket.cocubo.service.MenuService;
import be.pekket.cocubo.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.pekket.cocubo.constant.CocuboConstant.MENU_IMAGE_NAME;

@Service
public class CocuboServiceImpl implements CocuboService {
    private static final Logger LOG = LoggerFactory.getLogger(CocuboServiceImpl.class);

    private final ImageProcessor imageProcessor;
    private final MenuService menuService;
    private final MenuBuilderService menuBuilderService;
    private final JsonDataRepository jsonDataRepository;

    public CocuboServiceImpl( ImageProcessor imageProcessor, MenuService menuService, MenuBuilderService menuBuilderService, JsonDataRepository jsonDataRepository ) {
        this.imageProcessor = imageProcessor;
        this.menuService = menuService;
        this.menuBuilderService = menuBuilderService;
        this.jsonDataRepository = jsonDataRepository;
    }

    @Override
    public Menu getMenu() throws CocuboException {
        Menu menu = jsonDataRepository.fetchData();
        int currentWeekNumber = TimeUtil.getWeekNumber();

        if ( menu == null ) {
            LOG.info("Starting new thread to fetch menu");
            new Thread(this::handleNewMenu).start();
        } else if ( currentWeekNumber != menu.getWeekNumber() ) {
            LOG.info("Old menu still exists, fetching new one");
            menu = null;
            removeMenu();
            new Thread(this::handleNewMenu).start();
        }

        return menu;
    }

    //Every monday at 9h30
    @Scheduled(cron = "0 30 9 ? * MON")
    @Override
    public void handleNewMenu() {
        LOG.debug("About to handle new menu");

        try {
            menuService.fetch();

            List<String> menuResults = imageProcessor.process(MENU_IMAGE_NAME);
            LOG.debug("Found {} menu results", menuResults.size());

            Menu menu = menuBuilderService.build(menuResults);
            LOG.debug("Build menu for week {}", menu.getWeekNumber());

            jsonDataRepository.storeData(menu);
            LOG.debug("Successfully handled new menu");
        } catch ( ImageProcessingException | CocuboException e ) {
            LOG.error("Error trying to fetch new menu {}", e.getMessage());
        }
    }

    //Every sunday at 23h55
    @Scheduled(cron = "0 55 23 ? * SUN")
    public void removeMenu() {
        LOG.debug("About to remove menu json");
        jsonDataRepository.removeData();
    }
}
