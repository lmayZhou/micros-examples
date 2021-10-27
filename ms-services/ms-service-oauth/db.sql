/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/12/25 14:46:42                          */
/*==============================================================*/

drop table if exists oauth_client_details;

drop table if exists sys_role;

drop table if exists sys_role_permission;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: oauth_client_details                                  */
/*==============================================================*/
create table oauth_client_details
(
    client_id            varchar(256) not null comment '客户端应用的账号',
    resource_ids         varchar(256) comment '客户端应用可访问的资源服务器列表',
    client_secret        varchar(256) not null comment '客户端应用的密码',
    scope                varchar(256) comment '资源服务器拥有的所有权限列表 (get add delete update)',
    authorized_grant_types varchar(256) comment '客户端支持的授权码模式列表',
    web_server_redirect_uri varchar(256) comment '授权码模式,申请授权码后重定向的uri',
    authorities          varchar(256) comment '权限',
    access_token_validity integer not null default 3600 comment '设置颁发token的有效期',
    refresh_token_validity integer not null default 36000 comment '颁发refresh_token的有效期',
    additional_information varchar(4096),
    autoapprove          varchar(256) not null default '1',
    primary key (client_id)
);

alter table oauth_client_details comment '客户端应用注册信息';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
    id                   bigint(20) not null comment '主键ID',
    role_name            varchar(30) not null comment '角色名称',
    role_key             varchar(100) not null comment '角色权限Code',
    sort                 int(10) not null default 1 comment '排序',
    scope                tinyint(1) not null default 1 comment '范围(1. 全部数据; 2. 自定义数据; 3. 本部门数据; 4. 本部门及以下数据;)',
    status               tinyint(1) not null default 0 comment '状态(0. 正常; 1. 停用; 2. 删除;)',
    create_at            datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by            varchar(30) not null comment '创建人',
    update_at            datetime comment '更新时间',
    update_by            varchar(30) comment '更新人',
    remark               varchar(500) comment '备注',
    ext                  varchar(500) comment '拓展字段',
    version              int(10) not null default 1 comment '版本号',
    primary key (id)
);

alter table sys_role comment '角色信息';

/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
    id                   bigint(20) not null comment '主键ID',
    role_id              bigint(20) not null comment '角色ID',
    permission_id        varchar(32) not null comment '权限id',
    create_at            datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by            varchar(30) not null comment '创建人',
    remark               varchar(500) comment '备注',
    primary key (id)
);

alter table sys_role_permission comment '角色权限';

/*==============================================================*/
/* Index: idx_role_permission                                   */
/*==============================================================*/
create unique index idx_role_permission on sys_role_permission
    (
     role_id,
     permission_id
        );

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
    id                   bigint(20) not null comment '主键ID',
    dept_id              bigint(20) comment '部门ID',
    client_id            varchar(256) comment '客户端ID',
    user_name            varchar(30) not null comment '用户名',
    password             varchar(100) not null comment '密码',
    user_type            varchar(2) not null default '01' comment '用户类型(00. 系统用户; 01. 注册用户;)',
    email                varchar(50) comment '用户邮箱',
    phonenumber          varchar(11) comment '手机号',
    sex                  char(1) not null default '2' comment '性别(0. 男; 1. 女; 2. 未知;)',
    avatar               varchar(100) comment '头像路径',
    qq                   varchar(50) comment 'QQ',
    wechat               varchar(50) comment '微信',
    weibo                varchar(50) comment '微博',
    status               tinyint(1) not null default 0 comment '状态(0. 正常; 1. 停用; 2. 删除;)',
    create_at            datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by            varchar(30) not null comment '创建人',
    update_at            datetime comment '更新时间',
    update_by            varchar(30) comment '更新人',
    remark               varchar(500) comment '备注',
    ext                  varchar(500) comment '拓展字段',
    version              int(10) not null default 1 comment '版本号',
    primary key (id)
);

alter table sys_user comment '用户信息';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
    id                   bigint(20) not null comment '主键ID',
    user_id              bigint(20) not null comment '用户ID',
    role_id              bigint(20) not null comment '角色ID',
    create_at            datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by            varchar(30) not null comment '创建人',
    remark               varchar(500) comment '备注',
    primary key (id)
);

alter table sys_user_role comment '用户角色';

/*==============================================================*/
/* Index: idx_user_role                                         */
/*==============================================================*/
create unique index idx_user_role on sys_user_role
(
     user_id,
     role_id
);

INSERT INTO oauth_client_details(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('user-client', NULL, '$2a$10$Qg.q5o1ZBVy/7TrV107u6ORr1Vyl.wm8AGU5tFcbpw2zPQuuxh82C', 'all', 'authorization_code,refresh_token,password', NULL, NULL, 3600, 36000, NULL, '1');
INSERT INTO oauth_client_details(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('ms-oauth', NULL, '$2a$10$Uup.KB3fj0ixHHih2eehZ.MceR1hDOCT0WpPiOFZNgLCY7uEvHRte', 'all', 'authorization_code,refresh_token,password', NULL, NULL, 3600, 36000, NULL, '1');
