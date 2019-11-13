import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class CmdStart
{
    private Bot bot;
    private SendMessage sender;
    CmdStart(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
        sender = new SendMessage();
    }
    public synchronized void start(Long chatId) throws TelegramApiException
    {
        Button button = new Button();
        button.Button(sender,chatId); // кнопки к сообщению
        UserLogW userlogW = new UserLogW();// пользователи
        userlogW.write(chatId); // проверим, есть ли пользователь в бд, а если нет - запишем
        sender.setChatId(chatId);
        sender.setText("Курсовая работа по разработке телеграм-бота");
        bot.execute(sender);
        sender.setText("Работу выполнил: Лакеева А.И, Толстова В.Э.");
        bot.execute(sender);
        sender.setText("Git:");
        bot.execute(sender);
        sender.setText("Версия 0.0.1");
        bot.execute(sender);
        sender.setText("Для ознакомления c возможностями введите:  ");
        bot.execute(sender);
    }
}