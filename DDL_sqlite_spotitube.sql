PRAGMA foreign_keys = ON;

CREATE TABLE "customer" ( 
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(128) NOT NULL,
    `firstName` varchar(50) NOT NULL,
    `lastName` varchar(50) NOT NULL,
    `token` varchar(128) DEFAULT NULL);

CREATE TABLE "playlist" ( 
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `owner` bigint(20) DEFAULT NULL,
  FOREIGN KEY(owner) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE SET NULL
  );

create table playlists_tracks
(
	playlist_id,
	track_id,
	availableOffline
)
;

create table tracks
(
	id,
	performer,
	title,
	url,
	album,
	publicationDate,
	description,
	length,
	trackType
)
;

