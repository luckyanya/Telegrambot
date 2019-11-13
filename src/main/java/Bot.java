import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    //быстрый доступ к командам А.К. модули
    private CmdStart commander;
    private CmdHelp commander1;
    private CmdDelete commander2;
    private SendToUser commander3;
    private CmdNewt commander4;
    private CmdStats commander5;

    protected Bot(DefaultBotOptions botOptions) {
        //все модули
        super(botOptions);
        this.commander = new CmdStart(this);// обработчик команды /start
        this.commander1 = new CmdHelp(this);// обработчик команды /help
        this.commander2 = new CmdDelete(this);// обработчик команды /delete и /deleteall
        this.commander3 = new SendToUser(this);// упрощенная отправка сообщений
        this.commander4 = new CmdNewt(this);// упрощенная отправка сообщений
        this.commander5 = new CmdStats(this);// упрощенная отправка сообщений
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        String[] cmdm = null;
        String cmd = null;
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId()); // создаем SendMessage и получаем сообщения и чат id (ID пользователя в данном случае это одно и тоже )
        sendMessage.enableMarkdown(true); // включаем разметку
        boolean understend = false; // хранить состояние бота
        String message = update.getMessage().getText();
        Button button = new Button();
        cmdm = message.split(" ");
        cmd = cmdm[0];
        int start = cmd.length();
        int fin = message.length();
        fin = fin - start;
        char[] stringed = new char[fin];

        Long chatId = update.getMessage().getChatId();
        //немного отладочной информации
        System.out.println("Id Чата " + " " + update.getMessage().getChatId());
        System.out.println("Имя пользователя " + " " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getText());
        message.getChars(cmd.length(), message.length(), stringed, 0);
        try {
            switch (cmd) {
                case ("/start"): {
                    this.commander.start(chatId);
                    understend = true;
                    break;
                }
                case ("/help"): {
                    this.commander1.help(chatId);
                    understend = true;
                    break;
                }
                case ("/stats"): {
                    this.commander5.Stats(chatId);
                    understend = true;
                    break;
                }
                case ("/deleteAll"): {
                    this.commander2.DeleteAll(chatId);
                    understend = true;
                    break;
                }
                case ("/newt"): {
                    this.commander4.СmdNewt(chatId, new String(stringed));
                    understend = true;
                    break;
                }
            }
            if (understend == false) {
                this.commander3.sendToUser(chatId, "Я не знаю, что ответить. Посмотри, что я могу в /help");
            } else {
                understend = false;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "@ByLTBUDGETbot";
    }

    @Override
    public String getBotToken() {
        String TOKEN = System.getenv("TELEGRAM_BOT_T");
        return TOKEN;
    }
}
