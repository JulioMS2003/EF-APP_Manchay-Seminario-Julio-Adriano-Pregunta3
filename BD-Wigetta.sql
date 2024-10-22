create Database Wigetta;
use Wigetta;
 
create table usuario (

	id Int Primary Key Auto_Increment,
    codigo varchar(30) not null,
    password varchar(100) not null,
    email varchar(60) not null,
    rol varchar(30) not null,
	activo boolean not null
);

Insert Into Usuario Values
(Null, 'Adriano2k03',
'0h6UMPH2P96AHD3DffkPdNKI7q6W8IvtInR7OPuNRa8', -- Jade2003
'julimanchay4862@gmail.com', 'ADMIN', True);

