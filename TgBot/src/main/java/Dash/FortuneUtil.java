package Dash;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FortuneUtil {

    private static final String KEY = "2647bfe97d1f0106c93972a9e2a3672a";
    private static final String URL = "http://web.juhe.cn/constellation/getAll";

    public static String getFortune(String constellation) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // 获取今日运势
        Request todayRequest = new Request.Builder()
                .url(URL + "?key=" + KEY + "&consName=" + constellation + "&type=today")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response todayResponse = client.newCall(todayRequest).execute();
        JSONObject todayResult = JSONObject.parseObject(todayResponse.body().string());
        String todayFortune = parseFortune(todayResult, "今日运势");

        // 获取明日运势
        Request tomorrowRequest = new Request.Builder()
                .url(URL + "?key=" + KEY + "&consName=" + constellation + "&type=tomorrow")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response tomorrowResponse = client.newCall(tomorrowRequest).execute();
        JSONObject tomorrowResult = JSONObject.parseObject(tomorrowResponse.body().string());
        String tomorrowFortune = parseFortune(tomorrowResult, "明日运势");

        return constellation + "运势:\n" + todayFortune + "\n\n" + tomorrowFortune;
    }

    private static String parseFortune(JSONObject result, String title) {
        if (result.getInteger("error_code") != 0) {
            return "请求失败";
        }
        String date = result.getString("date");
        String all = result.getString("all");
        String health = result.getString("health");
        String love = result.getString("love");
        String money = result.getString("money");
        String work = result.getString("work");
        String number = result.getString("number");
        String qfriend = result.getString("QFriend");
        String summary = result.getString("summary");

        return title + ":\n日期:" + date + "\n综合指数:" + all + "\n健康指数:"
                + health + "\n爱情指数:" + love + "\n财运指数:" + money + "\n工作指数:"
                + work + "\n幸运数字:" + number + "\n速配星座:" + qfriend + "\n概述:" + summary;
    }

}