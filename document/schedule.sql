create table User
(
    user_id varchar(100) primary key comment '아이디',
    password varchar(100) not null comment '비밀번호',
    email varchar(100) not null comment '이메일',
    name varchar(100) not null comment '닉네임',
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '생성 날짜',
    updateDate TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '수정 날짜'
);

create table Schedule
(
    id int AUTO_INCREMENT PRIMARY KEY comment '번호',
    user_id varchar(100) comment '아이디',
    title varchar(100) comment '제목',
    content varchar(100) comment '내용',
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '생성 날짜',
    updateDate TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '수정 날짜',
    CONSTRAINT foreign key (user_id) references User(user_id) ON DELETE CASCADE
);

create view ScheduleView as
(
    select s.id, u.user_id, u.name, s.title, s.content, s.createDate, s.updateDate
    from User as u join schedule as s on u.user_id = s.user_id
);

insert into User(user_id, password, email, name) values('hong', '1q2w3e4r', 'hong@gmail.com', '홍길동');
insert into Schedule(user_id, title, content) values('hong', '제목제목제목', '내용내용내용');

update user
set name='김길동'
where user_id='hong';

update Schedule
set content='수정수정수정'
where id=1;

drop table Schedule;
drop table User;

delete from Schedule;
delete from User;