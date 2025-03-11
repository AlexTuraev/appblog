Application blog

$ - обозначение командной строки.

Развернуть сервер Tomcat.
   Будем считать, что развернули в папке tomcat 

Сборка проекта (команда Maven)
    $ mvn clean package
    Скопировать target/appblog.war в папку сервера tomcat/webapps

Запустить сервер tomcat 
    $ bin/startup

При локальном запуске набрать в браузере: 
    localhost://appblog/blog