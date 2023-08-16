package Dash;

import okhttp3.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class NewsUtil{

    private static final String URL = "http://v.juhe.cn/toutiao/index";
    private static final String KEY = "57a1d2f7805864c10bd8336838125dc7";

    public static String getNews() throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL+"?key="+KEY+"&page_size=16")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            String result = response.body().string();
            JSONObject json = JSON.parseObject(result);

            JSONArray newsArray = json.getJSONObject("result")
                    .getJSONArray("data");

            StringBuilder report = new StringBuilder();
            for (Object obj : newsArray) {
                JSONObject newObj = (JSONObject) obj;
                report.append(newObj.getString("title") + "\n");
                report.append(newObj.getString("url") + "\n\n");
            }

            return report.toString();

        } catch (Exception e) {
            return "请求失败";
        }

    }

}