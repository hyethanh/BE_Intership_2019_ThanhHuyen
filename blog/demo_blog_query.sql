

create table User(
    id int primary key auto_increment,
    username varchar(255),
    password varchar(255),
    full_name varchar(255),
    email varchar(255)
);

create table Role(
    role_id int primary key auto_increment,
    name varchar(50)
);

create table User_Role(
    user_id int,
    role_id int,
    foreign key (user_id) references User(id),
    foreign key (role_id) references Role(role_id)
);

create table Post(
    id int primary key auto_increment,
    title varchar(255),
    content varchar(255),
    post_date Date,
    user_id int,
    constraint fk_post_user foreign key (user_id) references User(id)
);

create table Tag(
    id int primary key auto_increment ,
    name varchar(255)
);

create table Post_Tag(
	post_id int,
    tag_id int,
	foreign key (post_id) references Post(id),
    foreign key (tag_id) references Tag(id)
);

create table Category(
    id int primary key auto_increment,
    cate varchar(255)
);

create table Post_Cate(
	post_id int,
    cate_id int,
	foreign key (post_id) references Post(id),
    foreign key (cate_id) references Category(id)
);

create table Comment(
	id int primary key,
    content varchar(255),
    post_id int,
    user_id int,
    cmt_date Date,
    constraint fk_cmt_post foreign key (post_id) references Post(id),
    constraint fk_cmt_user foreign key (user_id) references User(id)
)