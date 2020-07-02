# RPClass
Trabajo de Fin de Máster, curso 2019-20, Facultad de Informática UCM

Autor: Aitor Cayón Ruano
Director: Manuel Freire Morán

## Contenido

RPClass es una aplicación para gamificación de ejercicios en el aula. Permite la creación de perfiles para profesores y estudiantes. Los profesores podrán crear sus propias clases y pruebas para organizar competiciones entre sus alumnos. La participación en las pruebas se realiza de forma individual pero los alumnos estarán agrupados en equipos cuyos miembros colaborarán para obtener los mejores resultados.

Los profesores podrán añadir recursos (clases y pruebas), mediante la carga de ficheros. En el caso de las clases bastará con proporcionar un nombre de clase y los nombre y apellidos de los estudiantes. Para las pruebas se deberá especificar un nombre de pruebas y para cada pregunta un enunciado y las opciones para responder acompañadas de una puntuación. Los docentes podrán controlar el acceso de los alumnos a las pruebas y determinar su momento de finalización. Los resultados obtenidos se recopilarán automáticamente en una tabla y se utilizarán para la generación de gráficas estadísticas 

Los estudiantes participarán en las pruebas y en función de su desempeño incrementarán su puntuación, desbloqueando logros y aportando su contribución individual al progreso del equipo al que pertenezcan. Cada prueba contará con su propia clasificación de alumnos y grupos y otorgará recompensas en función de la posición alcanzada. El progreso conseguido a lo largo de la utilización de la plataforma se recogerá en un ranking global, el objetivo principal de la competición.

Para probar el sistema se incluye en el proyecto el siguiente contenido por defecto (fichero [import.sql](https://github.com/Aitorcay/RPClass/blob/master/codigo/src/main/resources/import.sql)):

    - Un perfil de profesor
    - Una clase con 6 alumnos repartidos en 3 equipos
    - Una prueba
    - Los resultados obtenidos con la participación de los alumnos
    - Logros desbloqueables
        * Número de respuestas correctas (individual / equipo)
        * Número de pruebas superadas (individual)
        * Número de pruebas con puntuación perfecta (individual)
        * Puntuación acumulada (individual / equipo)
        * Número de pruebas finalizadas en el top3 (individual / equipo)
        
Además se incluye el directorio de recursos compartidos utilizado para testear la aplicación (carpeta [datos_de_prueba](https://github.com/Aitorcay/RPClass/tree/master/datos_de_prueba)). Contiene las plantillas de los ficheros para creación de contenido y las imágenes asociadas a usuarios, equipos y logros. Para su utilización, simplemente copiar las carpetas en la ruta que se haya especificado para el directorio de recursos compartidos (ver [configuración inicial](https://github.com/Aitorcay/RPClass/wiki/Configuraci%C3%B3n-inicial)]
