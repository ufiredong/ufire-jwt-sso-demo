# ufire-jwt-sso-demo

学习 oauth2.0 协议.基于 JWT 无状态去中心化思想,实现 sso 单点登录的学习示例。

| 服务         | 描述           | url                         |
| ------------ | -------------- | --------------------------- |
| auth-sso     | 用户认证中心   | http://sso.ufiredong.cn     |
| auth-console | 控制台聚合入口 | http://console.ufiredong.cn |
| auth-client  | 客户端 A       | http://clienta.ufiredong.cn |


用户  admin  admin

auth-sso

authServer/authorize 仿照 oatuh2.0 实现简单的 client ,sercet 认证,
颁发 auth_code 授权码。

authServer/access_token 通过授权码申请 jwt token ,获取 token 后 设置 cookie domain 重定向到目标地址。token/refresh_token 通过服务内部 http 调用 一个内置的永久 token 去为即将过期的 access-token 续签。

token/refresh-token  因为单点登陆业务系统之间的信任关系较高，我们可以在客户端console、clienta服务配置文件中预定义永久token，token的作用在于客户端向auth-sso刷新即将过期的access-token http调用为服务内部调用。

auth-console/auth-client

check/token 客户端 通过 rsa-jwt.pub 公钥对 token 进行校验。

auth-sso 作为认证中心服务端，用 RSA256 非对称加密算法生成密钥对文件 rsa-jwt.pri rsa-jwt.pub，私钥加密 jwt 公钥发放给第三方客户端用户解密,私钥不能泄露给客户端防止客户端自行颁发 toekn。不对 jwt 做中心化处理，服务端及第三方缓存不保存 jwt ，所以 jwt 的过期时间较短，30 分钟，理想情况用户活跃在页面产生 http 请求频繁，每隔 20 分钟就会自动续签。用户超过 30 分钟页面没有发生操作 jwt 就会失效 重新重定向回 sso。分布式 中心化处理可以将 jwt 存在 redis 或者 数据库中，jti 可做 token_key 集群的化 jti 应当用分布式 id 去实现。用户注销 可用 token 黑白名单去实现。

cas 与 jwt 单点登陆比较

cas 是基于 session 机制实现的单点登录解决方案，cas 核心思想就是为了解决单点的登陆，server 为认证中心 client 为客户端服务器 客户端服务器 session 的解决方案 可以用 spring-session 解决 放到第三方的 redis 缓存中。所以 cas 是中心化统一管理 session 的单点登陆系统 cookie 端存储 session-id 单点退出 服务端 session 失效。 jwt 可以存放到 header 头部，以适应浏览器以外的第三方 app 客户端，或者其他 服务 的 http 调用。所以就没有跨域的限制。 服务端不存储 jwt 大大减少了服务端的压力，jwt 可以存放到 cookie localstorage 或者 app 的缓存中 ，但是单点退出时 只能达到 客户端的删除 cookie 删除缓存 的伪注销。实际 token 依然有效， 如果想真正注销，服务端必须对 jwt 的状态进行备案，必然会借助第三方 redis 或者数据库进行持久化 又形成了 另类的 session 机制。 jwt 本身又体积较大，可能存储些用户敏感信息 容易暴露，而 cookie 仅仅存储的是一个 session id 值没什么可用价值 所以我觉的 cas 还是目前最佳的单点登录解决方案。

demo 的实现代码仅供学习理解,没有做统一异常处理，供参考。
