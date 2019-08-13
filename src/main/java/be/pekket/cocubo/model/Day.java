package be.pekket.cocubo.model;

public class Day {

    private boolean open;
    private Course soup;

    public Day( boolean open, Course soup ) {
        this.open = open;
        this.soup = soup;
    }

    public Day() {
    }

    public boolean isOpen() {
        return open;
    }

    public Course getSoup() {
        return soup;
    }

    public void setOpen( boolean open ) {
        this.open = open;
    }

    public void setSoup( Course soup ) {
        this.soup = soup;
    }
}
