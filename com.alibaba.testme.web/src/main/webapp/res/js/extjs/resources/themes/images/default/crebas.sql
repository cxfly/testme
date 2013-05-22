/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/5/21 11:26:30                           */
/*==============================================================*/


drop table if exists testme.tm_system;

drop table if exists testme.tm_system_env;

drop table if exists testme.tm_system_env_detail;

drop table if exists testme.tm_system_require_prop;

drop table if exists testme.tm_testunit;

drop table if exists testme.tm_testunit_flow;

drop table if exists testme.tm_testunit_flow_case;

drop table if exists testme.tm_testunit_flow_case_detail;

drop table if exists testme.tm_testunit_flow_detail;

drop table if exists testme.tm_testunit_param;

drop table if exists testme.tm_testunit_param_ext;

drop table if exists testme.tm_user;

drop table if exists testme.tm_work_space;

/*==============================================================*/
/* Table: tm_system                                             */
/*==============================================================*/
create table tm_system
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime comment '����ʱ��',
   creator              varchar(128) comment '������',
   gmt_modified         datetime comment '�޸�ʱ��',
   modifier             varchar(128) comment '�޸���',
   name                 varchar(128) comment 'ϵͳ����',
   remark               varchar(4000) comment '��ע',
   primary key (id)
);

alter table tm_system comment 'ϵͳ�����';

/*==============================================================*/
/* Table: tm_system_env                                         */
/*==============================================================*/
create table tm_system_env
(
   ID                   int(18) not null auto_increment comment 'ID',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   user_id              int(18) not null comment '�û�ID',
   system_id            int(18) not null comment 'ϵͳID',
   config_name          varchar(128) not null comment '��������',
   is_default           char(1) not null default 'Y' comment '�Ƿ�Ĭ��ʹ��',
   primary key (ID)
);

alter table tm_system_env comment '�û�ϵͳ����������';

/*==============================================================*/
/* Table: tm_system_env_detail                                  */
/*==============================================================*/
create table tm_system_env_detail
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   system_id            int(18) not null comment 'ϵͳ����������ID',
   prop_key             varchar(128) not null comment '���Լ�',
   prop_value           varchar(128) not null comment '����ֵ',
   remark               varchar(128) not null comment '��ע����',
   config_type          varchar(128) not null comment '��������',
   primary key (id)
);

alter table tm_system_env_detail comment '�û�ϵͳ�������������';

/*==============================================================*/
/* Table: tm_system_require_prop                                */
/*==============================================================*/
create table tm_system_require_prop
(
   ID                   int(18) not null auto_increment comment 'ID',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   system_id            int(18) not null comment '����ϵͳID',
   prop_code            varchar(128) not null comment '����Ӣ����',
   prop_name            varchar(128) not null comment '����������',
   default_value        varchar(128) not null comment 'Ĭ��ֵ',
   nullable             char(1) not null comment '�Ƿ�ɿ�',
   help                 varchar(128) comment '��д��ʾ',
   primary key (ID)
);

alter table tm_system_require_prop comment 'ϵͳ��Ҫ����������';

/*==============================================================*/
/* Table: tm_testunit                                           */
/*==============================================================*/
create table tm_testunit
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��2',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   code                 varchar(128) not null comment '���Ե�Ԫ����',
   name                 varchar(128) not null comment '���Ե�Ԫ������',
   work_space_id        int(18) not null comment '�����ռ�ID',
   class_qualified_name varchar(512) not null comment '��·��',
   version              varchar(128) not null comment 'bundle�汾��',
   tag                  varchar(256) comment '��ǩ',
   user_id              int(18) not null comment '�û�ID',
   remark               varchar(4000) comment '��ע˵��',
   primary key (id)
);

alter table tm_testunit comment '���Ե�Ԫ�����';

/*==============================================================*/
/* Table: tm_testunit_flow                                      */
/*==============================================================*/
create table tm_testunit_flow
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��2',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��2',
   modifier             varchar(128) not null comment '�޸���',
   code                 varchar(128) not null comment '���Ե�Ԫ���̱���',
   name                 varchar(128) not null comment '���Ե�Ԫ����������',
   system_id            varchar(128) not null comment 'ϵͳȺ�����',
   tag                  varchar(256) comment '��ǩ',
   user_id              int(18) not null comment '�û�ID',
   pic_url              varchar(128) comment 'ͼƬ',
   times                int(18) comment '���ô���',
   env_config_required  char(1) not null comment '�Ƿ���Ҫϵͳ���ò���',
   �Ƿ����                 char(1) not null comment '�Ƿ����',
   remark               varchar(4000) comment '��ע˵��',
   primary key (id)
);

alter table tm_testunit_flow comment '���Ե�Ԫ���̶����';

