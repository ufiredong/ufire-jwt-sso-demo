学习oauth2.0协议.基于JWT无状态去中心化思想,实现sso单点登录的学习示例。 
1、auth-sso 认证中心 1、authServer/authorize 仿照oatuh2.0实现简单的client ,sercet 认证,
颁发auth_code授权码。 
2、authServer/access_token 通过授权码申请 jwt token ,获取token后 设置 cookie domain 重定向到目标地址。 3、token/refresh_token 通过
服务内部http调用 一个内置的永久token去为即将过期的access-token续签。 
2、auth-console 控制台 单点系统的管理模块，负责用户权限等管理，及其他子系统的聚合入口 
3、auth-client 接入单点系统的服务
auth-sso 作为认证中心服务端，用RSA256非对称加密算法生成密钥对文件 rsa-jwt.pri rsa-jwt.pub，私钥加密jwt 公钥发放给第三方客户端用户解密,私钥不能泄露给客户端防止客户端自行颁发toekn
不对jwt做中心化处理，服务端及第三方缓存不保存jwt ，所以jwt的过期时间较短，30分钟，理想情况用户活跃在页面产生http请求频繁，每隔20分钟就会自动续签。用户超过30分钟页面没有发生操作 jwt就会失效 重新重定向回sso。
分布式 中心化处理可以将jwt存在redis 或者 数据库中，jit可做 token_key 集群的化 jit 应当用分布式id去实现。用户注销 可用token黑白名单去实现。
cas是基于session机制实现的单点登录解决方案，cas核心思想就是为了解决单点的登陆，server 为认证中心 client为客户端服务器 客户端服务器session 的解决方案 可以用spring-session 解决
放到第三方的redis缓存中。所以 cas 是中心化统一管理session的单点登陆系统 cookie端存储session-id 单点退出 服务端session 失效。 jwt
可以存放到header头部，以适应浏览器以外的第三方app客户端，或者其他 服务 的http调用。所以就没有跨域的限制。 服务端不存储jwt 大大减少了服务端的压力，jwt可以存放到 cookie localstrong 或者app的缓存中
，但是单点退出时 只能达到 客户端的删除cookie 删除缓存 的伪注销。实际token依然有效， 如果想真正注销，服务端必须对jwt的状态进行备案，必然会借助第三方redis或者数据库进行持久化 又形成了 另类的session机制。
jwt本身又体积较大，可能存储些用户敏感信息 容易暴露，而cookie 仅仅存储的是一个session id值没什么可用价值 所以我觉的cas 还是目前最佳的单点登录解决方案。
    



    
    
    
