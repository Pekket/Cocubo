package be.pekket.cocubo.service.impl;

import be.pekket.cocubo.dto.SlackResponse;
import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.model.Course;
import be.pekket.cocubo.model.Day;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.service.CocuboService;
import be.pekket.cocubo.service.SlackService;
import be.pekket.cocubo.util.NumberUtil;
import be.pekket.cocubo.util.TimeUtil;
import org.springframework.stereotype.Service;

import static be.pekket.cocubo.util.MessageUtil.*;
import static be.pekket.cocubo.util.TimeUtil.TODAY;

@Service
public class SlackServiceImpl implements SlackService {

    private final CocuboService cocuboService;

    public SlackServiceImpl( CocuboService cocuboService ) {
        this.cocuboService = cocuboService;
    }

    @Override
    public SlackResponse handleSlackRequest( String parameter ) {
        SlackResponse slackResponse;
        try {
            Menu menu = cocuboService.getMenu();

            if ( menu != null ) {
                int dayIndex = getDayIndex(parameter);
                if ( dayIndex == 5 || dayIndex == 6 ) {
                    slackResponse = new SlackResponse(createWeekendMessage());
                } else {
                    Day day = menu.getDay(dayIndex);
                    String dayStr = TimeUtil.getDay() - 1 == dayIndex ? TODAY : TimeUtil.getDayName(dayIndex);
                    if ( day.isOpen() ) {
                        Course soup = day.getSoup();
                        slackResponse = new SlackResponse(createSoupMessage(dayStr, soup.getName()));
                    } else {
                        slackResponse = new SlackResponse(createClosedMessage());
                    }
                }

            } else {
                slackResponse = new SlackResponse(createErrorMessage());
            }
        } catch ( CocuboException e ) {
            slackResponse = new SlackResponse(createErrorMessage());
        }
        return slackResponse;
    }

    private int getDayIndex( String parameter ) {
        int dayIndex = TimeUtil.getDay() - 1;
        if ( NumberUtil.isNumeric(parameter) ) {
            int parseInt = Integer.parseInt(parameter) - 1;
            if ( parseInt >= 0 && parseInt <= 6 ) {
                dayIndex = parseInt;
            }
        }
        return dayIndex;
    }
}
