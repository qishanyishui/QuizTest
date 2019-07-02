
/***初始化如下***/

/***创建表结构***/
create table student(uid bigint auto_increment, uname varchar(50), upassword varchar(30),ulevel int(11),unumber varchar(30));
create table STUDENTGRADE(sid bigint auto_increment,uid bigint,sgrade int(11),stotal int(11));
create table QUESTION(qid bigint auto_increment, qcontent varchar(255),qtopics varchar(100),qdifficulty int(11),qtype int(11));
create table MCQChoice(mid bigint auto_increment, mchoice varchar(100),mvalid boolean,qid bigint);
create table OpenQuestion(oqid bigint auto_increment,oqtext varchar(4000),qid bigint);
create table ANSWER(aid bigint auto_increment,qid bigint,atext varchar(255));
create table QUIZ(id bigint auto_increment, name varchar(255));

/***初始化测试用户登录数据 1:管理员 2:普通用户***/
insert into student (uname,upassword,ulevel) values ('admin','123',1);
insert into student (uname,upassword,ulevel,unumber) values ('front','123',2,141785);

/***
后端手动造数据
    使用命令将sql下面的txt后缀的文件导入数据库
    CREATE TABLE MYTABLE AS SELECT * FROM CSVREAD('D:/H2/dbbak/20141013.csv');
前端试卷试题
    按照试卷标题  先从数据库里面读取多项选择题或者开放试题按照题目复杂度规则生成试卷，试卷题目保存到文件中，然后做题时从文件中读取使用。

