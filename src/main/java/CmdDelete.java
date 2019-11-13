import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CmdDelete
{
    private Bot bot;
    private SendMessage sender;
    CmdDelete(Bot bot)
    {
        this.bot = bot;
        sender = new SendMessage();
    }
    public void DeleteAll(Long ids)
    {
        BdSql mysql = new BdSql();
        mysql.DeleteTableA(ids.toString());
        sender.setChatId(ids);
        sender.setText("Я все-все удалил! Пока, дорогой друг!");
        try {
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}