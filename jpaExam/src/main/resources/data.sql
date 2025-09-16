-- Routes
INSERT INTO route (id, route_name, type) VALUES (1, 'E21', 'Expreso');
INSERT INTO route (id, route_name, type) VALUES (2, 'E31', 'Expreso');
INSERT INTO route (id, route_name, type) VALUES (3, 'T31', 'Troncal');
INSERT INTO route (id, route_name, type) VALUES (4, 'A16', 'Alimentador');

-- Buses
INSERT INTO bus (id, license_plate, route_id) VALUES (1, 'AAA111', 1);
INSERT INTO bus (id, license_plate, route_id) VALUES (2, 'BBB222', 1);
INSERT INTO bus (id, license_plate, route_id) VALUES (3, 'CCC333', 2);
INSERT INTO bus (id, license_plate, route_id) VALUES (4, 'DDD444', 2);
INSERT INTO bus (id, license_plate, route_id) VALUES (5, 'EEE555', 3);
INSERT INTO bus (id, license_plate, route_id) VALUES (6, 'FFF666', 3);
INSERT INTO bus (id, license_plate, route_id) VALUES (7, 'GGG777', 4);
INSERT INTO bus (id, license_plate, route_id) VALUES (8, 'HHH888', 4);
INSERT INTO bus (id, license_plate, route_id) VALUES (9, 'III999', 1);
INSERT INTO bus (id, license_plate, route_id) VALUES (10, 'JJJ000', 2);
INSERT INTO bus (id, license_plate, route_id) VALUES (11, 'KKK111', 3);
INSERT INTO bus (id, license_plate, route_id) VALUES (12, 'LLL222', 4);

-- GeoPoints
-- Bus 1
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (1, 3.4516, -76.5320, '2025-09-12 10:00:00', 1);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (2, 3.4521, -76.5330, '2025-09-12 10:10:00', 1);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (3, 3.4530, -76.5340, '2025-09-12 10:20:00', 1);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (4, 3.4700, -76.5000, '2025-09-12 11:00:00', 1);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (5, 3.4720, -76.5050, '2025-09-12 11:11:40', 1);
-- Bus 2
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (6, 3.3700, -76.5400, '2025-09-12 12:00:00', 2);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (7, 3.3710, -76.5410, '2025-09-12 12:13:20', 2);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (8, 3.4000, -76.5200, '2025-09-12 13:00:00', 2);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (9, 3.4010, -76.5215, '2025-09-12 13:10:00', 2);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (10, 3.4025, -76.5230, '2025-09-12 13:20:00', 2);
-- Bus 3
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (11, 3.5516, -76.6320, '2025-09-13 14:00:00', 3);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (12, 3.5521, -76.6330, '2025-09-13 14:10:00', 3);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (13, 3.5530, -76.6340, '2025-09-13 14:20:00', 3);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (14, 3.5700, -76.6000, '2025-09-13 15:00:00', 3);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (15, 3.5720, -76.6050, '2025-09-13 15:11:40', 3);
-- Bus 4
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (16, 3.2700, -76.4400, '2025-09-13 16:00:00', 4);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (17, 3.2710, -76.4410, '2025-09-13 16:13:20', 4);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (18, 3.5000, -76.4200, '2025-09-13 17:00:00', 4);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (19, 3.5010, -76.4215, '2025-09-13 17:10:00', 4);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (20, 3.5025, -76.4230, '2025-09-13 17:20:00', 4);
-- Bus 5
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (21, 3.6000, -76.7000, '2025-09-14 09:00:00', 5);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (22, 3.6010, -76.7010, '2025-09-14 09:05:00', 5);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (23, 3.6020, -76.7020, '2025-09-14 09:10:00', 5);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (24, 3.6030, -76.7030, '2025-09-14 09:15:00', 5);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (25, 3.6040, -76.7040, '2025-09-14 09:20:00', 5);
-- Bus 6
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (26, 3.6100, -76.7100, '2025-09-14 10:00:00', 6);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (27, 3.6110, -76.7110, '2025-09-14 10:05:00', 6);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (28, 3.6120, -76.7120, '2025-09-14 10:10:00', 6);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (29, 3.6130, -76.7130, '2025-09-14 10:15:00', 6);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (30, 3.6140, -76.7140, '2025-09-14 10:20:00', 6);
-- Bus 7
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (31, 3.6200, -76.7200, '2025-09-14 11:00:00', 7);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (32, 3.6210, -76.7210, '2025-09-14 11:05:00', 7);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (33, 3.6220, -76.7220, '2025-09-14 11:10:00', 7);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (34, 3.6230, -76.7230, '2025-09-14 11:15:00', 7);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (35, 3.6240, -76.7240, '2025-09-14 11:20:00', 7);
-- Bus 8
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (36, 3.6300, -76.7300, '2025-09-14 12:00:00', 8);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (37, 3.6310, -76.7310, '2025-09-14 12:05:00', 8);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (38, 3.6320, -76.7320, '2025-09-14 12:10:00', 8);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (39, 3.6330, -76.7330, '2025-09-14 12:15:00', 8);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (40, 3.6340, -76.7340, '2025-09-14 12:20:00', 8);
-- Bus 9
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (41, 3.7000, -76.8000, '2025-09-15 09:00:00', 9);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (42, 3.7010, -76.8010, '2025-09-15 09:05:00', 9);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (43, 3.7020, -76.8020, '2025-09-15 09:10:00', 9);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (44, 3.7030, -76.8030, '2025-09-15 09:15:00', 9);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (45, 3.7040, -76.8040, '2025-09-15 09:20:00', 9);
-- Bus 10
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (46, 3.7100, -76.8100, '2025-09-15 10:00:00', 10);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (47, 3.7110, -76.8110, '2025-09-15 10:05:00', 10);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (48, 3.7120, -76.8120, '2025-09-15 10:10:00', 10);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (49, 3.7130, -76.8130, '2025-09-15 10:15:00', 10);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (50, 3.7140, -76.8140, '2025-09-15 10:20:00', 10);
-- Bus 11
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (51, 3.7200, -76.8200, '2025-09-15 11:00:00', 11);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (52, 3.7210, -76.8210, '2025-09-15 11:05:00', 11);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (53, 3.7220, -76.8220, '2025-09-15 11:10:00', 11);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (54, 3.7230, -76.8230, '2025-09-15 11:15:00', 11);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (55, 3.7240, -76.8240, '2025-09-15 11:20:00', 11);
-- Bus 12
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (56, 3.7300, -76.8300, '2025-09-15 12:00:00', 12);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (57, 3.7310, -76.8310, '2025-09-15 12:05:00', 12);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (58, 3.7320, -76.8320, '2025-09-15 12:10:00', 12);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (59, 3.7330, -76.8330, '2025-09-15 12:15:00', 12);
INSERT INTO geo_point (id, latitude, longitude, timestamp, bus_id) VALUES (60, 3.7340, -76.8340, '2025-09-15 12:20:00', 12);