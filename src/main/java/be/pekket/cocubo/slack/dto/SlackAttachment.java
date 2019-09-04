package be.pekket.cocubo.slack.dto;

public class SlackAttachment {

    private String text;

    public SlackAttachment( String text ) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
