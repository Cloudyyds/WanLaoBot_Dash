from nonebot import on_command, CommandSession
import requests
import json

# on_command 装饰器将函数声明为一个命令处理器
@on_command('fortune', aliases=('运势查询', '运势', '星座运势'))
async def fortune(session: CommandSession):
    # 取得消息的内容，并且去掉首尾的空白符
    constellation = session.current_arg_text.strip()

    if not constellation:
        constellation = (await session.aget(prompt='你想查询哪个星座的运势呢？')).strip()
        # 如果用户只发送空白符，则继续询问
        while not constellation:
            constellation = (await session.aget(prompt='要查询的星座名称不能为空呢，请重新输入')).strip()
    
    fortune_report = await get_fortune_of_cons(constellation)
    await session.send(fortune_report)


async def get_fortune_of_cons(constellation: str) -> str:
    # 这里简单返回一个字符串
    headers ={"Content-Type": "application/x-www-form-urlencoded"}
    url = "http://web.juhe.cn/constellation/getAll"
    params = {
        "key":"apikey", # 在个人中心->我的数据,接口名称上方查看
        "consName":constellation, # 星座名称，如:狮子座
        "type":"today", # 运势类型：today,tomorrow,week,month,year    
    }
    t_resp = requests.get(url,params,headers=headers)
    result = json.loads(t_resp.text)
    error_code = result['error_code']
    if (error_code == 0):
        date = result['datetime']# 日期
        all = result['all']# 综合指数
        health = result['health']# 健康指数
        love = result['love']# 爱情指数
        money = result['money']# 财运指数
        work = result['work']# 工作指数
        number = result['number']# 幸运数字
        qfriend = result['QFriend']# 速配星座
        summary = result['summary']# 今日概述
        t_fortune = f"日期:{date}\n综合指数:{all}\n健康指数:{health}\n爱情指数:{love}\n财运指数:{money}\n工作指数:{work}\n幸运数字:{number}\n速配星座:{qfriend}\n今日概述:{summary}" 
    else:
        return "请求失败"

    mparams = {
        "key":"apikey", # 在个人中心->我的数据,接口名称上方查看
        "consName":constellation, # 星座名称，如:狮子座
        "type":"tomorrow", # 运势类型：today,tomorrow,week,month,year    
    }
    m_resp = requests.get(url,mparams,headers=headers)
    m_result = json.loads(m_resp.text)
    error_code = m_result['error_code']
    if (error_code == 0):
        date = m_result['datetime']# 日期
        all = m_result['all']# 综合指数
        health = m_result['health']# 健康指数
        love = m_result['love']# 爱情指数
        money = m_result['money']# 财运指数
        work = m_result['work']# 工作指数
        number = m_result['number']# 幸运数字
        qfriend = m_result['QFriend']# 速配星座
        summary = m_result['summary']# 明日概述
        m_fortune = f"日期:{date}\n综合指数:{all}\n健康指数:{health}\n爱情指数:{love}\n财运指数:{money}\n工作指数:{work}\n幸运数字:{number}\n速配星座:{qfriend}\n明日概述:{summary}" 
    else:
        return "请求失败"

    wparams = {
        "key":"apikey", # 在个人中心->我的数据,接口名称上方查看
        "consName":constellation, # 星座名称，如:狮子座
        "type":"week", # 运势类型：today,tomorrow,week,month,year    
    }
    w_resp = requests.get(url,wparams,headers=headers)
    w_result = json.loads(w_resp.text)
    error_code = w_result['error_code']
    if (error_code == 0):
        w_date = w_result['date']# 日期
        w_health = w_result['health']# 健康
        w_love = w_result['love']# 爱情
        w_money = w_result['money']# 财运
        w_work = w_result['work']# 工作
        w_fortune = f"日期:{w_date}\n健康情况:{w_health}\n恋情:{w_love}\n财运:{w_money}\n工作:{w_work}"
    else:
        return "请求失败"
    return f"{constellation}今日运势:\n{t_fortune}\n\n明日运势:{m_fortune}\n\n本周运势:\n{w_fortune}"
