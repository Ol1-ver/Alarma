# Reflexión sobre el uso de IA

## Herramientas utilizadas

- Copilot / asistente de IA en VS Code: para generar ideas de clases, estructuras y documentación técnica.

## Prompts utilizados

- "Diseña un modelo de clases Java para un despertador inteligente con repetición semanal, snooze y modo vacaciones."
- "Escribe una clase Java `AlarmManager` que gestione una colección de alarmas y detecte conflictos entre horas cercanas."
- "Crea una especificación de casos de uso para una aplicación de alarma sin interfaz gráfica."

## Código generado por IA

- Estructura inicial de clases: `Alarm`, `AlarmManager`, `SnoozeManager`, `MathChallenge`, `SleepStatistics`.
- Documentación de diseño y UML en Mermaid.

## Modificaciones y validaciones

- Revisé y ajusté las validaciones de hora/minuto.
- Ajusté la lógica de `nextActivation` para evitar valores nulos y asegurar recurrencia semanal.
- Validé manualmente la salida de `Main` y la coherencia de los métodos de gestión.

## Ventajas y limitaciones

- La IA aceleró la definición de clases y la creación de archivos iniciales.
- La IA no conoce el contexto exacto del repositorio, por lo que requerí verificar nombres de archivos y estructura.
- Es importante validar cada fragmento generado; por ejemplo, la lógica de repetición y la sincronización de snooze necesitó revisión.

## Mejores prácticas

- Usar IA para obtener ideas de diseño y no como sustituto de la verificación técnica.
- Documentar qué partes fueron generadas y cuáles se adaptaron.
- Probar manualmente los casos críticos.
