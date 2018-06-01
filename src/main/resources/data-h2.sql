insert into Day (day_id, name) values (1, 'Monday');
insert into Day (day_id, name) values (2, 'Tuesday');
insert into Day (day_id, name) values (3, 'Wednesday');
insert into Day (day_id, name) values (4, 'Thursday');
insert into Day (day_id, name) values (5, 'Friday');
insert into Day (day_id, name) values (6, 'Saturday');
insert into Day (day_id, name) values (7, 'Sunday');


-- All reserved and no weekends
insert into Parking (parking_id, name, opening_Hour, closing_Time, total_Space,
free_Space, latitude, longitude) 
values (1, 'Parking Parque del Príncipe - Caceres', 9, 22, 50, 0, 39.4759703, -6.38041769);
insert into Parking_Day (parking_id, day_id) select 1, day_id from Day where day_id < 6;

-- Few reserved and only weekends
insert into Parking (parking_id, name, opening_Hour, closing_Time, total_Space, free_Space, latitude, longitude) 
values (2, 'Parking Canovas - Caceres', 8, 20, 20, 19, 39.42, -6.31);
insert into Parking_Day (parking_id, day_id) select 2, day_id from Day where day_id > 5;

-- Always open
insert into Parking (parking_id, name, opening_Hour, closing_Time, total_Space,
free_Space, latitude, longitude) 
values (3, 'Parking Plasencia - Plasencia', 0, 23, 10, 5, 40.01243, -6.14341);
insert into Parking_Day (parking_id, day_id) select 3, day_id from Day;