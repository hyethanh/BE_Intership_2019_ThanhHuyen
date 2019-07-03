
create table Post(
    id int primary key auto_increment,
    title varchar(255),
    comment varchar(255)
);

create table Tag(
    id int primary key auto_increment ,
    tag varchar(255)
);

create table Post_Tag(
	postid int,
    tagid int,
	foreign key (postid) references Post(id),
    foreign key (tagid) references Tag(id)
);

create table Category(
    id int primary key auto_increment,
    cate varchar(255)
);

create table Post_Cate(
	postid int,
    cateid int,
	foreign key (postid) references Post(id),
    foreign key (cateid) references Cate(id)
);

create table User(
    userid int primary key auto_increment,
    username varchar(255),
    password varchar(255),
    fullname varchar(255)
);

create table Role(
    roleid int primary key auto_increment,
    description varchar(255),
    name varchar(50)
);

create table User_Role(
    userid int,
    roleid int,
    foreign key (userid) references User(userid),
    foreign key (roleid) references Role(roleid)
)