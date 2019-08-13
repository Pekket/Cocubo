package be.pekket.cocubo.service.impl;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.exception.ImageProcessingException;
import be.pekket.cocubo.image.processor.ImageProcessor;
import be.pekket.cocubo.model.Course;
import be.pekket.cocubo.model.Day;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.repository.JsonDataRepository;
import be.pekket.cocubo.service.CocuboService;
import be.pekket.cocubo.service.MenuService;
import be.pekket.cocubo.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.pekket.cocubo.constant.CocuboConstant.CLOSED;
import static be.pekket.cocubo.constant.CocuboConstant.MENU_IMAGE_NAME;

@Service
public class CocuboServiceImpl implements CocuboService {
    private static final Logger LOG = LoggerFactory.getLogger(CocuboServiceImpl.class);

    private final ImageProcessor imageProcessor;
    private final MenuService menuService;
    private final JsonDataRepository jsonDataRepository;

    public CocuboServiceImpl( ImageProcessor imageProcessor, MenuService menuService, JsonDataRepository jsonDataRepository ) {
        this.imageProcessor = imageProcessor;
        this.menuService = menuService;
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
            removeJsonFile();
            new Thread(this::handleNewMenu).start();
        }

        return menu;
    }

    //Every monday at 9h30
    @Scheduled(cron = "0 30 9 ? * MON")
    public void handleNewMenu() {
        LOG.debug("About to handle new menu");

        try {
            menuService.fetch();
            List<String> menuResults = imageProcessor.process(MENU_IMAGE_NAME);
            LOG.debug("Found {} menu results", menuResults.size());

            Menu menu = new Menu();
            menu.setWeekNumber(TimeUtil.getWeekNumber());
            for ( String course : menuResults ) {
                Day day;
                if ( course.contains(CLOSED) )
                    day = new Day(false, null);
                else
                    day = new Day(true, new Course(course));
                LOG.debug("About to add soup {}", day.getSoup());
                menu.addAdd(day);
            }
            jsonDataRepository.storeData(menu);
        } catch ( ImageProcessingException | CocuboException e ) {
            LOG.error("Error trying to fetch new menu {}", e.getMessage());
        }
    }

    //Every friday at 22h00
    @Scheduled(cron = "0 0 22 ? * FRI")
    public void removeJsonFile() {
        LOG.debug("About to remove menu json");
        jsonDataRepository.removeData();
    }
}
