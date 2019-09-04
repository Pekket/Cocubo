package be.pekket.cocubo.service.impl;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.exception.ConnectorException;
import be.pekket.cocubo.repository.MenuConnector;
import be.pekket.cocubo.service.MenuService;
import be.pekket.cocubo.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);
    private final MenuConnector menuConnector;

    public MenuServiceImpl( MenuConnector menuConnector ) {
        this.menuConnector = menuConnector;
    }

    @Override
    public void fetch() throws CocuboException {
//        int year = TimeUtil.getYear();
//        int weekNumber = TimeUtil.getWeekNumber();
//        int monthNumber = TimeUtil.getMonth();
//        String month = TimeUtil.getMonthName();

        try {
            String url = menuConnector.getMenuUrl();
            //String.format(MENU_URL, year, doubleDiggit(monthNumber - 2), year, doubleDiggit(monthNumber), month, doubleDiggit(weekNumber));

            if ( url != null && url.contains(TimeUtil.getWeekNumber() + "") ) {
                LOG.info("Created url {}", url);
                menuConnector.getMenu(url);
            }
        } catch ( ConnectorException e ) {
            throw new CocuboException(e.getMessage());
        }
    }
}
