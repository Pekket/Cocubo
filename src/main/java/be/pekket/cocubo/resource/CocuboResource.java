package be.pekket.cocubo.resource;

import be.pekket.cocubo.service.CocuboService;
import be.pekket.cocubo.slack.service.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cocubo")
public class CocuboResource {
    private static final Logger LOG = LoggerFactory.getLogger(CocuboResource.class);

    private final SlackService slackService;
    private final CocuboService cocuboService;

    @Autowired
    public CocuboResource( SlackService slackService, CocuboService cocuboService ) {
        this.slackService = slackService;
        this.cocuboService = cocuboService;
    }

    @PostMapping(
            value = "/slack",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleSlack(
            @RequestParam(name = "response_url", required = false) String responseUrl,
            @RequestParam(name = "team_domain", required = false) String domain,
            @RequestParam(name = "channel_name", required = false) String channel,
            @RequestParam(name = "user_name", required = false) String user,
            @RequestParam(name = "text", required = false) String parameter ) {
        LOG.debug("Received slack post request with response url {} and parameter {}", responseUrl, parameter);
        LOG.info("Slack request: domain [{}], channel [{}], user [{}]", domain, channel, user);
        slackService.handleSlackRequest(responseUrl, parameter);
    }

    @GetMapping(value = "/new")
    public void handleNew() {
        cocuboService.handleNewMenu();
    }
}
