# Autoevaluación

Esta autoevaluación valora el trabajo realizado según los criterios establecidos en la práctica.

## Criterios y valoración

| Criterio | Peso | Valoración | Comentario |
|---|---|---|---|
| Diseño orientado a objetos | 25% | 23/25 | El diseño incluye clases claras y responsables, con buen desacoplo. Falta un poco de refinamiento en la separación de responsabilidades en `AlarmManager`.
| Calidad del código | 20% | 18/20 | El código está modularizado, con nombres claros y validación de datos. Queda margen en las pruebas unitarias formales.
| Uso correcto de Git/GitHub | 15% | 12/15 | El repositorio está inicializado y hay ramas `feature` y `develop`. Sin embargo, el historial no refleja completamente un desarrollo incremental real con commits de cada feature.
| Diagramas UML | 15% | 13/15 | Se incluye documentación UML y rutas para imágenes. Falta añadir las exportaciones finales en PNG.
| Especificación de casos de uso | 10% | 9/10 | Los casos de uso están completos y detallados. Podría mejorarse con diagramas de flujo adicionales.
| Documentación README | 10% | 9/10 | El README es profesional y completo. Se puede enriquecer con enlaces directos a las imágenes y al archivo de autoevaluación.
| Reflexión sobre IA | 5% | 5/5 | Se ha documentado el uso de IA, sus ventajas y limitaciones de forma clara.

## Puntuación total estimada

- **Total: 89/100**

## Justificación

- La implementación cumple los requisitos funcionales y avanzados propuestos.
- La documentación técnica está presente y organizada.
- El uso de Git es adecuado, aunque la historia de commits no es perfecta para un flujo incremental real.
- Las evidencias de ejecución se han preparado, pero las capturas PNG finales deben insertarse en `docs/screenshots`.

## Mejoras futuras

- Añadir pruebas unitarias con JUnit para validar casos clave.
- Exportar los diagramas UML a PNG y añadirlos al repositorio.
- Refinar el gestor de alarmas para reducir la responsabilidad de `AlarmManager`.
- Vincular el repositorio a GitHub y usar Pull Requests reales.
