# Biblioteca Java

Este proyecto es una aplicación de consola básica para la gestión de una biblioteca. Permite:

1. Registrar, buscar, listar, modificar y eliminar libros.
2. Registrar, listar, modificar y eliminar usuarios.
3. Buscar libros por título, autor o año de publicación.
4. Validar texto, año, teléfono chileno y e-mail durante el ingreso.

## Estructura

- `model`: entidades, validaciones y reglas del catálogo.
- `view`: interacción mediante la consola.
- `controller`: coordinación entre la vista, el modelo y la persistencia.
- `persistence`: lectura y escritura de `biblioteca.xlsx`.

## Requisitos

- Java Development Kit (JDK) 17 o superior.
- Una terminal (PowerShell, CMD, Bash u otra compatible).

Comprueba la instalación de Java con:

```bash
javac -version
```

## Compilación y ejecución

Desde la carpeta raíz del proyecto, ejecuta:

```bash
mvn compile
mvn exec:java "-Dexec.mainClass=Main"
```

En PowerShell también puedes usar los mismos comandos. La aplicación mostrará un menú interactivo; selecciona la opción `10` para salir.

La aplicación guarda automáticamente la información en `biblioteca.xlsx`, en la raíz del proyecto. El archivo contiene las hojas `Libros` y `Usuarios`, y se carga al iniciar el programa. Está excluido del control de versiones para no publicar datos locales.

## Autor

duckcode-dev

## Licencia

MIT
