create table User
(
    id       int          not null primary key auto_increment,
    username varchar(32)  not null comment '用户名',
    password char(255)    not null comment '密码',
    nickname varchar(32)  not null comment '昵称',
    email    varchar(64)  not null default '' comment '邮箱',
    mobile   varchar(32)  not null default '' comment '手机号',
    qq       char(32)     not null default '' comment 'qq',
    state    int          not null default 1 comment '状态, 0：未激活, 1: 正常, 2：封号',
    regDt    datetime     not null default now() comment '注册时间',
    avatar   varchar(260) not null default '' comment '头像'
) charset = UTF8MB4, comment ='用户信息表';

create index IDX_username on User (username);