# micros-examples

#### 介绍
Microservice Examples (微服务示例)

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1. keytool生成秘钥
```shell
# 生成秘钥
keytool -genkeypair -alias ms-service-oauth -keyalg RSA -keypass ms-oauth -keystore ms-service-oauth.jks -storepass ms-oauth

# 查看公钥/私钥
# http://slproweb.com/products/Win32OpenSSL.html
keytool -list -rfc --keystore ms-service-oauth.jks | openssl x509 -inform pem -pubkey
```

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)