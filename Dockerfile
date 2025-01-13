# Используем базовый образ для Java
FROM openjdk:21

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR файл приложения в контейнер
COPY api/build/libs/api-1.0-SNAPSHOT.jar news-online.jar

# Устанавливаем переменные окружения (по необходимости)
ENV JAVA_OPTS=""

# Команда запуска приложения
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/news-online.jar"]
