package com.example.guido.securityapp.restful;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by guido on 1/17/15.
 */
public abstract class HttpManager {

    protected HttpURLConnection connection;

    public abstract String getData(RequestPackage requestPackage) throws Exception;

    protected void initializeConnection(RequestPackage requestPackage) throws Exception {

        String uri = requestPackage.getUri();

        URL url = new URL(uri);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(this.getMethod());
        Iterator<String> headerKeys = requestPackage.getHeaderKeys();
        while (headerKeys.hasNext()) {
            String key = headerKeys.next();
            connection.setRequestProperty(key, requestPackage.getHeaderProperty(key));
        }

    }

    protected String readData() throws Exception {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (FileNotFoundException e) {
            //if bad request(error 400,401,etc) FileNotFound exception is raised and ErrorStream should
            //be used instead
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        reader.close();

        return sb.toString();

    }

    protected abstract String getMethod();
}



