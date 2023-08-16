package Dash;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import com.alibaba.fastjson.JSON;

public class ImageUtil {

    private static final String URL = "https://tuapi.eees.cc/api.php";

    public static String getImage(String category) throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL + "?type=json&category="+category)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            String result = response.body().string();
            JSONObject json = JSON.parseObject(result);

            if (!"0".equals(json.getString("error"))) {
                return "请求失败";
            }

            return json.getString("img");

        } catch (Exception e) {
            return "请求失败";
        }

    }

}
