# Especificación de casos de uso

```mermaid
usecaseDiagram
    actor Usuario
    Usuario --> (Crear alarma)
    Usuario --> (Posponer alarma)
    Usuario --> (Activar modo vacaciones)
    Usuario --> (Consultar próxima alarma activa)
    Usuario --> (Detener alarma)
    (Posponer alarma) .> (Detener alarma) : extend
    (Activar modo vacaciones) .> (Consultar próxima alarma activa) : include
```

## Caso de uso 1: Crear alarma

- Nombre: Crear alarma
- Objetivo: Añadir una nueva alarma con hora, etiqueta, sonido y repetición.
- Actor principal: Usuario
- Precondiciones: El usuario conoce la hora y las preferencias de sonido.
- Flujo principal:
  1. El usuario solicita crear una alarma.
  2. El sistema pide hora, minuto, etiqueta y repetición.
  3. El sistema valida los valores.
  4. El sistema almacena la alarma y la activa.
- Flujos alternativos:
  - Si una alarma tiene una hora inválida, el sistema muestra un error y pide corrección.
  - Si hay conflicto de alarma cercana, el sistema avisa con un mensaje y permite guardar.
- Postcondiciones: La alarma está registrada en el gestor.
- Reglas de negocio:
  - La hora debe estar entre 0 y 23.
  - El minuto debe estar entre 0 y 59.
  - La alarma puede repetirse en días concretos o ser de un solo uso.

## Caso de uso 2: Posponer alarma

- Nombre: Posponer alarma
- Objetivo: Retrasar una alarma que está sonando para que suene más tarde.
- Actor principal: Usuario
- Precondiciones: Una alarma está activa y el gestor la detecta.
- Flujo principal:
  1. El usuario solicita posponer la alarma.
  2. El sistema aplica la duración de snooze.
  3. El sistema registra la posposición en estadísticas.
- Flujos alternativos:
  - Si la alarma está desactivada, el sistema no aplica snooze.
- Postcondiciones: La alarma sonará de nuevo tras el intervalo de snooze.
- Reglas de negocio:
  - El tiempo de posponer debe ser positivo.
  - Cada posposición se contabiliza en estadísticas.

## Caso de uso 3: Detener alarma con reto matemático

- Nombre: Detener alarma con reto matemático
- Objetivo: Apagar la alarma solo después de resolver una operación aritmética.
- Actor principal: Usuario
- Precondiciones: Una alarma está sonando y el sistema ha generado un reto matemático.
- Flujo principal:
  1. El sistema presenta una operación matemática al usuario.
  2. El usuario envía la respuesta.
  3. Si la respuesta es correcta, el sistema detiene la alarma.
  4. Si la respuesta es incorrecta, el sistema pospone la alarma.
- Flujos alternativos:
  - Si el usuario no responde correctamente, el sistema pospone la alarma automáticamente.
- Postcondiciones: La alarma se detiene o se pospone en función de la respuesta.
- Reglas de negocio:
  - Solo la respuesta correcta apaga la alarma.
  - Las respuestas incorrectas provocan un posponer inmediato.

## Caso de uso 4: Activar modo vacaciones

- Nombre: Activar modo vacaciones
- Objetivo: Suspender temporalmente todas las alarmas.
- Actor principal: Usuario
- Precondiciones: Existen alarmas registradas.
- Flujo principal:
  1. El usuario activa el modo vacaciones.
  2. El sistema activa el estado de vacaciones.
  3. El sistema omite todas las alarmas activas hasta desactivarse.
- Flujos alternativos:
  - Si no hay alarmas registradas, el sistema sigue en modo vacaciones pero no hay efecto visible.
- Postcondiciones: Las alarmas no se disparan mientras dure el modo vacaciones.
- Reglas de negocio:
  - El modo vacaciones ignora el estado habilitado de cada alarma.

## Caso de uso 4: Consultar próxima alarma activa

- Nombre: Consultar próxima alarma activa
- Objetivo: Mostrar cuál es la siguiente alarma habilitada.
- Actor principal: Usuario
- Precondiciones: Al menos una alarma habilitada y no activado modo vacaciones.
- Flujo principal:
  1. El usuario pide la próxima alarma.
  2. El sistema calcula la siguiente activación basada en hora y repetición.
  3. El sistema muestra la alarma más temprana.
- Flujos alternativos:
  - Si no hay alarmas activas, el sistema informa que no hay próximas alarmas.
- Postcondiciones: Se identifica la siguiente alarma disponible.
- Reglas de negocio:
  - Las alarmas deshabilitadas o en modo vacaciones no se consideran.
