package com.epam.hujj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GoHome {

    Properties prop;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dateTimeFormat;
    Thread t;
    @FXML
    Label timeLabel;
    boolean running = false;

    public GoHome() {
        load();
    }
    private void load() {
        try {
            prop = readProperty();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        timeFormat = new SimpleDateFormat(prop.getProperty("time-format"));
        dateTimeFormat = new SimpleDateFormat(prop.getProperty("date-time-format"));
    }

    public void printOutMinutesLeft() throws ParseException {
        final Date actualDate = new Date();
        final String actualTime = dateTimeFormat.format(actualDate.getTime());
        final String finishTime = actualTime.split("\\s")[0] + " " + prop.getProperty("time-to-leave");
        final Date finishDate = dateTimeFormat.parse(finishTime);
        final int timeZoneCorrection = 1000 * 60 * 60;
        final long difference = finishDate.getTime() - actualDate.getTime() - timeZoneCorrection;
        Platform.runLater(() -> {
            timeLabel.setText("Time left: " + timeFormat.format(difference));
        });
    }

    public void printOutMinutesLeftAsync() {
        t = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                    printOutMinutesLeft();
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
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

    @FXML
    public void stopTimer() {
        running = false;
    }

    @FXML
    public void startTimer() {

        running = true;
        printOutMinutesLeftAsync();
    }

    public void stop() {
        stopTimer();
        if (t != null) {
            t.interrupt();
        }
    }
}
