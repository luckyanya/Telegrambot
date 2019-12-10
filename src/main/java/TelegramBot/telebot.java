package TelegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class telebot {
    public static void main(String[] args) {
        BdSql mysql = new BdSql();
        mysql.InitTable();
        ApiContextInitializer.init(); // начало инициализации
        TelegramBotsApi telegram = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class); // обьявление botOptions для настройки подключения к серверам и взаимодействия с телеграм API
            //botOptions.setProxyHost("178.213.130.101");
            //botOptions.setProxyPort(52658);
            //botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            Bot bot = new Bot(botOptions);
            telegram.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            System.out.println("Error initialization or proxy");
            e.printStackTrace();
        }
    }
}

