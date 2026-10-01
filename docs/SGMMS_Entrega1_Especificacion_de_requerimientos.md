# Análisis y especificación del problema y los requerimientos funcionales

**Proyecto:** SGMMS – Sistema de Gestión y Monitoreo de Movilidad y Seguridad  
**Entrega:** Tarea Integradora – Entrega 1

| | |
|---|---|
| **Cliente** | Alcaldía Municipal de Palmira |
| **Usuario** | Operador del Centro de Monitoreo Urbano |
| **Contexto del problema** | La ciudad de Palmira enfrenta retos significativos en movilidad y seguridad ciudadana debido a la congestión vehicular y a la ocurrencia constante de incidentes como accidentes de tránsito, robos e incendios. Actualmente la administración no posee una herramienta centralizada que integre el monitoreo de emergencias y la capacidad de respuesta. El proyecto SGMMS (Sistema de Gestión y Monitoreo de Movilidad y Seguridad) busca implementar una plataforma interactiva en Java con interfaz gráfica que simule el comportamiento urbano en un mapa 2D. Desde esta herramienta, el operador podrá registrar e inspeccionar incidentes activos, evaluar su gravedad organizándolos por prioridad, consultar el estado de la flota de vehículos de atención (patrullas, ambulancias, camiones de bomberos) y realizar asignaciones eficientes y validadas de unidades según el tipo de emergencia. Las restricciones del sistema imponen el desarrollo bajo el patrón MVC, el no uso de APIs o mapas externos ni bases de datos, y la prohibición del uso de librerías de colecciones estándar de Java (ArrayList, HashMap, etc.), exigiendo la implementación propia de las estructuras de datos. |

## Requerimientos funcionales (listado)

- RF1- Gestionar incidentes: Registrar, consultar y actualizar incidentes de tipo accidente, robo e incendio, incluyendo su ID, ubicación, gravedad, fecha/hora de generación, descripción, estado y vehículo asignado.
- RF2- Gestionar la prioridad de los incidentes: Organizar los incidentes activos según su gravedad (Alta > Media > Baja) y aplicar un criterio de desempate por antigüedad cuando tengan la misma prioridad, permitiendo consultar y atender el incidente de mayor prioridad.
- RF3- Gestionar vehículos de atención: Registrar, consultar y controlar la ubicación y los cambios de estado (Disponible, En ruta, Atendiendo, Fuera de servicio) de los vehículos de emergencia.
- RF4- Asignar vehículos a incidentes: Validar y permitir la asignación de vehículos disponibles y compatibles según el tipo de incidente, proponer un candidato adecuado y restringir asignaciones no permitidas.

## Requerimientos no funcionales (listado)

- RNF1- Arquitectura y Lenguaje: El sistema debe desarrollarse en Java bajo el patrón de arquitectura Modelo-Vista-Controlador (MVC).
- RNF2- Usabilidad e Interfaz Gráfica: La interfaz debe ser clara, comprensible y mantener coherencia visual en los escenarios de Centro de Monitoreo, Mapa de Tráfico y Panel de Incidentes.
- RNF3- Control de Excepciones y Robustez: El sistema debe controlar situaciones anómalas mediante excepciones personalizadas con mensajes claros para el usuario, evitando cierres inesperados de la aplicación.
- RNF4- Consistencia de Datos: El sistema debe garantizar la sincronización y consistencia atómica entre las diferentes estructuras de datos en memoria (Tabla Hash, Árbol Binary Search Tree y Colas de Prioridad) al registrar, modificar o eliminar entidades.

## Requerimientos de proceso (listado)

- RP1- Control de Versiones: El proyecto debe ser gestionado colaborativamente mediante Git y la estrategia Gitflow (main, develop, ramas feat/) con el reporte progresivo de indicadores de calidad en al menos 15 commits equi-temporales.
- RP2- Estándar de Repositorio: Toda la documentación del proyecto debe alojarse en la carpeta doc/ en formato Markdown.
- RP3- Verificación y Calidad: Se deben implementar pruebas unitarias automatizadas con JUnit para las estructuras de datos y la lógica de dominio.

## Especificación de requerimientos funcionales

### RF1 - Gestionar incidentes

**Resumen:** El sistema debe permitir el registro automático o manual de incidentes de tipo accidente, robo e incendio en la simulación. Además, debe permitir la consulta detallada de sus propiedades y la actualización de su estado ("pendiente", "en proceso", "resuelto") a lo largo del ciclo de vida del evento. Cada registro debe almacenarse simultáneamente en el índice Hash por ID y en la estructura de prioridad para mantener la consistencia del sistema.

**Entradas**

| Nombre entrada | Tipo de dato | Condición o valores válidos |
|---|---|---|
| id | String | Cadena alfanumérica única (mínimo 3, máximo 15 caracteres. Ej. "INC-001") |
| tipo | String | Solo valores: "ACCIDENTE", "ROBO", "INCENDIO" |
| ubicacion | String | Nombre de zona o coordenadas válidas dentro del mapa 2D |
| gravedad | String | Solo valores: "ALTA", "MEDIA", "BAJA" |
| descripcion | String | Cadena de texto descriptiva (máximo 200 caracteres) |
| fechaHora | LocalDateTime | Fecha y hora válida de generación |

**Resultado o Postcondición:** El incidente queda creado con estado "pendiente", visible en los paneles y sincronizado en las estructuras centrales (índice hash por ID y estructura de prioridad).

