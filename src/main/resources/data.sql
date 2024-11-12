SET FOREIGN_KEY_CHECKS = 0;
 TRUNCATE TABLE user;
INSERT INTO yeogiwa.`user` (is_deleted,created_at,email,name,oauth2id,password,`role`) VALUES
	 (0,'2024-09-24 21:51:38','mathpaul3@gmail.com','엄호용','kakao 3663497654','$2a$10$gkAPFRkt0qUOdSI0LFSoNOP.nH2ryeV626ftDBGoF3zjwH9xpmKCe','ROLE_USER'),
	 (0,'2024-09-27 05:25:51','csh200310@kakao.com','최서현','kakao 3710838344','$2a$10$n2Ro7IRrFAI03v6DsVWMuefDiDigG47Uh31kB7zo3ndyBqGlATdXm','ROLE_USER'),
	 (0,'2024-09-27 20:20:49','yeogiwa.test1@kakao.com','여기와유저','kakao 3722712034','$2a$10$ncZwXwq89eQTAODBUggcg.jH6p5sDKQfTe79vJrY76SmF1VCdSUe.','ROLE_USER'),
     (0,'2024-09-29 20:04:49','cars0106@naver.com','정지혁','kakao 3712022168','$2a$10$eFajYqMSZ0aTz1LZHowYr.AHSyzMLGrhyt8aidrcBxjRef7xjcUni','ROLE_ADMIN'),
     (0,'2024-09-30 16:09:57','jeehouk0106@gmail.com','정지혁','kakao 3726449519','$2a$10$3s3FNX7yZiLZRd2gdAIs9.JAEXaIwIx6v7w7kxQZjIflYY1jzHnle','ROLE_USER'),
     (0,'2024-10-02 03:05:38','yeogiwa.ad@kakao.com','여기와.manger','kakao 3728568537','$2a$10$NJSD4vDTZ8fzy1nnJLHID.vuZjSn5f3cWdOTv.e7sU7QykMBEKxvm','ROLE_ADMIN');
SET FOREIGN_KEY_CHECKS = 1;
