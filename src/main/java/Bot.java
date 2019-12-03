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
    private CmdEdit commander3;
    private CmdNewt commander4;
    private CmdStats commander5;
    private CmdNewCat commander6;

    protected Bot(DefaultBotOptions botOptions) {
        //все модули
        super(botOptions);
        this.commander = new CmdStart(this);// обработчик команды /start
        this.commander1 = new CmdHelp(this);// обработчик команды /help
        this.commander2 = new CmdDelete(this);// обработчик команды /delete и /deleteall
        this.commander3 = new CmdEdit(this);// упрощенная отправка сообщений
        this.commander4 = new CmdNewt(this);// упрощенная отправка сообщений
        this.commander5 = new CmdStats(this);// упрощенная отправка сообщений
        this.commander6 = new CmdNewCat(this);// упрощенная отправка сообщений
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        String textCommand = null;
        String UserStatus = null;
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId()); // создаем SendMessage и получаем сообщения и чат id (ID пользователя в данном случае это одно и тоже )
        sendMessage.enableMarkdown(true); // включаем разметку
        String message = update.getMessage().getText();
        //Button button = new Button();
        textCommand = message.split(" ")[0];
        Long chatId = update.getMessage().getChatId();
        //немного отладочной информации
        System.out.println("Id Чата " + " " + update.getMessage().getChatId());
        System.out.println("Имя пользователя " + " " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getText());
        if (!textCommand.equals("/start")) {
            UserStatus = BdSql.GetStatus(chatId.toString());
            System.out.println("Status " + " В цикле" + UserStatus);
            if (UserStatus.split(" ")[0].equals("Tran")) {
                textCommand = "/newt";
                System.out.println("Команда " + " " + textCommand);
            }
            if (UserStatus.split(" ")[0].equals("Cat")) {
                textCommand = "/newcat";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
            if (UserStatus.split(" ")[0].equals("State")) {
                textCommand = "/stats";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
            if (UserStatus.split(" ")[0].equals("Edit")) {
                textCommand = "/edit";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
        }
        try {
            switch (textCommand) {
                case ("/start"): {
                    this.commander.start(chatId);
                    break;
                }
                case ("/newcat"): {
                    this.commander6.СmdNewCat(chatId, message, UserStatus);
                    break;
                }
                case ("/help"): {
                    this.commander1.help(chatId);
                    break;
                }
                case ("/stats"): {
                    this.commander5.Stats(chatId, message, UserStatus);
                    break;
                }
                case ("/deleteAll"): {
                    this.commander2.DeleteAll(chatId);
                    break;
                }
                case ("/newt"): {
                    this.commander4.СmdNewt(chatId, message, UserStatus);
                    break;
                }
                case ("/edit"): {
                    this.commander3.СmdEdit(chatId, message, UserStatus);
                    break;
                }
                default: {
                    sendMessage.setText("Я не знаю, что ответить. Попробуй посмотреть, что я могу в /help");
                    execute(sendMessage);
                }
                break;
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
