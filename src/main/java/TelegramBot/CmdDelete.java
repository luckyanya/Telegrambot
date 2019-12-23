package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

final class CmdDelete {
    private final Bot bot;
    private final SendMessage sender;

    CmdDelete(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public void deleteAll(Long ids) {
        BdSql mysql = new BdSql();
        mysql.deleteTable(ids.toString());
        sender.setChatId(ids);
        sender.setText("Я все-все удалил! Пока, дорогой друг!");
        try {
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
