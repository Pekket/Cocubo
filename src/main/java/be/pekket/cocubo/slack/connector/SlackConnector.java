package be.pekket.cocubo.slack.connector;

import be.pekket.cocubo.slack.dto.SlackResponse;

public interface SlackConnector {
    void sendResponse( String responseUrl, SlackResponse slackResponse );
}
