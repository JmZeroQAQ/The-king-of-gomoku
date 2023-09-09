# ✨ TKOG ✨
一个基于 SpringBoot 和 Vue3 的五子棋对战平台，支持在线对战，可以选择亲自出马战胜对手，亦或者是创造 Bot 来取得胜利。

## ✨ 特性
- 🔥 使用SpringBoot + Vue3 + Element-Plus 等主流框架
- 💪 使用 Docker 执行 Bot 的代码
- 🎉 前后端完全分离，可分别部署到不同服务器
- 👏 支持对局回放

## 🎇 效果展示

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
后端运行：
- 使用 Idea 打开 `The-king-of-gomoku/backendcloud/`
- 点击 Idea 右边工具栏的 Maven，选择 "重新加载所有 Maven 项目"
- 等待依赖安装完成
- 运行 各个 Module 里面的 Application 类
