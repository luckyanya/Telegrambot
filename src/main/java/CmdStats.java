import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class CmdStats {
    private Bot bot;
    private SendMessage sender;
    CmdStats(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
        sender = new SendMessage();
    }

    public synchronized void Stats(Long chatId) throws TelegramApiException {
        String[] outstring=new String[100];
        BdSql mysql = new BdSql();
        try {
            sender.setChatId(chatId);
            outstring=mysql.GettableT(chatId.toString());

            for(int z=0;z<outstring.length;z++) {
                sender.setText(outstring[z]);
                bot.execute(sender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}