/*==============================================================*/
/* Table: tm_testunit_flow_case                                 */
/*==============================================================*/
create table tm_testunit_flow_case
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   testunit_flow_id     varchar(128) not null comment 'bundle����ID',
   status               varchar(128) not null comment '����ִ��״̬',
   gmt_start            datetime comment '��ʼִ��ʱ��',
   gmt_end              datetime comment '�������ʱ��',
   user_id              varchar(128) not null comment '�û�ID',
   system_env_id        int(18) not null comment '������������ID',
   gmt_next_retry       datetime comment '�´�����ʱ��',
   primary key (id)
);

alter table tm_testunit_flow_case comment '��������ʵ����';

/*==============================================================*/
/* Table: tm_testunit_flow_case_detail                          */
/*==============================================================*/
create table tm_testunit_flow_case_detail
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   testunit_flow_case_id int(18) not null comment '����ʵ��ID',
   testunit_flow_detail_id int(18) not null comment '���Ե�Ԫ��·ID',
   gmt_start            datetime comment 'ִ�п�ʼʱ��',
   gmt_end              datetime comment 'ִ�н���ʱ��',
   gmt_last_run         datetime comment '����ִ��ʱ��',
   status               varchar(128) not null comment 'ִ��״̬',
   in_param             varchar(4000) comment '���Ե�Ԫ���ֵ',
   out_param            varchar(4000) comment '���Ե�Ԫ����ֵ',
   msg                  varchar(4000) comment 'ִ�н����Ϣ',
   primary key (id)
);

alter table tm_testunit_flow_case_detail comment '��������ʵ�������';

/*==============================================================*/
/* Table: tm_testunit_flow_detail                               */
/*==============================================================*/
create table tm_testunit_flow_detail
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   testunit_flow_id     int(18) not null comment '���Ե�Ԫ���̶���ID',
   testunit_id          int(18) not null comment '���Ե�ԪID',
   pre_testunit_id      int(18) comment '��һ�����Ե�ԪID',
   next_testunit_id     int(18) comment '��һ�����Ե�Ԫ ID',
   primary key (id)
);

alter table tm_testunit_flow_detail comment '���Ե�Ԫ���������';

/*==============================================================*/
/* Table: tm_testunit_param                                     */
/*==============================================================*/
create table tm_testunit_param
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   label_name           varchar(128) not null comment '�ؼ���ʾ��',
   param_name           varchar(128) not null comment '��������',
   form_ctrl_type       varchar(128) not null comment '���ؼ�����',
   testunit_id          varchar(128) not null comment '���Ե�Ԫ ID',
   default_value        varchar(128) comment 'ȱʡֵ',
   rank                 int(4) not null comment 'λ�����',
   is_required          char(1) not null comment '�Ƿ����',
   help                 varchar(128) comment '��д��ʾ',
   primary key (id)
);

alter table tm_testunit_param comment '���Ե�Ԫ���������';

/*==============================================================*/
/* Table: tm_testunit_param_ext                                 */
/*==============================================================*/
create table tm_testunit_param_ext
(
   id                   int(18) not null auto_increment comment 'id',
   gmt_create           datetime comment '����ʱ��',
   creator              varchar(128) comment '������',
   gmt_modified         datetime comment '�޸�ʱ��',
   modifier             varchar(128) comment '�޸���',
   value_name           varchar(128) comment '�ؼ�ֵ��ʾ����',
   value                varchar(128) comment '�ؼ�ֵ',
   testunit_param_id    int(18) comment '���Ե�Ԫ��������id',
   primary key (id)
);

alter table tm_testunit_param_ext comment '���Ե�Ԫ����������չ��';

/*==============================================================*/
/* Table: tm_user                                               */
/*==============================================================*/
create table tm_user
(
   ID                   int(18) not null auto_increment comment 'ID',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   user_name            varchar(128) not null comment '�û���',
   password             varchar(128) not null comment '�û�����',
   wangwang             varchar(128) comment '�û�����',
   primary key (ID)
);

alter table tm_user comment '�û���';

/*==============================================================*/
/* Table: tm_work_space                                         */
/*==============================================================*/
create table tm_work_space
(
   ID                   int(18) not null auto_increment comment 'ID',
   gmt_create           datetime not null comment '����ʱ��',
   creator              varchar(128) not null comment '������',
   gmt_modified         datetime not null comment '�޸�ʱ��',
   modifier             varchar(128) not null comment '�޸���',
   name                 varchar(128) not null comment '�����ռ�����',
   user_id              varchar(128) not null comment '�����û�ID',
   system_id            int(18) not null comment '����ϵͳ',
   primary key (ID)
);

alter table tm_work_space comment '���Ե�Ԫ�����ռ�';

