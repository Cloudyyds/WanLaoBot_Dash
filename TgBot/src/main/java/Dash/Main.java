package Dash;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {

    public static void main(String[] args) {

        //梯子在自己电脑上就写127.0.0.1  软路由就写路由器的地址
        String proxyHost = "127.0.0.1";
        //端口根据实际情况填写，说明在上面，自己看
        int proxyPort = 7890;

        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        //注意一下这里，ProxyType是个枚举，看源码你就知道有NO_PROXY,HTTP,SOCKS4,SOCKS5;
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        DefaultBotSession defaultBotSession = new DefaultBotSession();
        defaultBotSession.setOptions(botOptions);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(defaultBotSession.getClass());
            //需要代理
            ExecBot bot = new ExecBot(botOptions);
            telegramBotsApi.registerBot(bot);
            System.out.println("Dash is listening......");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
