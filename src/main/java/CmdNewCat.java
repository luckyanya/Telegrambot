import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

final class CmdNewCat {
    private Bot bot;
    private SendMessage sender;

    CmdNewCat(Bot bot) {
        this.bot = bot;

        sender = new SendMessage();
    }

    public void СmdNewt(Long ids, String mes) {
        String[] cmdm = null;
        String cmd = null;
        cmdm = mes.split("_");
        String catName = cmdm[0];
        Float sum = Float.parseFloat(cmdm[1]);

        BdSql mysql = new BdSql();
        mysql.NewCategory(ids.toString(), catName, sum);
        sender.setChatId(ids);
        sender.setText("Вы создали категорию " + catName + " c лимитом " + sum);
        try {
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
