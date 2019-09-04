package be.pekket.cocubo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

public class Day {

    private boolean open;
    private Course soup;
    private Course dish;
    private Course vegi;
    private Course wpp;

    public Day( boolean open ) {
        this.open = open;
    }

    public Day() {
    }

    public boolean isOpen() {
        return open;
    }

    public Course getSoup() {
        return soup;
    }

    public Course getDish() {
        return dish;
    }

    public Course getVegi() {
        return vegi;
    }

    public Course getWpp() {
        return wpp;
    }

    public void setOpen( boolean open ) {
        this.open = open;
    }

    public void setSoup( Course soup ) {
        this.soup = soup;
    }

    public void setDish( Course dish ) {
        this.dish = dish;
    }

    public void setVegi( Course vegi ) {
        this.vegi = vegi;
    }

    public void setWpp( Course wpp ) {
        this.wpp = wpp;
    }

    @JsonIgnore
    public boolean isValid() {
        return !StringUtils.isEmpty(soup.getName())
                && !StringUtils.isEmpty(dish.getName())
                && !StringUtils.isEmpty(vegi.getName())
                && !StringUtils.isEmpty(wpp.getName());
    }
}
