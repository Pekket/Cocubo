package be.pekket.cocubo.repository.impl;

import be.pekket.cocubo.exception.ConnectorException;
import be.pekket.cocubo.repository.MenuConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static be.pekket.cocubo.constant.CocuboConstant.MENU_IMAGE_NAME;

@Repository
public class MenuConnectorImpl implements MenuConnector {
    private static final Logger LOG = LoggerFactory.getLogger(MenuConnectorImpl.class);

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
