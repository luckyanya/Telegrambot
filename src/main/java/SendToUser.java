import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendToUser {
    private Bot bot;
    private SendMessage sender;

    SendToUser(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public void sendToUser(Long chatId, String txt) throws TelegramApiException { // получаем чат и сообщение, которое нужно вывести.
        sender.setChatId(chatId);
        sender.setText(txt);
        bot.execute(sender);
    }
}
