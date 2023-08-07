import urllib
import urllib.request as request
import urllib.error as error
import json

async def get_weather_of_city(city: str) -> str:
    # 这里简单返回一个字符串
    # 实际应用中，这里应该调用返回真实数据的天气 API，并拼接成天气预报内容
    api_url = 'http://apis.juhe.cn/simpleWeather/query'
    params_dict = {
        "city": city,  # 查询天气的城市名称，如：北京、苏州、上海
        "key": "c6dd1e27cb8ef726552fdb01c39eed0b",  # 您申请的接口API接口请求Key
    }
    params = urllib.parse.urlencode(params_dict)
    try:
        req = request.Request(api_url, params.encode())
        response = request.urlopen(req)
        content = response.read()
        if content:
            try:
                result = json.loads(content)
                error_code = result['error_code']
                if (error_code == 0):
                    temperature = result['result']['realtime']['temperature']
                    humidity = result['result']['realtime']['humidity']
                    info = result['result']['realtime']['info']
                    wid = result['result']['realtime']['wid']
                    direct = result['result']['realtime']['direct']
                    power = result['result']['realtime']['power']
                    aqi = result['result']['realtime']['aqi']
                    temres = f"温度：{temperature}\n湿度：{humidity}\n天气：{info}\n天气标识：{wid}\n风向：{direct}\n风力：{power}\n空气质量：{aqi}" 
                else:
                    return "请求失败"
            except Exception as e:
                return "解析结果异常"
        else:
            return "请求异常"
    except error.HTTPError as err:
        return err
    except error.URLError as err:
        # 其他异常
        return err        
    return f'{city}的天气情况：\n{temres}'

