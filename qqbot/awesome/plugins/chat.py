from nonebot import on_command, CommandSession
from typing import Optional
from aiocqhttp.message import escape
from nonebot import on_command, CommandSession
from nonebot import on_natural_language, NLPSession, IntentCommand
from nonebot.helpers import render_expression
import requests
import json


@on_command('chat')
async def chat(session: CommandSession):
    # 取得消息的内容，并且去掉首尾的空白符
    content = session.state.get('message')
    # 获取内容
    if not content:
        content = (await session.aget(prompt='你想聊些什么呢？')).strip()
        # 如果用户只发送空白符，则继续询问
        while not content:
            content = (await session.aget(prompt='真是的，有话快说呀！')).strip()
    # 获取AI回答
    chat_answer = await get_answer(content)
    # 向用户发送回答
    await session.send(chat_answer)


@on_natural_language(keywords={'chat'},only_to_me=False)
async def _(session: NLPSession):
    # 以置信度 60.0 返回 chat 命令
    # 确保任何消息都在且仅在其它自然语言处理器无法理解的时候使用 chat 命令
    return IntentCommand(60.0, 'chat', args={'message': session.msg_text.split()[1]})



async def get_answer(content: str) -> str:
    url = "https://api.chatanywhere.com.cn/v1/chat/completions"
    payload = json.dumps({
        "model": "gpt-3.5-turbo",
        "messages": [
        {
            "role": "user",
            "content": content
        }
        ]
        })
    headers = {
        'Authorization': 'gptfree key',
        'User-Agent': 'Apifox/1.0.0 (https://apifox.com)',
        'Content-Type': 'application/json'
    }
    response = requests.request("POST", url, headers=headers, data=payload)
    rep = response.json()
    reply = rep["choices"][0]["message"]["content"]
    return f'{reply}'

