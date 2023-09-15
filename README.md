# ✨ TKOG ✨
一个基于 SpringBoot 和 Vue3 的五子棋对战平台，支持在线对战，可以选择亲自出马战胜对手，亦或者是创造 Bot 来取得胜利。

## ✨ 特性
- 🔥 使用SpringBoot + Vue3 + Element-Plus 等主流框架
- 💪 使用 Docker 执行 Bot 的代码
- 🎉 前后端完全分离，可分别部署到不同服务器
- 👏 使用JWT进行身份验证

## 🎇 效果展示
[**Demo**](http://47.115.228.202/home/)

<div align="center">
    <img alt="Home" src="https://github.com/JmZeroQAQ/ImageRepository/raw/main/tkog/tkog-home.png" />
    <img alt="Home" src="https://github.com/JmZeroQAQ/ImageRepository/raw/main/tkog/tkog-bot.png" />
    <img alt="Record" src="https://github.com/JmZeroQAQ/ImageRepository/raw/main/tkog/tkog-record.png" />
</div>

## 🚀 快速开始

前端运行：
```sh
# 克隆项目
git clone https://github.com/JmZeroQAQ/The-king-of-gomoku.git

# 到前端目录
cd The-king-of-gomoku/web/

# 安装依赖
pnpm install

# 运行
pnpm run dev
```

创建数据库：
```sql
# 创建数据库
CREATE DATABASE tkog;
# 使用数据库
use tkog;
# 运行脚本创建对应表，这里换成项目所在的绝对路径即可。
source ~/The-king-of-gomoku/script.sql;
```

在开始运行后端前请务必确保已经看了[**注意事项**](#-注意事项)。

后端运行：
- 使用 Idea 打开 `The-king-of-gomoku/backendcloud/`
- 点击 Idea 右边工具栏的 Maven，选择 "重新加载所有 Maven 项目"
- 等待依赖安装完成
- 运行 各个 Module 里面的 Application 类

## 🐸 模块介绍
TKOG 一共有三个模块，其中 backend 为中心模块。backend 会将对应的执行代码任务，或者是匹配任务通过 Spring Cloud 发送给 botSystem 模块或者是 matchingSystem 模块。

- backend：主模块，负责响应前端的数据请求和身份验证，基本业务都是由这个模块完成的。
- matchingSystem： 匹配模块，维护一个匹配池，当成功匹配两个用户的时候，将结果返回给 backend 模块。
- botSystem：运行用户Bot代码的模块，每次执行都会创建一个 docker 容器，将用户的代码在容器内编译运行。

## 💥 注意事项
- 在运行各个模块前需要先将数据库的各个表创建好，只需要使用根目录的`script.sql`脚本即可创建数据库。另外，还需要去设置 backend 模块里面`application.properties` 的数据库连接地址和用户，以及对应的密码。
- 各个模块的 通信链接(url)都放在对应模块的 `application.properties` 文件内，若需要将不同的模块部署到不同的服务器上，需要修改相应链接，以及在 `config/SecurityConfig.java` 里面放行对应的接口。
- 运行或部署 botSystem 模块需要创建对应的工作目录，工作目录的路径也设置在 `application.properties` 内。需要注意在`start.sh` 里也要设置成相同的工作目录。例如：源代码内设置的工作目录是`/home/jmzero/app/`，所以我们要创建`/home/jmzero/app/code/`这个文件夹，code目录是放置bot将要执行的代码和输入数据的地方。