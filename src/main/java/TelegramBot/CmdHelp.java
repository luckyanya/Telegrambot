package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

final class CmdHelp {
    private final Bot bot;
    private final SendMessage sender;

    CmdHelp(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public void help(Long chatId) throws TelegramApiException {// вывод help информации
        sender.setChatId(chatId);
        sender.setText("Я еще в разработке, но совсем скоро я смогу многое!");
        bot.execute(sender);
        sender.setText("Я могу записывать и выводить расходы.");
        bot.execute(sender);
        sender.setText("Мои команды:");
        bot.execute(sender);
        sender.setText("Чтобы записать транзакцию, введите /newt");
        bot.execute(sender);
        sender.setText("Чтобы добавить категорию, введите /newcat");
        bot.execute(sender);
        sender.setText("Чтобы изменить категорию или транзакцию, введите /edit");
        bot.execute(sender);
        sender.setText("Чтобы вывести свою статистику, введите /stats");
        bot.execute(sender);
        sender.setText("Чтобы удалить свою информацию введите /deleteAll *при вводе происходит полное и безвозвратное удаление ваших данных. Чтобы начать пользоваться ботом после этих действий, нужно ввести команду /start ");
        bot.execute(sender);
    }
}