#MediumTestProjectForBoostBrain

##REST API Service

Для запроса необходимо в GET указать имя города и код страны.

###Примеры
**/weather/five-days/analysis/averagetemp?city=Moscow&country=ru** данный запрос выводит среднюю температуру (кельвин) на будущие пять дней для г. Москва

**/weather/five-days/analysis/mintemp?city=Moscow&country=ru** данный запрос выводит минимальную температуру (кельвин) за будущие пять дней для г. Москва с указанием даты и времени(в миллисекундах)

**/weather/five-days/analysis/maxtemp?city=Moscow&country=ru** данный запрос выводит максимальную температуру (кельвин) за будущие пять дней для г. Москва с указанием даты и времени(в миллисекундах)