create table User
(
    seq int AUTO_INCREMENT PRIMARY KEY comment '번호',
    email varchar(100) not null comment '이메일',
    password varchar(20) not null comment '비밀번호',
    name varchar(10) not null comment '닉네임',
    auth varchar(10) not null comment '권한',
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '생성 날짜',
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '수정 날짜'
);

create table Schedule
(
    seq int AUTO_INCREMENT PRIMARY KEY comment '번호',
    user_seq int not null comment '유저 번호',
    title varchar(50) comment '제목',
    content varchar(300) comment '내용',
    weather varchar(50) comment '날씨',
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '생성 날짜',
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '수정 날짜',
    CONSTRAINT foreign key (user_seq) references User(seq) ON DELETE CASCADE
);

create table Comment
(
    seq int AUTO_INCREMENT PRIMARY KEY comment '번호',
    user_seq int not null comment '작성자 번호',
    schedule_seq int not null comment '일정 번호',
    content varchar(300) comment '내용',
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '생성 날짜',
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '수정 날짜',
    CONSTRAINT foreign key (user_seq) references User(seq) ON DELETE CASCADE,
    CONSTRAINT foreign key (schedule_seq) references Schedule(seq) ON DELETE CASCADE
);

insert into User(password, email, name) values('1q2w3e4r', 'hong@gmail.com', '홍길동');
insert into Schedule(user_seq, title, content) values(1, '제목제목제목', '내용내용내용');
insert into Comment(user_seq, schedule_seq, content) values(1, 1, '댓글댓글댓글');

# drop table Schedule;
# drop table User;
