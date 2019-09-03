package be.pekket.cocubo.service.impl;

import be.pekket.cocubo.model.Course;
import be.pekket.cocubo.model.Day;
import be.pekket.cocubo.model.Menu;
import be.pekket.cocubo.service.MenuBuilderService;
import be.pekket.cocubo.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static be.pekket.cocubo.constant.CocuboConstant.CLOSED;
import static be.pekket.cocubo.constant.CocuboConstant.MENU_DELIMITER;

@Service
public class MenuBuilderServiceImpl implements MenuBuilderService {

    @Override
    public Menu build( List<String> menuResult ) {
        Menu menu = null;

        if ( menuResult != null ) {
            menu = new Menu();
            menu.setWeekNumber(TimeUtil.getWeekNumber());

            for ( String dayMenu : menuResult ) {
                Day day;
                if ( dayMenu.contains(CLOSED) )
                    day = new Day(false);
                else {
                    day = new Day(true);

                    String[] courses = StringUtils.delimitedListToStringArray(dayMenu, MENU_DELIMITER);
                    if ( courses != null && courses.length > 3 ) {
                        day.setSoup(new Course(courses[0]));
                        day.setDish(new Course(courses[1]));
                        day.setVegi(new Course(courses[2]));
                        day.setWpp(new Course(courses[3]));
                    }
                }
                menu.addAdd(day);
            }
        }
        return menu;
    }
}
