
/***��ʼ������***/

/***������ṹ***/
create table student(uid bigint auto_increment, uname varchar(50), upassword varchar(30),ulevel int(11),unumber varchar(30));
create table STUDENTGRADE(sid bigint auto_increment,uid bigint,sgrade int(11),stotal int(11));
create table QUESTION(qid bigint auto_increment, qcontent varchar(255),qtopics varchar(100),qdifficulty int(11),qtype int(11));
create table MCQChoice(mid bigint auto_increment, mchoice varchar(100),mvalid boolean,qid bigint);
create table OpenQuestion(oqid bigint auto_increment,oqtext varchar(4000),qid bigint);
create table ANSWER(aid bigint auto_increment,qid bigint,atext varchar(255));
create table QUIZ(id bigint auto_increment, name varchar(255));

/***��ʼ�������û���¼���� 1:����Ա 2:��ͨ�û�***/
insert into student (uname,upassword,ulevel) values ('admin','123',1);
insert into student (uname,upassword,ulevel,unumber) values ('front','123',2,141785);

/***
����ֶ�������
    ʹ�����sql�����txt��׺���ļ��������ݿ�
    CREATE TABLE MYTABLE AS SELECT * FROM CSVREAD('D:/H2/dbbak/20141013.csv');
ǰ���Ծ�����
    �����Ծ����  �ȴ����ݿ������ȡ����ѡ������߿������ⰴ����Ŀ���Ӷȹ��������Ծ��Ծ���Ŀ���浽�ļ��У�Ȼ������ʱ���ļ��ж�ȡʹ�á�

