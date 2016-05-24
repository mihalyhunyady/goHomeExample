package main;

import java.text.ParseException;

import com.epam.hujj.GoHome;

public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("HELLO ECLIPSE");

        final GoHome goHomeApp = new GoHome();
        goHomeApp.printOutMinutesLeftAsync();
        try {
            goHomeApp.printOutMinutesLeft();
        } catch (final ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
