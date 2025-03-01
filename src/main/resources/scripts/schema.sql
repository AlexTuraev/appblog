create table of not exists post(
    id bigserial primary key,
    name varchar(256) not null,
    content text,
    foreign key (tag_id) references tag(id) on delete cascade,
    foreign key (comment_id) references comment(id) on delete cascade,
    count_like int default 0
);

create table of not exists tag(
    id bigserial primary key,
    tag_text varchar(50)
);

create table of not exists comment(
    id bigserial primary key,
    content text
);

insert into post(name, content) values ('Пост1', 'Содержание поста №1');
insert into post(name, content) values ('Пост2', 'Содержание поста №2');
insert into post(name, content) values ('Пост2', 'Содержание поста №3. Содержание поста №3');
insert into post(name, content) values ('Пост2', 'Содержание поста №4. Содержание поста №4.');