from nonebot import on_command, CommandSession

# on_command 装饰器将函数声明为一个命令处理器
@on_command('menu', aliases=('功能', '帮助', '菜单'))
async def menu(session: CommandSession):
    menulist = "当前功能如下：\n1、天气查询\n2、星座运势\n3、Chat"
    # 向用户发送天气预报
    await session.send(menulist)
