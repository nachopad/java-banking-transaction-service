 /* Agrego un cliente titular */
insert into Cliente(Type,contraseña,Dni,Nombre,Email,Domicilio,Estado) values('T','password1','12345678','Ignacio Padilla','nacho@gmail.com','Bajo la viña',true);
 /* Agrego un cliente adherente */
insert into Cliente(Type,contraseña,Dni,Nombre,Email,Domicilio,Estado) values('ADH','password2','39230981','Facundo Romero','facu@gmail.com','Belgrano N°17 ',true);
 /* Agrego una cuenta a nombre del titular creado anteriormente */
insert into Cuenta_Bancaria(nro_cuenta,cuenta_titular,fecha_ingreso,saldo,estado,limite_extraccion)values ('100','1',"2020-03-25",'40000',true,'15000');
 /* Agrego el adherente creado anteriormente a la cuenta del titular */
insert into Titular_cliente(titular_id,id) values ('1','2');
/* Agrego un deposito a nombre del titular */
insert into movimiento(dni_operador,fecha_hora,importe,nombre_operador,operacion,saldo,cuenta_id) values ('12345678',"2022-11-11",'10000.0','Ignacio Padilla',true,'10000.0','1');
/* Agrego una extraccion a nombre del adherente */
insert into movimiento(dni_operador,fecha_hora,importe,nombre_operador,operacion,saldo,cuenta_id) values ('39230981',"2022-11-14",'5000.0','Facundo Romero',false,'5000.0','1');