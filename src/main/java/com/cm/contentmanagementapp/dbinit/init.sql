CREATE TABLE plants (
                        trail_id bigserial PRIMARY KEY,
                        sum_of_ratings int4,
                        avg_rating float8,
                        name varchar(255),
                        region_region_id int8,
                        ratings int4,
                        has_image boolean DEFAULT false,
                        image_url varchar(1000)
);