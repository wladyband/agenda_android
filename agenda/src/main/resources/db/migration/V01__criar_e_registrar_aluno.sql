
CREATE TABLE aluno (
    codigo SERIAL,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL,
    momentodecadastro DATE NULL,
	PRIMARY KEY(codigo)
);


INSERT INTO aluno (nome, email, telefone, momentodecadastro) values ('Wladimir', 'wladimir@qintess.com', '2018-01-27', '2018-01-27');
INSERT INTO aluno (nome, email, telefone, momentodecadastro) values ('Alex', 'alex@qintess.com', '2018-01-27', '2018-01-27');
