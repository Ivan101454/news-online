# Задание: Performance Monitoring Starter
В отдельном модуле spring-metric ru.clevertec.springbootmetricstarter.annotation создаем аннотацию @MonitorPerformance,  
которую в примере поставим над методом в основном проекте car-dealer carService.findById(Long id) и затем мы получим время выполнения этого метода, если оно будет больше минимально требуемого, в примере 10 мс.   
Аннотацию можно вкл/выкл и устанавливать порог времени срабатывания в application.yml основного проекта
значениями enable и min-time-execute соответсвенно  