**Salidas**

| Nombre salida | Tipo de dato | Formato |
|---|---|---|
| id | String | Texto único registrado |
| tipo | String | Texto del tipo de incidente. |
| ubicacion | String | Texto de la ubicación. |
| gravedad | String | Texto del nivel de gravedad. |
| descripcion | String | Texto descriptivo registrado. |
| estado | String | "pendiente", "en proceso" o "resuelto" |
| fechaHora | LocalDateTime | Fecha y hora de generación (dd/MM/yyyy HH:mm:ss). |
| vehiculoAsignado | String | ID del vehículo asignado o "Ninguno". |

### RF2 - Gestionar la prioridad de los incidentes

**Resumen:** El sistema debe organizar los incidentes activos según su gravedad (Alta > Media > Baja) y aplicar como criterio de desempate la antigüedad (fecha/hora de generación, el más antiguo primero) cuando dos incidentes tengan la misma gravedad. Debe permitir consultar cuál es el incidente activo de mayor prioridad y atenderlo (extraerlo) desde el Panel de Incidentes.

**Entradas**

| Nombre entrada | Tipo de dato | Condición o valores válidos |
|---|---|---|
| accion | String | Solo valores: "CONSULTAR_MAYOR_PRIORIDAD" o "ATENDER_SIGUIENTE" |
| incidentesActivos | Colección de Incident | Debe existir al menos un incidente activo (no resuelto) para poder consultar o atender |

**Resultado o Postcondición:** La estructura de prioridad se mantiene ordenada por gravedad y antigüedad. Si la acción es "ATENDER_SIGUIENTE", el incidente de mayor prioridad se extrae de la estructura y queda listo para asignación.

**Salidas**

| Nombre salida | Tipo de dato | Formato |
|---|---|---|
| incidenteMayorPrioridad | Incident | ID, tipo, ubicación, gravedad y fecha/hora del incidente más prioritario. |
| totalActivos | int | Entero >= 0 con la cantidad de incidentes activos restantes. |
| mensaje | String | Texto informativo (p. ej. "No hay incidentes activos" cuando la estructura está vacía). |

### RF3 - Gestionar vehículos de atención

**Resumen:** El sistema debe permitir registrar y consultar los vehículos de atención (patrulla, ambulancia, camión de bomberos), su tipo, su ubicación en el mapa y su estado (Disponible, En ruta, Atendiendo, Fuera de servicio), controlando que los cambios de estado durante la atención de un incidente sigan un ciclo válido.

**Entradas**

| Nombre entrada | Tipo de dato | Condición o valores válidos |
|---|---|---|
| idVehiculo | String | Cadena alfanumérica única (mínimo 3, máximo 15 caracteres. Ej. "VEH-01") |
| tipoVehiculo | String | Solo valores: "PATRULLA", "AMBULANCIA", "CAMION_BOMBEROS" |
| ubicacion | Position (fila, columna) | Coordenadas dentro de los límites del mapa y sobre una celda transitable |
| estado | String | Solo valores: "DISPONIBLE", "EN_RUTA", "ATENDIENDO", "FUERA_DE_SERVICIO" |

**Resultado o Postcondición:** El vehículo queda registrado en la tabla hash por ID con su estado actualizado. Solo se aceptan transiciones válidas (Disponible → En ruta → Atendiendo → Disponible; cualquier estado ↔ Fuera de servicio).

**Salidas**

| Nombre salida | Tipo de dato | Formato |
|---|---|---|
| idVehiculo | String | Texto único registrado |
| tipoVehiculo | String | Texto del tipo de vehículo. |
| ubicacion | Position | Coordenadas (fila, columna). |
| estado | String | Estado vigente del vehículo. |
| incidenteAsignado | String | ID del incidente asignado o "Ninguno". |

### RF4 - Asignar vehículos a incidentes

**Resumen:** El sistema debe permitir al operador asignar un vehículo a un incidente validando que el vehículo esté disponible y sea compatible con el tipo de incidente (patrulla: robos y apoyo en accidentes; ambulancia: accidentes; camión de bomberos: incendios), que el incidente no esté resuelto ni en proceso con otro vehículo, y proponiendo un vehículo candidato adecuado. Toda asignación inválida se informa mediante una excepción personalizada y no genera puntos.

**Entradas**

| Nombre entrada | Tipo de dato | Condición o valores válidos |
|---|---|---|
| idIncidente | String | ID existente en el índice de incidentes; el incidente debe estar en estado "pendiente" |
| idVehiculo | String | ID existente en el registro de vehículos; el vehículo debe estar "DISPONIBLE" y ser compatible con el tipo de incidente |

**Resultado o Postcondición:** El incidente pasa a "en proceso" con el vehículo asignado, el vehículo pasa a "EN_RUTA" y la asignación queda registrada en el incidente y en el vehículo. Si la asignación es inválida no se modifica ningún estado.

**Salidas**

| Nombre salida | Tipo de dato | Formato |
|---|---|---|
| resultadoAsignacion | boolean | true si la asignación fue aceptada; false si fue rechazada. |
| idIncidente | String | ID del incidente atendido. |
| idVehiculo | String | ID del vehículo asignado. |
| mensaje | String | Mensaje de confirmación o descripción del error (InvalidAssignmentException). |
