package main;

import bot.MyBot;

import static util.Util.getDataProperties;

public class App {
    public static void main(String[] args) throws Exception {
        new MyBot(getDataProperties("bot.username"), getDataProperties("bot.token"));
    }
}
