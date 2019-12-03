import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CmdStart {
    private final Bot bot;
    private SendMessage sender;

    CmdStart(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
        sender = new SendMessage();
    }

    public synchronized void start(Long chatId) throws TelegramApiException {
        Button(sender, chatId);
        BdSql mysql = new BdSql();
        mysql.CreateTable(chatId.toString());
        sender.setChatId(chatId);
        sender.setText("Курсовая работа по разработке телеграм-бота \n  Работу выполнила \n Git: \n Версия 0.0.1 \n Для ознакомления c возможностями введите \n  ");
        bot.execute(sender);
    }

    public void Button(SendMessage sendMessage, Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/stats"));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("/help"));
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("/newt"));
        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(new KeyboardButton("/newCat"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}