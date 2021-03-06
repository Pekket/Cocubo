package be.pekket.cocubo.resource;

import be.pekket.cocubo.slack.service.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cocubo")
public class CocuboResource {
    private static final Logger LOG = LoggerFactory.getLogger(CocuboResource.class);

    private final SlackService slackService;

    @Autowired
    public CocuboResource( SlackService slackService ) {
        this.slackService = slackService;
    }

    @PostMapping(
            value = "/slack",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleSlack(
            @RequestParam(name = "response_url", required = false) String responseUrl,
            @RequestParam(name = "text", required = false) String parameter ) {
        LOG.debug("Received slack post request with response url {} and parameter {}", responseUrl, parameter);
        slackService.handleSlackRequest(responseUrl, parameter);
    }
}
