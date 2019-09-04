package be.pekket.cocubo.repository.impl;

import be.pekket.cocubo.exception.ConnectorException;
import be.pekket.cocubo.repository.MenuConnector;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static be.pekket.cocubo.constant.CocuboConstant.*;

@Repository
public class MenuConnectorImpl implements MenuConnector {
    private static final Logger LOG = LoggerFactory.getLogger(MenuConnectorImpl.class);

    @Override
    public String getMenuUrl() throws ConnectorException {
        String url = null;
        try ( final WebClient webClient = new WebClient() ) {
            LOG.debug("Searching menu url on {}", CORDA_CUISINE_URL);
            final HtmlPage page = webClient.getPage(CORDA_CUISINE_URL);
            NodeList images = page.getElementsByTagName("img");

            LOG.debug("Found {} images on page", images.getLength());
            for ( int i = 0; i < images.getLength(); i++ ) {
                Node image = images.item(i);
                if ( image != null ) {
                    Node srcNode = image.getAttributes().getNamedItem("src");
                    if ( srcNode != null && srcNode.getTextContent().contains(MENU_URL_WORD) ) {
                        url = srcNode.getTextContent();
                        LOG.debug("Found menu url on page {}", url);
                        break;
                    }
                }
            }
        } catch ( IOException e ) {
            throw new ConnectorException("Error trying to get menu url " + e.getMessage());
        }

        return url;
    }

    @Override
    public void getMenu( String url ) throws ConnectorException {

        try {
            LOG.debug("About to download new menu image from {}", url);
            URL website = new URL(url);
            try ( InputStream in = website.openStream() ) {
                Files.copy(in, Paths.get(MENU_IMAGE_NAME), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                LOG.debug("Successfully downloaded new menu image");
            }
        } catch ( IOException e ) {
            throw new ConnectorException("Error trying to get menu image " + e.getMessage());
        }
    }
}