FROM mysql:8.0

COPY init.sql /docker-entrypoint-initdb.d

ENV MYSQL_USER=board
ENV MYSQL_PASSWORD=board1234
ENV MYSQL_ROOT_PASSWORD=root1234
ENV MYSQL_DATABASE=boarddb

CMD [ "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci" ]