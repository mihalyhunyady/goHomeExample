package com.epam.hujj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class GoHome {

    Properties prop;
    final SimpleDateFormat timeFormat;
    final SimpleDateFormat dateTimeFormat;

    public GoHome() {
        try {
            prop = readProperty();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        timeFormat = new SimpleDateFormat(prop.getProperty("time-format"));
        dateTimeFormat = new SimpleDateFormat(prop.getProperty("date-time-format"));
    }

    public void printOutMinutesLeft() throws ParseException {
        // final MessageFormat msgFormat = new MessageFormat(pattern);

        final Date actualDate = new Date();

        final String actualTime = dateTimeFormat.format(actualDate.getTime());
        final String finishTime = actualTime.split("\\s")[0] + " 17:00";

        final Date finishDate = dateTimeFormat.parse(finishTime);
        final long difference = finishDate.getTime() - actualDate.getTime();
        System.out.println("Time left:" + timeFormat.format(difference));

    }

    public void printOutMinutesLeftAsync() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    printOutMinutesLeft();
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Properties readProperty() throws IOException {
        final Properties prop = new Properties();
        final String propFileName = "config.properties";
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        return prop;
    }
}
