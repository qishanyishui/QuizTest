
/***实现过程中的思路sql***/
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

/***初始化考试试卷题目 qtype 1:多选题  2:开放题***/
insert into question (qcontent,qtopics,qdifficulty,qtype)
values('Which statements  inheritance are true?','C++',3,1);

select max(qid) as qid from question;

insert into mcqchoice (mchoice,mvalid,qid)
values('A. In C++ programming language only allows single inheritance.',false,3)
insert into mcqchoice (mchoice,mvalid,qid)
values('B. In C++ programming language allows a class to implement only one interface.',false,5)
insert into mcqchoice (mchoice,mvalid,qid)
values('C. In C++ programming language a class cannot extend a class and implement a interface together.',true,5)
insert into mcqchoice (mchoice,mvalid,qid)
values('D. In C++ programming language single inheritance makes code more reliable.',false,5)

insert into answer (qid,atext) values(5,'In C++ programming language a class cannot extend a class and implement a interface together.')

/***以上语句执行5次  插入5条多选择题***/

/***初始化开放试题 ***/
insert into question (qcontent,qtopics,qdifficulty,qtype)
values('What is an Iterator ?','C++',3,2)

select max(qid) from question

insert into openquestion (oqtext,qid)
values('The Iterator interface provides a number of methods that are able to iterate over any Collection. Each C++ Collection contains the iterator method that returns an Iterator instance. Iterators are capable of removing elements from the underlying collectionduring the iteration.',7)

/***以上语句执行2次  插入2条开放题***/


/** 后端先造数据**/
/**  1 查询出题目的标题**/
select distinct qtopics from QUESTION;
/**  2 查询出多选题  放入一个集合中,待后面随机生成试题**/
select * from QUESTION where qtype=1 and qtopics=?;
/**  3 查询出开放题  放入一个集合中,待后面随机生成试题**/
select * from QUESTION where qtype=2 and qtopics=?;
/**  4 查询出一个多选题的所有选择项**/
select * from MCQChoice where qid=?;
/** 从集合中产生试题**/
/** 前端把这些数据按照格式展示在UI界面上**/
/** 对比用户提交的答案和标准答案,统计分数**/
