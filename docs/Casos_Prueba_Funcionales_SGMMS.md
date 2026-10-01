# Diseño de Casos de Prueba Funcionales — SGMMS (Entrega 1)

## RF1 – Gestionar Incidentes

**Sprint/Iteración:** Entrega 1

| ID Caso | Nombre Caso (Objetivo) | Precondición | Datos de Entrada | Pasos | Resultado Esperado |
|---|---|---|---|---|---|
| CP-RF1-01 | (Positivo) Registrar un incidente de tipo Robo válidamente | El sistema SGMMS está en ejecución y el Panel de Incidentes está activo. | id: "INC-101"<br>tipo: "ROBO"<br>ubicacion: "Zona Comercial A"<br>gravedad: "MEDIA"<br>descripcion: "Hurto en establecimiento" | 1. Ingresar al Panel de Incidentes.<br>2. Diligenciar el formulario con los datos de entrada.<br>3. Presionar el botón "Registrar Incidente". | El incidente "INC-101" se registra con éxito, cambia su estado a "pendiente" y se visualiza en la lista de incidentes activos y en el mapa. |
| CP-RF1-02 | (Negativo) Rechazar el registro de un incidente con tipo no reconocido o nulo | El sistema SGMMS está en ejecución y el Panel de Incidentes está activo. | id: "INC-102"<br>tipo: "INUNDACION"<br>ubicacion: "Vía Principal 2"<br>gravedad: "ALTA"<br>descripcion: "Desborde" | 1. Ingresar al Panel de Incidentes.<br>2. Ingresar el tipo no permitido "INUNDACION".<br>3. Presionar el botón "Registrar Incidente". | El sistema bloquea el registro, lanza una excepción de tipo no válido y muestra el mensaje: "Error: Tipo de incidente no reconocido". |

## RF2 – Gestionar la Prioridad de los Incidentes

**Sprint/Iteración:** Entrega 1

| ID Caso | Nombre Caso (Objetivo) | Precondición | Datos de Entrada | Pasos | Resultado Esperado |
|---|---|---|---|---|---|
| CP-RF2-01 | (Positivo) Consultar el incidente de mayor prioridad ordenado por gravedad y tiempo de desempate | Existen tres incidentes activos registrados:<br>1. INC-1 (Gravedad MEDIA, 10:00 AM)<br>2. INC-2 (Gravedad ALTA, 10:15 AM)<br>3. INC-3 (Gravedad ALTA, 10:05 AM). | Acción: Consulta de incidente prioritario. | 1. Hacer clic en la opción "Consultar mayor prioridad" en el Panel de Incidentes. | El sistema retorna e identifica el incidente INC-3, puesto que comparte la gravedad ALTA con INC-2 pero su hora de generación es más antigua. |
| CP-RF2-02 | (Negativo) Intentar consultar el incidente de mayor prioridad cuando la estructura está vacía | No existen incidentes activos registrados en el sistema (0 incidentes pendientes). | Acción: Solicitud de atención/consulta de prioridad. | 1. Hacer clic en "Atender Siguiente" o "Consultar mayor prioridad". | El sistema no falla ni se cierra; captura la condición de lista vacía y muestra el mensaje informativo: "No hay incidentes activos pendientes de atención". |

## RF3 – Gestionar Vehículos de Atención

**Sprint/Iteración:** Entrega 1

| ID Caso | Nombre Caso (Objetivo) | Precondición | Datos de Entrada | Pasos | Resultado Esperado |
|---|---|---|---|---|---|
| CP-RF3-01 | (Positivo) Actualizar el estado de un vehículo existente a "En Ruta" | El vehículo "AMB-01" está registrado en el sistema con estado "Disponible". | idVehiculo: "AMB-01"<br>nuevoEstado: "EN_RUTA" | 1. Consultar el vehículo "AMB-01" por su ID.<br>2. Modificar el estado a "EN_RUTA".<br>3. Confirmar la actualización. | El estado del vehículo "AMB-01" se actualiza correctamente en la tabla hash y el número de vehículos disponibles en el indicador decrece en 1. |
| CP-RF3-02 | (Negativo) Intentar consultar o actualizar un vehículo con un ID inexistente | El sistema se encuentra en ejecución. | idVehiculo: "PAT-999" (Inexistente) | 1. Ingresar el ID "PAT-999" en la barra de búsqueda de vehículos.<br>2. Hacer clic en "Buscar Vehículo". | El sistema lanza la excepción VehicleNotFoundException y despliega la advertencia: "Vehículo no encontrado en el sistema". |

## RF4 – Asignar Vehículos a Incidentes

**Sprint/Iteración:** Entrega 1

| ID Caso | Nombre Caso (Objetivo) | Precondición | Datos de Entrada | Pasos | Resultado Esperado |
|---|---|---|---|---|---|
| CP-RF4-01 | (Positivo) Asignar un vehículo compatible disponible a un incidente activo | Incidente "INC-201" de tipo "INCENDIO" está "pendiente". El vehículo "BOM-01" de tipo "CAMION_BOMBEROS" está "Disponible". | idIncidente: "INC-201"<br>idVehiculo: "BOM-01" | 1. Seleccionar el incidente "INC-201" en el Panel de Incidentes.<br>2. Seleccionar el vehículo candidato "BOM-01".<br>3. Hacer clic en "Asignar Vehículo". | La asignación es exitosa. "INC-201" cambia a estado "en proceso", "BOM-01" cambia a "EN_RUTA" y se vinculan mutuamente. |
| CP-RF4-02 | (Negativo) Impedir la asignación de un vehículo incompatible con el tipo de incidente | Incidente "INC-202" de tipo "INCENDIO" está "pendiente". Vehículo "PAT-01" de tipo "PATRULLA" está "Disponible". | idIncidente: "INC-202"<br>idVehiculo: "PAT-01" | 1. Seleccionar el incidente de incendio "INC-202".<br>2. Seleccionar la patrulla "PAT-01".<br>3. Hacer clic en "Asignar Vehículo". | El sistema bloquea la operación, lanza la excepción IncompatibleVehicleException y muestra el mensaje de error: "Asignación no permitida: Una Patrulla no puede atender un Incendio". |

---

## Instrucciones para el Estudiante

- El nombre del caso debe describir claramente qué comportamiento se está verificando.
- Cada caso debe estar asociado a un requerimiento o Historia de Usuario existente.
- El resultado esperado debe ser verificable y específico. No escribir 'Funciona correctamente'.
- Los pasos deben ser claros, numerados y reproducibles.
- Incluir casos válidos e inválidos aplicando técnicas de caja negra.
