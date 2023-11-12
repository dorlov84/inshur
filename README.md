# Springboot weather API

Notes:
1. To build execure "mvn install" in project root folder 
2. To start server execute "mvn spring-boot:run" in application folder

Weather API:
http://localhost:8080/weather/warmestDay?lat={LAT}&lon={LON}

History API:
http://localhost:8080/history/search/weather?asc={true|false}&sortBy={dt|lat|lon}&lat={LAT_FILTER}&lon={LON_FILTER}
