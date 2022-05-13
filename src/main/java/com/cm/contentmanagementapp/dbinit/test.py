import os
import mysql.connector

dB = mysql.connector.connect(
    host=os.environ.get('HOST'),
    user=os.environ.get('USER'),
    password=os.environ.get('PASSWORD'),
    database=os.environ.get('DB')

)

dBCursor = dB.cursor()

plantFile = open("plants.txt", 'r', encoding='utf-8')
tagFile = open("tags.txt", 'r', encoding='utf-8')

for tag in tagFile:
    t = tag.split(",", 1)
    value = t[0].strip()
    category = t[1].strip()
    sql = "INSERT INTO content_tag (category, value) VALUES (%s, %s)"
    val = (value, category)
    print(value, category)
    dBCursor.execute(sql, val)

dB.commit()

plants = []
for line in plantFile:
    # split scientific name, plant parameters
    split = line.split(",", 1)

    plant = {}
    scienceName = split[0].strip().title()
    genus = scienceName.split(" ", 1)[0]
    plant["scienceName"] = scienceName
    plant["genus"] = scienceName.split(" ", 1)[0]


    attributes = []

    # If plant has properties specified, else it just has scientific name
    if len(split) == 2:
        attributes = split[1].split(" -")
        plant["commonName"] = attributes[0].title()
        plant["type"] = attributes[1].split("=", 1)[1]
        plant["size"] = attributes[2].split("=", 1)[1]
        plant["water"] = attributes[3].split("=", 1)[1]
        plant["light"] = attributes[4].split("=", 1)[1]
        plant["petsafe"] = attributes[5].split("=", 1)[1]
        plant["difficulty"] = attributes[6].split("=", 1)[1].strip()

    plants.append(plant)

for plant in plants:
    print(plant)