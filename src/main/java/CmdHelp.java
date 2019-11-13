import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class CmdHelp
{
    private Bot bot;
    private SendMessage sender;
    CmdHelp(Bot bot)
    {
        this.bot = bot;
        sender = new SendMessage();
    }
    public synchronized void help(Long chatId) throws TelegramApiException
    {// вывод help информации
        sender.setChatId(chatId);
        sender.setText("Я еще в разработке, но совсем скоро я смогу многое!");
        bot.execute(sender);
        sender.setText("Я могу записывать и выводить твои расходы.");
        bot.execute(sender);
        sender.setText("Мои команды:");
        bot.execute(sender);
        sender.setText("Чтобы записать транзакцию, введи /newt потраченная сумма_категория_где потратил_назначение");
        bot.execute(sender);
        sender.setText("Пример /newt 100500_Развлечение_Cinemapark_Просмотр фильма");
        bot.execute(sender);
        sender.setText("Чтобы вывести свою статистику, введите /stats (пока не доделано, выводит все транзакции)");
        bot.execute(sender);
        sender.setText("Чтобы вывести свою статистику за день, введите /stats_day");
        bot.execute(sender);
        sender.setText("Чтобы вывести свою статистику за выбранный период  /stats_date_10.10.10_to_11.10.10");
        bot.execute(sender);
        sender.setText("Чтобы удалить свою информацию введите /deleteAll *при вводе происходит полное и безвозвратное удаление ваших данных. Чтобы начать пользоваться ботом после этих действий, нужно ввести команду /start ");
        bot.execute(sender);
    }
}