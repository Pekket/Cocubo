package be.pekket.cocubo.slack.service.impl;

import be.pekket.cocubo.exception.CocuboException;
import be.pekket.cocubo.model.Day;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.service.CocuboService;
import be.pekket.cocubo.slack.connector.SlackConnector;
import be.pekket.cocubo.slack.dto.SlackResponse;
import be.pekket.cocubo.slack.service.SlackService;
import be.pekket.cocubo.util.NumberUtil;
import be.pekket.cocubo.util.TimeUtil;
import org.springframework.stereotype.Service;

import static be.pekket.cocubo.constant.CocuboConstant.STEAK_NAME;
import static be.pekket.cocubo.util.MessageUtil.*;
import static be.pekket.cocubo.util.TimeUtil.TODAY;

@Service
public class SlackServiceImpl implements SlackService {
    private final CocuboService cocuboService;
    private final SlackConnector slackConnector;

    public SlackServiceImpl( CocuboService cocuboService, SlackConnector slackConnector ) {
        this.cocuboService = cocuboService;
        this.slackConnector = slackConnector;
    }

    @Override
    public void handleSlackRequest( String responseUrl, String parameter ) {
        SlackResponse slackResponse = null;
        try {
            Menu menu = cocuboService.getMenu();

            if ( menu != null ) {
                if ( parameter != null && parameter.contains(STEAK_NAME) ) {
                    slackResponse = createSteakResponse(menu);
                } else {
                    int dayIndex = getDayIndex(parameter);

                    if ( dayIndex == 5 || dayIndex == 6 ) {
                        slackResponse = new SlackResponse(false, createWeekendMessage());
                    } else {
                        Day day = menu.getDay(dayIndex);
                        String dayStr = TimeUtil.getDay() - 1 == dayIndex ? TODAY : TimeUtil.getDayName(dayIndex);
                        if ( day.isOpen() ) {
                            slackResponse = createMenuSlackResponse(dayStr, day);
                        } else {
                            slackResponse = new SlackResponse(false, createClosedMessage());
                        }
                    }
                }
            } else {
                slackResponse = new SlackResponse(false, createErrorMessage());
            }
        } catch ( CocuboException e ) {
            slackResponse = new SlackResponse(false, createErrorMessage());
        } finally {
            slackConnector.sendResponse(responseUrl, slackResponse);
        }
    }

    private SlackResponse createMenuSlackResponse( String dayStr, Day day ) {
        SlackResponse slackResponse = new SlackResponse(false, createErrorMessage());
        if ( day != null && day.isOpen() && day.isValid() ) {
            slackResponse = new SlackResponse(true, createMenuMessage(dayStr));

            slackResponse.addAttachment(createSoupMessage(day.getSoup().getName()));
            slackResponse.addAttachment(createDishMessage(day.getDish().getName()));
            slackResponse.addAttachment(createVegiMessage(day.getVegi().getName()));
            slackResponse.addAttachment(createWppMessage(day.getWpp().getName()));
        }
        return slackResponse;
    }

    private SlackResponse createSteakResponse( Menu menu ) {
        SlackResponse slackResponse = new SlackResponse(false, createErrorMessage());

        Day day = menu.getDay(2);
        if ( day != null && day.getDish() != null )
            slackResponse = new SlackResponse(true, createSteakMessage(day.getDish().getName()));

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
