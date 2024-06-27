INSERT INTO states (name, uf)
VALUES ('Paraná', 'PR'),
       ('Santa Catarina', 'SC');

INSERT INTO cities (path_image, name, state_id)
-- VALUES ('https://i.imgur.com/qMKc4rf.png', 'Guarapuava', '1');
VALUES ('https://i.imgur.com/qMKc4rf.png', 'Guarapuava', '1'), ('https://www.viajeparana.com/sites/viaje-parana/arquivos_restritos/files/imagem/2019-04/igreja_nossa_senhora_da_gloria_ca.jpg', 'Pitanga', '1');

INSERT INTO addresses (neighborhood, number, postal_code, street, city_id)
VALUES ('Industrial', '800', '85053525', 'Avenida Professora Laura Pacheco Bastos', '1');

-- ROLE_ADMIN: Administrador
-- Senha: qwerty123
-- id 1
INSERT INTO users
(email, email_verified, name, phone_number, phone_verified, profile_picture, profile_verified,
 address_id, rating, profile, password, description)
VALUES ('admin@mail.com', 'true', 'Administrador', '(42) 99999-9995', 'true',
        null, 'false', '1', 3, 'ROLE_ADMIN', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Descrição profissional 1');

-- ROLE_USER: Usuário comum
-- Senha: qwerty123
-- ids do 2 ao 6
INSERT INTO users
(email, email_verified, name, phone_number, phone_verified, profile_picture, profile_verified,
 address_id, rating, profile, password, description)
VALUES ('duarte@mail.com', 'true', 'Duarte Trindade', '(42) 99999-9991', 'true',
        'https://res.cloudinary.com/dgueb0wir/image/upload/v1717708789/images/perfil01_bpevao.png', 'true', '1', 2, 'ROLE_USER','$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Realizo serviços de qualidade.'),
       ('marcelo@mail.com', 'true', 'Marcelo Macedo', '(42) 99999-9992', 'true',
        'https://res.cloudinary.com/dgueb0wir/image/upload/v1717708789/images/perfil04_kq1jfw.png', 'true', '1', 4,'ROLE_USER', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Há 10 anos no mercado se prestação de serviços!'),
       ('luis@mail.com', 'true', 'Luís Almeida', '(42) 99999-9993', 'true',
        'https://res.cloudinary.com/dgueb0wir/image/upload/v1717708790/images/perfil03_ymoozz.png', 'true', '1', 5, 'ROLE_USER', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Possuo uma loja na cidade, onde acabo utilizando os serviços do sistema para encontrar os profissionais.'),
       ('lucas@mail.com', 'true', 'Lucas Santos', '(42) 99999-9994', 'true',
        'https://i.imgur.com/owhNAKK.png', 'true', '1', 1, 'ROLE_USER', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Quando preciso utilizo a plataforma.'),
       ('guilherme@mail.com', 'true', 'Guilherme Oliveira', '(42) 99999-9995', 'false',
        null, 'false', '1', 3, 'ROLE_USER', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Natural de Guarapuava.'),
       ('tais.hrissay@unisaude.co.ao', 'true', 'Michele', '(42) 99993-5496', 'true',
            'https://res.cloudinary.com/dgueb0wir/image/upload/v1717708790/images/perfil03_ymoozz.png', 'true', '1', 1, 'ROLE_USER', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Atuo em diversas áreas!');

--ROLE_COMPANY: Empresa
-- Senha: qwerty123
-- ids do 7 ao 8
INSERT INTO users
(email, email_verified, name, phone_number, phone_verified, profile_picture, profile_verified,
 address_id, rating, profile, password, description)
VALUES ('casacontrucao@mail.com', 'true', 'Casa Construção', '(42) 99999-9994', 'true',
        'https://i.imgur.com/owhNAKK.png', 'true', '1', 1, 'ROLE_COMPANY', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS',  'Prestação de serviço na área de construção'),
       ('faztudo@mail.com', 'true', 'Faz Tudo', '(42) 99999-9996', 'true',
        null, 'true', '1', 2, 'ROLE_COMPANY', '$2a$10$ZqgnFnwi6/8qjELs5.Y7rOXacIu/vbudYDl4vA55KDvDuGcpaEFzS', 'Empresa com diversos profissionais de todas as áreas!');

INSERT INTO individuals (cpf, gender, birth_date, id)
VALUES ('982.988.640-93', 'MASCULINE', '2003-01-01', '2'),
       ('998.045.450-47', 'MASCULINE', '2000-01-01', '3'),
       ('130.218.260-91', 'MASCULINE', '2001-01-01', '4'),
       ('619.487.532-19', 'MASCULINE', '2001-01-01', '5'),
       ('814.541.391-53', 'MASCULINE', '2001-01-01', '6');

INSERT INTO categories (name)
VALUES ('Animais de estimação'),
       ('Artesanato'),
       ('Assistência técnica'),
       ('Educação e cursos'),
       ('Veículos'),
       ('Consultoria'),
       ('Casa e construção'),
       ('Design e Tecnologia'),
       ('Comer e beber'),
       ('Eventos'),
       ('Moda e Beleza'),
       ('Diversão, Lazer e Esportes'),
       ('Saúde'),
       ('Serviços domésticos');


INSERT INTO expertises (name, description, path_icon, category_id)
VALUES ('Encanador', 'Realiza serviço como encanador', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176874/servicebook/expertises/encanador_q7b9vg.svg', 7),
       ('Eletricista', 'Realiza serviço como eletricista', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176874/servicebook/expertises/eletricista_tlqro5.svg', 7),
       ('Pintor', 'Realiza serviço como pintor', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176875/servicebook/expertises/pintor_kfl3de.svg', 7),
       ('Mecânico', 'Realiza serviço como mecânico', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176876/servicebook/expertises/mecanico_quczum.svg', 5);

INSERT INTO professional_expertises (expertise_id, professional_id, rating, description)
VALUES (1, 2, 1, 'descrição'),
       (2, 2, 2, 'descrição'),
       (3, 2, 3, 'descrição'),
       (4, 2, 4, 'descrição'),
       (2, 5, 4, 'descrição'),
       (2, 7, 4, 'descrição'),
       (2, 4, 4, 'descrição'),
       (1, 9, 5, 'descrição');

INSERT INTO services (name, description, allow_scheduling, path_icon, expertise_id)
VALUES ('Balanceamento', 'Balanceamento das rodas', false, 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176876/servicebook/expertises/mecanico_quczum.svg', 4),
       ('Troca de óleo', 'Troca do óleo do motor', false, 'https://res.cloudinary.com/dgueb0wir/image/upload/v1689176876/servicebook/expertises/mecanico_quczum.svg', 4);

-- INSERT INTO PROFESSIONAL_SERVICE_OFFERINGS (DESCRIPTION,DURATION,NAME,PRICE,TYPE,UNIT,SERVICE_ID, USER_ID, EXPERTISE_ID, ID)
-- values ('Balanceamento  Teste', '1 Hora','Balanceamento', 9.00, 'INDIVIDUAL', 'Hora', 1, 5, 4, 1),
--  ('Balanceamento com máquina importada1', '2 Horas', 'O balanceamento com 100% de garantia',219000,'COMBINED_PACKAGE','Hora', 2, 5, 4, 2);

-- INSERT INTO professional_service_offerings (name, description, price, unit, duration, service_id, user_id, type, id)
-- VALUES ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'INDIVIDUAL', 1),
--  ('Balanceamento com máquina importada1', 'O balanceamento com 100% de garantia', 219000, 'Hora', '2 Horas', 1, 2, 'COMBINED_PACKAGE', 2);
--  ('Balanceamento com máquina importada2', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'INDIVIDUAL', 3),
--  ('Balanceamento com máquina importada3', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'INDIVIDUAL', 4);
--        (null, null, 2, 2);

-- INSERT INTO professional_service_package_offering (name, description, price, unit, duration, service_id, user_id, type, amount,id)
-- VALUES ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'SIMPLE_PACKAGE', 2, 1),
--  ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'COMBINED_PACKAGE', 2, 2);
--  ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'SIMPLE_PACKAGE', 2, 3),
--  ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'SIMPLE_PACKAGE', 2, 4),
--  ('Balanceamento com máquina importada', 'O balanceamento com 100% de garantia', 19000, 'Hora', '2 Horas', 1, 2, 'SIMPLE_PACKAGE', 2, 5);

INSERT INTO companies (cnpj, id)
VALUES ('98.988.640/0001-91', 8),
       ('98.988.640/0001-91', 9);

INSERT INTO company_professionals (company_id, professional_id, rating, is_confirmed)
VALUES (8, 2, 1, true),
       (9, 3, 1, true),
       (9, 2, 1, false);

-- INSERT INTO job_requests
-- (client_confirmation, date_created, date_target, description, professional_confirmation, quantity_candidators_max,
--  status, client_id, expertise_id)
-- VALUES ('false', '2024-01-01', '2024-01-01', 'Preciso de um Encanador 1!', 'true', '5', 'AVAILABLE', '2', '1'),
--        ('false', '2024-01-01', '2024-01-02', 'Preciso de um Encanador 2!', 'true', '10', 'AVAILABLE', '2', '1'),
--        ('false', '2024-01-01', '2024-01-07', 'Preciso de um Encanador 3!', 'true', '15', 'AVAILABLE', '2', '1'),
--        ('true', '2024-01-01', '2024-01-07', 'Preciso de um Encanador 30!', 'true', '15', 'TO_HIRED', '2', '1'),
--        ('false', '2024-01-01', '2024-01-08', 'Preciso de um Encanador 4!', 'true', '20', 'TO_DO', '2', '1'), -- 4
--        ('false', '2024-01-01', '2024-01-08', 'Preciso de um Encanador 5!', 'true', '20', 'DOING', '2', '1'),
--        ('false', '2024-01-01', '2024-01-08', 'Preciso de um Encanador 6!', 'true', '20', 'CANCELED', '2', '1'),
--        ('false', '2022-11-29', '2022-12-13', 'Preciso de um Encanador 7!', 'true', '20', 'DOING', '3', '1'),
--        ('false', '2024-01-01', '2024-01-08', 'Preciso de um Encanador 8!', 'true', '20', 'DOING', '3', '1'),
--        ('false', '2024-01-01', '2024-01-30', 'Preciso de um Eletricista 1!', 'true', '5', 'TO_HIRED', '3', '2'),--10
--        ('false', '2024-01-01', '2024-02-02', 'Preciso de um Eletricista 2!', 'true', '10', 'CLOSED', '3', '2'),
--        ('true', '2024-01-01', '2024-03-01', 'Preciso de um Eletricista 3!', 'true', '15', 'TO_HIRED', '4', '2'),
--        ('false', '2024-01-01', '2024-04-01', 'Preciso de um Eletricista 4!', 'true', '20', 'TO_DO', '4', '2'), -- 13
--        ('false', '2024-01-01', '2024-04-01', 'Preciso de um Eletricista 5!', 'true', '20', 'CLOSED', '4', '2'),
--        ('false', '2024-01-01', '2024-04-01', 'Preciso de um Eletricista 6!', 'true', '20', 'CLOSED', '4', '2'),
--        ('true', '2024-01-01', '2024-01-01', 'Preciso de um Pintor 1!', 'true', '5', 'TO_HIRED', '4', '3'),
--        ('true', '2024-01-01', '2024-01-02', 'Preciso de um Pintor 2!', 'true', '10', 'CLOSED', '4', '4'),
--        ('true', '2024-01-01', '2024-01-03', 'Preciso de um Pintor 3!', 'true', '15', 'TO_HIRED', '4', '3'),
--        ('true', '2023-01-01', '2024-01-04', 'Preciso de um Pintor 4!', 'true', '20', 'TO_DO', '3', '3'), -- 19
--        ('true', '2023-07-01', '2024-01-04', 'Preciso de um Pintor 5!', 'true', '20', 'CLOSED', '4', '3'),
--        ('true', '2023-04-01', '2024-01-04', 'Preciso de um Pintor 6!', 'true', '20', 'DOING', '4', '3'),
--        ('true', '2023-04-26', '2024-08-27', 'Preciso de um Mecânico 1!', 'true', '5', 'AVAILABLE', '4', '4'),
--        ('true', '2023-05-26', '2024-08-28', 'Preciso de um Mecânico 2!', 'true', '10', 'AVAILABLE', '4', '4'),
--        ('true', '2023-03-26', '2024-09-01', 'Preciso de um Mecânico 3!', 'true', '15', 'AVAILABLE', '4', '4'),
--        ('true', '2023-02-26', '2024-09-10', 'Preciso de um Mecânico 4!', 'true', '20', 'AVAILABLE', '4', '4'),
--        ('true', '2023-02-16', '2023-01-17', 'Preciso de um Mecânico 5!', 'true', '20', 'CLOSED', '4', '4');


--
-- INSERT INTO job_contracted (comments, rating, job_request, professional, hired_date, todo_date)
-- VALUES ('Ótimo Encanador!', '5', '19', '5', '2024-04-01', '2024-08-26'),
--        ('Ótimo Eletricista!', '5', '10', '2', '2024-04-01', '2024-08-26'),
--        ('Ótimo Pintor!', '5', '16', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Encanador!', '3', '5', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Encanador!', '3', '8', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Encanador!', '3', '9', '2', '2024-04-01', '2024-08-26'),
--        ('Péssimo Encanador!', '1', '6', '6', '2024-04-01', '2024-08-26'),
--        ('Bom Eletricista!', '3', '13', '2', '2024-06-20', '2024-06-24'),
--        ('Péssimo Eletricista!', '1', '12', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Pintor!', '3', '15', '2', '2024-04-01','2024-08-26'),
--        ('Bom Pintor!', '3', '20', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Pintor!', '3', '17', '2', '2024-04-01', '2024-08-26'),
--        ('Bom Pintor!', '3', '1', '2', '2024-05-01', '2024-05-26'),
--        ('Bom Pintor!', '3', '4', '2', '2024-05-01', '2024-05-26'),
--        ('Péssimo Pintor!', '1', '14', '2', '2024-04-01', '2024-08-26');
--
-- INSERT INTO job_candidates (job_id, professional_id, chosen_by_budget)
-- VALUES ('1', '2', 'false'),
--        ('1', '3', 'true'),
--        ('1', '6', 'false'),
--        ('2', '2', 'false'),
--        ('3', '2', 'true'),
--        ('6', '2', 'false'),
--        ('17', '2', 'false'),
--        ('4', '2', 'false'),
--        ('6', '6', 'true'),
--        ('13', '2', 'true');

INSERT INTO follows (client_id, professional_id)
VALUES (4, 2);

-- DURAÇÃO DE SERVIÇOS
INSERT INTO duration_services (name)
VALUES ('Sem agendamento'),
       ('30 minutos'),
       ('1 hora'),
       ('1 hora e meia'),
       ('2 horas'),
       ('3 horas'),
       ('Um período do dia'),
       ('O dia inteiro');

-- UNIDADE DE PREÇO
INSERT INTO price_units (name)
VALUES ('Hora'),
       ('Metro quadrado'),
       ('Unidade');


-- INSERT INTO payments (id, payment_id, status)
-- VALUES (1, 1, 'approved');
--
-- INSERT INTO assessment_professionals (id, comment, date, quality, client_id, job_request_id, professional_id)
-- VALUES (1, 'Serviço de eletricista impecável: pontual, profissional e com excelente qualidade, garantindo segurança e satisfação. Recomendo fortemente!', '2024-06-01', 4, 4, 14, 2),
--        (2, 'Pontual', '2024-06-02', 4, 4, 14, 5);
--
-- INSERT INTO assessment_responses (id,response, date, assessment_professional, professional_id)
-- VALUES (1,'Muito obrigado pela preferência', '2024-06-01', 1, 2),
--        (2,'Muito obrigada', '2024-06-01', 2, 5);
--
-- INSERT INTO assessment_professionals_files (id,path_image, assessment_professional_id)
-- VALUES (1,'http://res.cloudinary.com/dgueb0wir/image/upload/v1718155628/txvfo0gilijknjsrnsqr.jpg', 1),
--        (2,'http://res.cloudinary.com/dgueb0wir/image/upload/v1718155628/txvfo0gilijknjsrnsqr.jpg', 2);

-- INSERT INTO job_images (id, path, job_request_id)
-- VALUES (1,'https://res.cloudinary.com/dgueb0wir/image/upload/v1713823542/jobs/yof94o7nvsmeay8g1xto.jpg', 14),
--        (2,'https://res.cloudinary.com/dgueb0wir/image/upload/v1713823339/jobs/hghg8tnxh96zz5yo7nva.jpg', 14),
--        (3,'https://res.cloudinary.com/dgueb0wir/image/upload/v1713823542/jobs/yof94o7nvsmeay8g1xto.jpg', 1),
--        (4,'https://res.cloudinary.com/dgueb0wir/image/upload/v1713823542/jobs/yof94o7nvsmeay8g1xto.jpg', 2),
--        (5,'https://res.cloudinary.com/dgueb0wir/image/upload/v1713823339/jobs/hghg8tnxh96zz5yo7nva.jpg', 3);
