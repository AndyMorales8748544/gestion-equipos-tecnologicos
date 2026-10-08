# Sistema de Gestión de Equipos Tecnológicos

Aplicación web desarrollada para gestionar y administrar equipos tecnológicos de una organización.

El sistema permite registrar, consultar, actualizar, eliminar y buscar equipos tecnológicos, como laptops, monitores, impresoras y proyectores.

## Descripción del proyecto

Este proyecto implementa una arquitectura monolítica utilizando Spring Boot y el patrón de capas Controller, Service, Repository y Entity.

La aplicación utiliza Thymeleaf para la interfaz web y MySQL para almacenar la información de los equipos tecnológicos.

Su objetivo es facilitar el control y la administración de los equipos mediante una interfaz moderna, intuitiva y fácil de utilizar.

## Tecnologías utilizadas

### Backend

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- Lombok

### Frontend

- HTML5
- CSS3
- Thymeleaf
- JavaScript (confirmación de eliminación)

### Base de datos

- MySQL 8

### Herramientas de desarrollo

- Visual Studio Code
- Apache Maven
- Git y GitHub
- SonarQube for IDE

### Arquitectura

- Arquitectura monolítica
- Patrón de capas: Controller, Service, Repository y Entity

## Funcionalidades del sistema

### Gestión de equipos tecnológicos

- **Registrar equipos:** permite ingresar nuevos equipos con su nombre, tipo, marca, número de serie, fecha de registro y estado.
- **Listar equipos:** muestra los equipos registrados en una tabla.
- **Editar equipos:** permite actualizar la información de un equipo existente.
- **Eliminar equipos:** permite eliminar registros previa confirmación del usuario.
- **Buscar equipos:** permite buscar equipos por nombre, sin distinguir entre mayúsculas y minúsculas.

### Estados de los equipos

Cada equipo puede tener uno de los siguientes estados:

- **Disponible:** equipo que se encuentra disponible para su uso.
- **Asignado:** equipo que está siendo utilizado.
- **Mantenimiento:** equipo que requiere revisión o reparación.

### Interfaz de usuario

- Diseño moderno y adaptable a diferentes tamaños de pantalla.
- Formularios con validación de campos obligatorios.
- Indicadores visuales para identificar el estado de los equipos.
- Navegación entre la página principal, el listado y los formularios.

## Estructura del proyecto

El sistema utiliza una arquitectura monolítica organizada en capas para separar las responsabilidades de cada componente.

```text
gestion-equipos/
├── database/
│   └── 01-schema.sql
├── src/
│   ├── main/
│   │   ├── java/pe/edu/usil/gestionequipos/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   │   └── impl/
│   │   │   └── AppConfig.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── fonts/
│   │       ├── templates/
│   │       │   ├── equipos/
│   │       │   └── index.html
│   │       └── application.properties.example
│   └── test/
├── pom.xml
└── README.md
```

### Responsabilidades de las capas

- **Controller:** recibe las solicitudes del usuario y gestiona la navegación entre las vistas.
- **Service:** contiene la lógica de negocio y coordina las operaciones del sistema.
- **Repository:** permite consultar y modificar los registros de MySQL mediante Spring Data JPA.
- **Entity:** representa los datos de los equipos y su relación con la tabla de la base de datos.
- **Templates:** contiene las páginas HTML procesadas mediante Thymeleaf.
- **Static:** almacena los estilos CSS y la tipografía utilizada en la interfaz.
- **Database:** contiene el script SQL necesario para crear la estructura de la base de datos.

## Instalación y ejecución

### Requisitos previos

Para ejecutar este proyecto localmente, se necesitan las siguientes herramientas:

- Java JDK 25
- MySQL Server 8
- Git
- Un editor de código, como Visual Studio Code

El proyecto incluye Maven Wrapper (`mvnw` y `mvnw.cmd`), por lo que no es obligatorio instalar Maven por separado.

### Configuración de la base de datos

1. Iniciar el servicio de MySQL.
2. Abrir MySQL Workbench o un cliente SQL compatible.
3. Ejecutar el archivo `database/01-schema.sql`.
4. Verificar que se haya creado la base de datos `gestion_equipos` y la tabla `equipo`.

### Configuración de la aplicación

1. Ir a `src/main/resources/`.
2. Crear una copia de `application.properties.example`.
3. Renombrar la copia como `application.properties`.
4. Configurar el usuario y la contraseña de MySQL correspondientes al entorno local.

El archivo `application.properties` está excluido de Git para evitar publicar credenciales.

### Ejecución de la aplicación

1. Abrir una terminal en la carpeta principal del proyecto.
2. Verificar que el servicio de MySQL esté funcionando.
3. Ejecutar el siguiente comando en Windows:

```cmd
mvnw.cmd spring-boot:run
```

4. Esperar a que Spring Boot termine de iniciar.

Si la aplicación se ejecuta correctamente, aparecerá un mensaje similar a:

```text
Tomcat started on port 8080
Started AppConfig
```

5. Abrir el navegador y acceder a:

http://localhost:8080

Desde la página principal se puede ingresar al módulo de Gestión de Equipos Tecnológicos.

### Detener la aplicación

Para detener Spring Boot, presionar `Ctrl + C` en la terminal donde se está ejecutando.

**Nota:** El puerto 8080 debe estar disponible. Si otra aplicación está utilizándolo, será necesario detenerla antes de iniciar este proyecto.
