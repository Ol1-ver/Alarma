# Diagramas UML y capturas

Este documento describe los diagramas que acompañan al proyecto y las rutas donde deben colocarse las imágenes.

## Diagramas UML

### Diagrama de clases

- Archivo esperado: `docs/diagrams/class-diagram.png`
- Descripción: representa las clases principales del sistema, sus atributos y métodos clave, y las relaciones entre ellas.
- Contenido:
  - `AlarmManager`: orquesta alarmas, modo vacaciones, conflictos y estadísticas.
  - `Alarm`: mantiene estado de duración, hora, etiqueta, sonido, recurrencia y estado.
  - `Recurrence`: define los días de repetición semanal.
  - `SoundProfile`: modela el sonido de la alarma y el volumen.
  - `MathChallenge`: genera y valida el reto matemático para apagar alarmas.
  - `SnoozeManager`: gestiona el posponer de alarmas.
  - `SleepStatistics`: registra métricas de uso.

### Diagrama de casos de uso

- Archivo esperado: `docs/diagrams/usecase-diagram.png`
- Descripción: muestra los actores y las acciones principales del sistema.
- Contenido:
  - Actor principal: Usuario.
  - Casos de uso: Crear alarma, Posponer alarma, Detener alarma con reto matemático, Activar modo vacaciones, Consultar próxima alarma activa.
  - Relaciones include/extend donde procede.

## Capturas de ejecución

### Rutas de captura

- `docs/screenshots/main-output.png`
  - Captura con la salida de la ejecución principal desde `Main.java`.
- `docs/screenshots/demo-output.png`
  - Captura con la salida de la demo de pruebas desde `tests/AlarmManagerDemo.java`.

### Propósito

Estas imágenes son evidencias de que el código funciona sin errores y ayudan a documentar ejemplos de uso real.

## Cómo añadir las imágenes

1. Ejecutar el proyecto y verificar la salida.
2. Exportar los diagramas UML en formato PNG desde Mermaid o la herramienta de diagramas utilizada.
3. Guardar los archivos en las rutas indicadas.
4. Confirmar que `docs/Diagrams.md` y `README.md` referencian correctamente los ficheros.
