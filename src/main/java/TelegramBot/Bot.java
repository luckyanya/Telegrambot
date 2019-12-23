package TelegramBot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private StatusManager commander;

    protected Bot(DefaultBotOptions botOptions) {
        super(botOptions);
        this.commander = new StatusManager(this);// обработчик команды /start
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId()); // создаем SendMessage и получаем сообщения и чат id (ID пользователя в данном случае это одно и тоже )
        sendMessage.enableMarkdown(true); // включаем разметку
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        //немного отладочной информации
        System.out.println("Id Чата " + " " + update.getMessage().getChatId());
        System.out.println("Имя пользователя " + " " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getText());
        this.commander.getCmd(chatId, message);
    }

    @Override
    public String getBotUsername() {
        return "@ByLTBUDGETbot";
    }

    @Override
    public String getBotToken() {
        String TOKEN = System.getenv("TELEGRAM_BOT_T");
        return TOKEN;
    }
}
