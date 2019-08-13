package be.pekket.cocubo.service;

import be.pekket.cocubo.dto.SlackResponse;

public interface SlackService {

    SlackResponse handleSlackRequest( String parameter );
}
