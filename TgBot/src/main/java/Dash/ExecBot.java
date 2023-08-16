package Dash;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;


public class ExecBot extends TelegramLongPollingBot {

    //填你自己的token和username
    private String token ="6653008526:AAGUPSEbyKq_3_Xl8-xv_QOMqixdALa3iJE";
    private String username ="WanLaoBot";
    private boolean weather = false;
    private boolean fortune = false;
    private boolean picture = false;

    private InlineKeyboardButton Aries = InlineKeyboardButton.builder()
            .text("白羊座").callbackData("白羊座")
            .build();

    private InlineKeyboardButton Taurus = InlineKeyboardButton.builder()
            .text("金牛座").callbackData("金牛座")
            .build();

    private InlineKeyboardButton  Gemini = InlineKeyboardButton.builder()
            .text("双子座").callbackData("双子座")
            .build();

    private InlineKeyboardButton Cancer = InlineKeyboardButton.builder()
            .text("巨蟹座").callbackData("巨蟹座")
            .build();

    private InlineKeyboardButton Leo = InlineKeyboardButton.builder()
            .text("狮子座").callbackData("狮子座")
            .build();

    private InlineKeyboardButton Virgo = InlineKeyboardButton.builder()
            .text("处女座").callbackData("处女座")
            .build();

    private InlineKeyboardButton Libra = InlineKeyboardButton.builder()
            .text("天秤座").callbackData("天秤座")
            .build();

    private InlineKeyboardButton Scorpio = InlineKeyboardButton.builder()
            .text("天蝎座").callbackData("天蝎座")
            .build();

    private InlineKeyboardButton  Sagittarius = InlineKeyboardButton.builder()
            .text("射手座").callbackData("射手座")
            .build();

    private InlineKeyboardButton  Capricorn = InlineKeyboardButton.builder()
            .text("摩羯座").callbackData("摩羯座")
            .build();

    private InlineKeyboardButton Aquarius = InlineKeyboardButton.builder()
            .text("水瓶座").callbackData("水瓶座")
            .build();

    private InlineKeyboardButton Pisces = InlineKeyboardButton.builder()
            .text("双鱼座").callbackData("双鱼座")
            .build();
    

    private InlineKeyboardMarkup keyboardF = InlineKeyboardMarkup.builder()
            .keyboardRow(Arrays.asList(Aries,Taurus,Gemini,Cancer))
            .keyboardRow(Arrays.asList(Leo,Virgo,Libra,Scorpio))
            .keyboardRow(Arrays.asList(Sagittarius,Capricorn,Aquarius,Pisces))
            .build();

    private InlineKeyboardButton Beauty = InlineKeyboardButton.builder()
            .text("美女").callbackData("meinv")
            .build();

    private InlineKeyboardButton Scenery = InlineKeyboardButton.builder()
            .text("风景").callbackData("fengjing")
            .build();

    private InlineKeyboardButton Cartoon = InlineKeyboardButton.builder()
            .text("动漫").callbackData("dongman")
            .build();

    private InlineKeyboardMarkup keyboardP = InlineKeyboardMarkup.builder()
            .keyboardRow(Arrays.asList(Beauty, Scenery,Cartoon))
            .build();



    public ExecBot() {
        this( new DefaultBotOptions());
    }

    public ExecBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public void onUpdateReceived(Update update) {
            if (update.hasMessage()) {
            User user = update.getMessage().getFrom();
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();
            System.out.println(chatId+" "+user+" "+text);
            if(weather){
                weatherPredict(text,chatId);
                weather = false;
            }

            if (text.equals("/fortune") || text.equals("/fortune@WanLaoBot")){
                sendMenu(chatId, "要查询哪个星座呢？", keyboardF);
                fortune = true;
            }else if(text.equals("/weather") || text.equals("/weather@WanLaoBot")){
                sendText("要查询哪个城市呢？",chatId);
                weather = true;
            }else if(text.equals("/picture") || text.equals("/picture@WanLaoBot")){
                    sendMenu(chatId,"要看哪种类型的图图呢？",keyboardP);
                    picture = true;
            }else if(text.equals("/news") || text.equals("/news@WanLaoBot")){
                    topNews(chatId);
            }

            }
            if(update.hasCallbackQuery()){
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                long chatId = callbackQuery.getMessage().getChatId();
                String qid = callbackQuery.getId();
                System.out.println(callbackQuery);
                if(fortune){
                    fortunePredict(chatId,qid,data);
                    fortune = false;
                }
                if(picture){
                    randomPicture(data,chatId);
                    picture = false;
                }
            }
    }


    //回复普通文本消息
    public void sendText(String text,Long chatId){
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void fortunePredict(Long id, String queryId, String data) {
        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();
        try {
            String report = FortuneUtil.getFortune(data);
            sendText(report,id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            execute(close);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void weatherPredict(String city,Long id){
        try {
            String report = WeatherUtil.getWeather(city);
            sendText(report,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void randomPicture(String kind,Long id){
        try {
            String picurl = ImageUtil.getImage(kind);
            sendText(picurl,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void topNews(Long id){
        try {
            String newsReport = NewsUtil.getNews();
            sendText(newsReport,id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

