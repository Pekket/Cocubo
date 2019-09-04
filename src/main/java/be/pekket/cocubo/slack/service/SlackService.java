package be.pekket.cocubo.slack.service;

public interface SlackService {

    void handleSlackRequest( String responseUrl, String parameter );
}
