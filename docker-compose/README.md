## 도커 컴포즈 백그라운드 실행법
docker-compose up -d

## DB 더미 데이터
```sql
USE boarddb;

create table person(
    id int primary key,
    name varchar(100)
);
insert into person values(1, 'kang');
select * from person;
```