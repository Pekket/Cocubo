package be.pekket.cocubo.dto;

import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class SlackResponse extends CocuboResponse {

    private String response_type = "in_channel";
    private String text;
    private List<Attachment> attachments = new LinkedList<>();

    public SlackResponse( String text ) {
        this.text = text;
    }

    public String getResponse_type() {
        return response_type;
    }

    public String getText() {
        return text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachment( String attachmentString ) {
        if ( !StringUtils.isEmpty(attachmentString) ) {
            attachments.add(new Attachment(attachmentString));
        }
    }
}
