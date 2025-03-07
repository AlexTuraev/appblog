create table if not exists tag(
                                  id bigserial primary key,
                                  tag_text varchar(50)
    );

create table if not exists comment(
                                      id bigserial primary key,
                                      content text
);

drop table if exists post;

create table if not exists post(
    id bigserial primary key,
    title varchar(256) not null,
    content text,
    tag_id bigint,
    comment_id bigint,
    foreign key (tag_id) references tag(id) on delete cascade,
    foreign key (comment_id) references comment(id) on delete cascade,
    count_like int default 0,
    tags text,
    image bytea
);


insert into post(title, content, count_like) values ('Пост1', 'Содержание поста №1', 10);
insert into post(title, content) values ('Пост2', 'Содержание поста №2');
insert into post(title, content) values ('Пост3', 'Содержание поста №3. Содержание поста №3');
insert into post(title, content) values ('Пост4', 'Содержание поста №4. Содержание поста №4.');
insert into post(title, content) values ('Пост5', 'Содержание поста №4. Содержание поста №5.');
insert into post(title, content) values ('Пост6', 'Содержание поста №4. Содержание поста №6.');
insert into post(title, content) values ('Пост7', 'Содержание поста №4. Содержание поста №7.');