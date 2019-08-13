package be.pekket.cocubo.resource;

import be.pekket.cocubo.dto.SlackResponse;
import be.pekket.cocubo.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cocubo")
public class CocuboResource {

    private final SlackService slackService;

    @Autowired
    public CocuboResource( SlackService slackService ) {
        this.slackService = slackService;
    }

    @PostMapping(
            value = "/slack",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SlackResponse handleSlack( @RequestParam(name = "text", required = false) String parameter ) {
        return slackService.handleSlackRequest(parameter);
    }
}
