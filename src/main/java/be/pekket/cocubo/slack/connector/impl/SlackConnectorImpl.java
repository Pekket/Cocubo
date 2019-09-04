package be.pekket.cocubo.slack.connector.impl;

import be.pekket.cocubo.slack.dto.SlackResponse;
import be.pekket.cocubo.slack.connector.SlackConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
public class SlackConnectorImpl implements SlackConnector {
    private static final Logger LOG = LoggerFactory.getLogger(SlackConnectorImpl.class);

    public void sendResponse( String responseUrl, SlackResponse slackResponse ) {

        try ( final CloseableHttpClient client = HttpClients.createDefault() ) {
            HttpPost httpPost = new HttpPost(responseUrl);
            httpPost.addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            final String requestBody = new ObjectMapper().writeValueAsString(slackResponse);
            LOG.debug("Slack response request body: {}", requestBody);

            httpPost.setEntity(new StringEntity(requestBody));
            client.execute(httpPost);
        } catch ( IOException e ) {
            LOG.error("Error sending slack response to {}", responseUrl);
        }
    }
}
