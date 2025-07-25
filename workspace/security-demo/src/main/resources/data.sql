
insert into users (username, password, enabled) values ('tim', '$2a$12$88svamGyOGD9iQkbVoTrROaSbIV.llKpX6tXwib840aE8m10GY/t.', 1);
insert into users (username, password, enabled) values ('anna', '$2a$12$Jn9fRnSc/klGg5pu9ntmW.kvtxE65AIcUs8hCa3WXxKYDxsMrpccq', 1);

insert into authorities(username, authority) values ('tim', 'ROLE_USER');
insert into authorities(username, authority) values ('anna', 'ROLE_USER');
insert into authorities(username, authority) values ('anna', 'ROLE_ADMIN');