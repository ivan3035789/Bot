package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import util.Util;

public class MyBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=TU7FSf0Q2d0djKtF7kDoW0rHbPjIbC5Y9AkFJdBU";

    public MyBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String answer = update.getMessage().getText();
            String[] answerSeparate = answer.split(" ");
            String action = answerSeparate[0];
            switch (action) {
                case "/help":
                    sendMessage("Я бот, я присылаю картинку дня. \n" +
                            "Мои команды \n" +
                            "/hello \n" +
                            "/start \n" +
                            "/image \n" +
                            "/date и интересующую дату в формате гггг-мм-дд", chatId);
                    break;
                case "/hello":
                    sendMessage("Привет, я бот Ивана", chatId);
                    break;
                case "/start":
                    sendMessage("Привет я бот, я присылаю картинки NASA", chatId);
                    break;
                case "/image":
                    String image = Util.getUrlNasa(URL);
                    sendMessage(image, chatId);
                    break;
                case "/date":
                    image = Util.getUrlNasa(URL + "&date=" + answerSeparate[1]);
                    sendMessage(image, chatId);
                    break;
                default:
                    sendMessage("Я не знаю такой каманды", chatId);
                    break;
            }
        }
    }

    void sendMessage(String text, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            System.out.println("Произошла ошибка");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
