# SmartAlarm Logic

Un sistema de lógica de despertador inteligente implementado en Java. Permite crear, activar, desactivar y administrar múltiples alarmas con repetición semanal, sonidos personalizados, posponer alarmas, modo vacaciones y estadísticas de sueño.

## Objetivos

- Implementar una lógica de negocio separada de la interfaz.
- Modelar alarmas con diseño orientado a objetos.
- Gestionar múltiples alarmas con recurrencia semanal y categorías.
- Incluir características avanzadas: reto matemático para apagar alarmas, modo vacaciones y detección de conflictos.
- Documentar el diseño con UML y casos de uso.

## Tecnologías utilizadas

- Java 17+ (o Java 11 compatible)
- `java.time` para gestión de horarios
- Markdown para documentación

## Estructura del proyecto

- `Main.java`: punto de entrada de demostración.
- `src/main/java/com/smartalarm/model`: clases de dominio (`Alarm`, `Recurrence`, `SoundProfile`, `SleepStatistics`).
- `src/main/java/com/smartalarm/service`: servicios de gestión (`AlarmManager`, `SnoozeManager`).
- `docs`: documentación técnica, diagramas UML y casos de uso.
- `tests`: demostraciones de uso y pruebas manuales.

## Instalación y ejecución

1. Abrir la carpeta del proyecto en un entorno Java.
2. Compilar con:

```powershell
javac -d out src/main/java/com/smartalarm/Main.java src/main/java/com/smartalarm/model/*.java src/main/java/com/smartalarm/service/*.java
```

3. Ejecutar:

```powershell
java -cp out com.smartalarm.Main
```

## Ejecución de pruebas manuales

```powershell
javac -d out tests/AlarmManagerDemo.java Main.java src/main/java/com/smartalarm/model/*.java src/main/java/com/smartalarm/service/*.java
java -cp out AlarmManagerDemo
```

## Documentación

- `docs/Design.md`: explicación del modelo de clases, relaciones y decisiones de diseño.
- `docs/UseCases.md`: especificación completa de casos de uso.
- `docs/Diagrams.md`: descripción de las imágenes de diagramas y capturas de ejecución.
- `docs/SelfAssessment.md`: autoevaluación según los criterios de la práctica.
- `docs/AI-Reflection.md`: reflexión sobre el uso de IA durante el desarrollo.

## Rutas de imágenes

- `docs/diagrams/class-diagram.png`
- `docs/diagrams/usecase-diagram.png`
- `docs/screenshots/main-output.png`
- `docs/screenshots/demo-output.png`

## Características implementadas

- Creación, eliminación, activación y desactivación de alarmas.
- Configuración de hora, etiqueta, categoría y sonido personalizado.
- Repetición semanal flexible: días individuales, todos los días, días laborables y fines de semana.
- Posponer alarma con `SnoozeManager`.
- Detener alarma con un reto matemático para confirmar la acción.
- Consultar la próxima alarma activa.
- Modo vacaciones para suspender alarmas temporalmente.
- Detección de conflictos de alarmas cercanas.

## Arquitectura

El sistema está pensado como un dominio desacoplado de cualquier interfaz gráfica. `AlarmManager` orquesta la lógica, mientras que `Alarm`, `Recurrence` y `SoundProfile` se encargan del estado. `SnoozeManager` y `MathChallenge` encapsulan comportamientos especializados.
