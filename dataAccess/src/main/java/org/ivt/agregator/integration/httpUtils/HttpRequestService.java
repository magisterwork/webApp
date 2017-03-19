package org.ivt.agregator.integration.httpUtils;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.integration.httpUtils.exception.HttpRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class HttpRequestService {

    protected static final Logger LOGGER = Logger.getLogger(HttpRequestService.class.getName());

    /**
     * Сделать http запрос и вернуть ответ в виде строки
     *
     * @param url
     * @return response
     */
    public String doGetRequest(URL url) {
        Validate.notNull(url);

        HttpURLConnection c = null;
        try {
            c = prepareGETConnection(url);
            LOGGER.info("Http get connection " + c.getURL());
            c.connect();
            return processResponse(c);
        } catch (IOException ex) {
            throw new HttpRequestException(ex);
        } finally {
            closeConnection(c);
        }
    }

    public String getRedirectUrl(URL url) {
        Validate.notNull(url);

        HttpURLConnection c = null;
        try {
            c = prepareGETConnection(url);
            c.setInstanceFollowRedirects(false);
            LOGGER.info("Http connection for redirect receiving" + c.getURL());
            c.connect();

            if (302 == c.getResponseCode()) {
                return c.getHeaderField("Location");
            } else {
                throw new HttpRequestException("Response has status " + c.getResponseCode());
            }
        } catch (IOException ex) {
            throw new HttpRequestException(ex);
        } finally {
            closeConnection(c);
        }
    }

    private String processResponse(HttpURLConnection c) throws IOException {
        switch (c.getResponseCode()) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
        }
        throw new HttpRequestException("Response has status " + c.getResponseCode());
    }

    private void closeConnection(HttpURLConnection c) {
        if (c != null) {
            try {
                c.disconnect();
            } catch (Exception ex) {
                LOGGER.log(SEVERE, null, ex);
            }
        }
    }

    private HttpURLConnection prepareGETConnection(URL url) throws IOException {
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");
        c.setUseCaches(false);
        c.setAllowUserInteraction(false);
        return c;
    }
}
