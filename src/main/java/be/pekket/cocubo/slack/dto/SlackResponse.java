package be.pekket.cocubo.slack.dto;

import be.pekket.cocubo.dto.CocuboResponse;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class SlackResponse extends CocuboResponse {

    private String RESPONSE_TYPE_EPHEMERAL = "ephemeral";
    private String RESPONSE_TYPE_IN_CHANNEL = "in_channel";

    private boolean inChannel;
    private String text;
    private List<SlackAttachment> attachments = new LinkedList<>();

    public SlackResponse(boolean inChannel, String text ) {
        this.inChannel = inChannel;
        this.text = text;
    }

    public String getResponse_type() {
        return this.inChannel ? RESPONSE_TYPE_IN_CHANNEL : RESPONSE_TYPE_EPHEMERAL;
    }

    public String getText() {
        return text;
    }

    public List<SlackAttachment> getAttachments() {
        return attachments;
    }

    public void addAttachment( String attachmentString ) {
        if ( !StringUtils.isEmpty(attachmentString) ) {
            attachments.add(new SlackAttachment(attachmentString));
        }
    }
}
