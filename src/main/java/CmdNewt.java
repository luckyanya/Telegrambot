import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CmdNewt {
    private final Bot bot;
    private final SendMessage sender;

    CmdNewt(Bot bot) {
        this.bot = bot;

        sender = new SendMessage();
    }

    public void СmdNewt(Long ids, String mes) {
        String[] cmdm = null;
        String cmd = null;
        cmdm = mes.split("_");
        BdSql mysql = new BdSql();
        mysql.NewTran(ids.toString(), cmdm[1], cmdm[2], cmdm[3], Float.parseFloat(cmdm[0]));
        sender.setChatId(ids);
        sender.setText("Я все записал!");
        try {
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}