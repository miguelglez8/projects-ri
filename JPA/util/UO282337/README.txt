Miguel González Navarro
UO282337

Casos de uso:
- Contratos
- Tipos de contrato

Ampliaciones realizadas por mi cuenta:
- Mecánicos

Comentarios:
- El warning referido al JRE lo he tenido que dejar ya que si no (al modificarlo) el proyecto se me corrompe y no funciona nada
- He cambiado los tests de Dominio de Invoice, para que el resultado salga truncado, ya que daba error de redondeo (los test realizaban Round.TwoCents())
- Se han añadido los nuevos test de eliminar mecánico por id, ya que los que había buscaban por dni (unica modificacion nombre interfaz MechanicService y declaracion en el ServiceFactory, para que compilara)
- En Contract, he dado por supuesto que el findByMechanic es por la id del mecánico debido a que lo indica el javadoc del service, y por lo tanto he modificado los test para que se buscara por id del mecánico en vez de por dni
- He realizado todos los métodos de la interfaz de servicio Invoice