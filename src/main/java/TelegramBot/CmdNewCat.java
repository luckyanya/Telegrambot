package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

final class CmdNewCat {
    private Bot bot;
    private SendMessage sender;

    CmdNewCat(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    Map<String, String> states = new HashMap<String, String>();

    public void сmdNewCat(Long ids, String mes, String UserStatus) {
        sender.setChatId(ids);
        try {
            switch (UserStatus) {
                case ("Main"):
                    sender.setText("Так, давай начнем. \nВведите имя категории");
                    BdSql.changeStatus(ids.toString(), "Cat 1");
                    break;
                case ("Cat 1"):
                    //Записать сумму траты
                    states.put(ids.toString() + "CatName", mes);
                    BdSql.changeStatus(ids.toString(), "Cat 2");
                    sender.setText("Какой лимит?");
                    break;
                case ("Cat 2"):
                    if (CmdNewT.isOnlyDigits(mes)) {
                        states.put(ids.toString() + "CatLimit", mes);
                        BdSql.changeStatus(ids.toString(), "Main");
                        BdSql.newCategory(ids.toString(), states.get(ids.toString() + "CatName"), Float.parseFloat(states.get(ids.toString() + "CatLimit")));
                        sender.setText("Вы создали категорию " + states.get(ids.toString() + "CatName") + "\nC лимитом в " + Float.parseFloat(states.get(ids.toString() + "CatLimit")));
                    } else {
                        sender.setText("Нужны цифры. Eще разок?");
                    }
                    break;
                case ("Cat 1 Tran 3"):
                    //Записать сумму траты
                    states.put(ids.toString() + "CatName", mes);
                    BdSql.changeStatus(ids.toString(), "Cat 2 Tran 3");
                    sender.setText("Какой лимит");
                    break;
                case ("Cat 2 Tran 3"):
                    if (CmdNewT.isOnlyDigits(mes)) {
                        states.put(ids.toString() + "CatLimit", mes);
                        CmdNewT.states.put(ids.toString() + "Number", "" + 2 + "");
                        BdSql.changeStatus(ids.toString(), "Tran 4");
                        BdSql.newCategory(ids.toString(), states.get(ids.toString() + "CatName"), Float.parseFloat(states.get(ids.toString() + "CatLimit")));
                        sender.setText("Вы создали категорию " + states.get(ids.toString() + "CatName") + "\nC лимитом в " + Float.parseFloat(states.get(ids.toString() + "CatLimit")) + "\nЧто ж, продолжим, осталось выбрать нашу категорию, для этого введи '1'");
                    } else {
                        sender.setText("Нужны цифры. Eще раз попробуем?");
                    }
                    break;
            }
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
