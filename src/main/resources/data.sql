SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE user;
INSERT INTO yeogiwa.`user` (is_deleted,created_at,email,name,oauth2id,password,`role`) VALUES
	 (0,'2024-09-24 21:51:38','mathpaul3@gmail.com','엄호용','kakao 3663497654','$2a$10$gkAPFRkt0qUOdSI0LFSoNOP.nH2ryeV626ftDBGoF3zjwH9xpmKCe','ROLE_USER'),
	 (0,'2024-09-27 05:25:51','csh200310@kakao.com','최서현','kakao 3710838344','$2a$10$n2Ro7IRrFAI03v6DsVWMuefDiDigG47Uh31kB7zo3ndyBqGlATdXm','ROLE_USER');
SET FOREIGN_KEY_CHECKS = 1;
