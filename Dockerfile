# Используем базовый образ для Java
FROM openjdk:21

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /news-online

# Копируем JAR файл приложения в контейнер
COPY build/libs/news-online-1.0-SNAPSHOT-plain.jar news-online.jar

# Устанавливаем переменные окружения (по необходимости)
ENV JAVA_OPTS=""

# Команда запуска приложения
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /news-online/news-online.jar"]
