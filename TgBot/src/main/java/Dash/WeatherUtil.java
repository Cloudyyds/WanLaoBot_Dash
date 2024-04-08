package Dash;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

public class WeatherUtil {

    private static final String API_URL = "http://apis.juhe.cn/simpleWeather/query";
    private static final String KEY = "apikey";

    public static String getWeather(String city) throws Exception {
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("city", city)
                .add("key", KEY)
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseContent = response.body().string();

        if(responseContent != null) {
            try {
                JSONObject result = JSONObject.parseObject(responseContent);
                if(result.getInteger("error_code") == 0) {
                    JSONObject realtime = result.getJSONObject("result").getJSONObject("realtime");
                    String temperature = realtime.getString("temperature");
                    String humidity = realtime.getString("humidity");
                    String info = realtime.getString("info");
                    String wid = realtime.getString("wid");
                    String direct = realtime.getString("direct");
                    String power = realtime.getString("power");
                    String aqi = realtime.getString("aqi");

                    return city + "天气:\n温度:" + temperature + "\n湿度:" + humidity
                            + "\n天气:" + info + "\n天气标识:" + wid + "\n风向:" + direct
                            + "\n风力:" + power + "\n空气质量:" + aqi;
                } else {
                    return "请求失败";
                }
            } catch (Exception e) {
                return "解析结果异常";
            }
        } else {
            return "请求异常";
        }
    }
}
