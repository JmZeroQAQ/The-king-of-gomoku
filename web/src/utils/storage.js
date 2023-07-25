// 获取令牌
function getToken() {
    return localStorage.getItem("token");
}

// 设置令牌
function setToken(token) {
    localStorage.setItem("token", token);
}

// 移除令牌
function removeToken() {
    localStorage.removeItem("token");
}

export {
    getToken,
    setToken,
    removeToken,
}