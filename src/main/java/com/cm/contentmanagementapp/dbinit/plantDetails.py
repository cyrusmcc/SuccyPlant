import os
import mysql.connector
import requests
import json

dB = mysql.connector.connect(
    host=os.environ.get('HOST'),
    user=os.environ.get('USER'),
    password=os.environ.get('PASSWORD'),
    database=os.environ.get('DB')
)

dBCursor = dB.cursor()
plantFile = open("plants.txt", 'r', encoding='utf-8')
tagFile = open("tags.txt", 'r', encoding='utf-8')
writeFile = open("plantDetails.txt", 'a', encoding='utf-8')

# Add content tags to dB
#for tag in tagFile:
#    t = tag.split(",", 1)
#    value = t[0].strip()
#    category = t[1].strip()
#    sql = "INSERT INTO content_tag (category, value) VALUES (%s, %s)"
#    val = (value, category)
#    print(value, category)
#    dBCursor.execute(sql, val)

#dB.commit()

waterCondition = {"WD": "plant-name tend to be tolerant of drought and should be watered once the soil has had a "
                        "chance to dry out. Soil may take a week or two to dry between waterings but this dependent "
                        "on climate & the light level provided.",
                  "THD": "plant-name should be watered once the top few inches of soil has had a chance to dry out.",
                  "SM": "plant-name tend to be thirsty plants and soil should be kept slightly moist. Do not allow "
                        "soil to dry out completely. plant-name should be watered once the top few inches of soil "
                        "begin to feel almost dry. "}
lightCondition = {"AL": "plant-name tend to do well in most lighting situations and are tolerant of low-light "
                        "environments. If this plant is placed in a bright area, indirect lighting or partial shade "
                        "in best.",
                  "IPS": "plant-name thrive best in indirect lighting. Direct sunlight should be avoided if possible.",
                  "BND": "plant-name thrives in bright but indirect lighting. Direct sunlight should be avoided as "
                         "should very low light conditions.",
                  "BPSO": "plant-name thrives in bright, direct lighting. Direct sunlight is preferable but light "
                          "partial shading is tolerable. "
                  }
petSafeAndSize = {
    "psno": "plant-name are toxic for house pets and can cause health complications if ingested. 'plant-name' should "
            "be kept in areas unreachable by pets and can be closely monitored.",
    "psyes": "plant-name are not known to be toxic for house pets and make for great houseplants for those with dogs "
             "or cats in their homes!",
    "small": "plant-name make great houseplants for those with limited space. When fully grown & indoors, plant-name "
             "tend not to exceed ~2ft (61cm) in height.",
    "medium": "plant-name make great houseplants for those with a of extra room. When fully grown indoors, plant-name "
              "can exceed 2ft (61cm) in height but usually do not exceed 5ft (152.4cm) in height.",
    "large": "plant-name make great houseplants for those with room to spare. When fully grown indoors, plant-name "
             "are known to exceed 5ft (152.4cm) in height. "
}

plants = []
for line in plantFile:
    # split scientific name, plant parameters
    split = line.split(",", 1)

    plant = {}
    postTags = []
    scienceName = split[0].strip().title()
    genus = scienceName.split(" ", 1)[0]
    plant["scienceName"] = scienceName
    plant["genus"] = scienceName.split(" ", 1)[0]

    # If plant has properties specified, else it just has scientific name
    attributes = []
    if len(split) == 2:
        attributes = split[1].split(" -")
        if (len(attributes) == 7):
            plant["commonName"] = attributes[0].title()
            plant["type"] = attributes[1].split("=", 1)[1]

            plant["size"] = attributes[2].capitalize().split("=", 1)[1]
            if plant["size"] == "Small":
                plant["sizeDesc"] = petSafeAndSize.get("small")
            elif plant["size"] == "Medium":
                plant["sizeDesc"] = petSafeAndSize.get("medium")
            elif plant["size"] == "Large":
                plant["sizeDesc"] = petSafeAndSize.get("large")

            plant["water"] = attributes[3].split("=", 1)[1]
            if plant["water"] == "WD":
                plant["waterDesc"] = waterCondition.get("WD")
            elif plant["water"] == "THD":
                plant["waterDesc"] = waterCondition.get("THD")
            elif plant["water"] == "SM":
                plant["waterDesc"] = waterCondition.get("SM")

            plant["light"] = attributes[4].split("=", 1)[1]
            if plant["light"] == "AL":
                plant["lightDesc"] = lightCondition.get("AL")
            elif plant["light"] == "IPS":
                plant["lightDesc"] = lightCondition.get("IPS")
            elif plant["light"] == "BND":
                plant["lightDesc"] = lightCondition.get("BND")
            elif plant["light"] == "BPSO":
                plant["lightDesc"] = lightCondition.get("BPSO")

            plant["petsafe"] = attributes[5].capitalize().split("=", 1)[1]
            if plant["petsafe"] == "No":
                plant["petDesc"] = petSafeAndSize.get("psno")
            elif plant["petsafe"] == "Yes":
                plant["petDesc"] = petSafeAndSize.get("psyes")

            plant["difficulty"] = attributes[6].split("=", 1)[1].strip()

    plants.append(plant)

for plant in plants: print(plant)

for plant in plants:
    scienceName = plant["scienceName"]
    scienceName = scienceName.replace(" ", "_")
    scienceName = scienceName.capitalize()

    response = requests.get("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro"
                            "&explaintext&redirects=1&titles=" + scienceName).json()
    parseResponseScience = response["query"]
    parseResponseScience = parseResponseScience["pages"]

    # If no wikipedia entry found w/ plant's scientific name
    # try w/ plant's common name
    if list(parseResponseScience)[0] == "-1":
        commonName = plant["commonName"]
        commonName = commonName.replace(" ", "_")
        commonName = commonName.capitalize()
        response = requests.get("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro"
                                "&explaintext&redirects=1&titles=" + commonName).json()

        parseResponseCommon = response["query"]
        parseResponseCommon = parseResponseCommon["pages"]
        # If no entry w/ common name, set description to empty
        if list(parseResponseCommon)[0] == "-1":
            plant["desc"] = "N/A"
        else:
            plant["desc"] = list(parseResponseCommon.values())[0]['extract']

    # If extract exists w/
    else:
        plant["desc"] = list(parseResponseScience.values())[0]['extract']
        print(plant)

    writeFile.write(json.dumps(plant) + "\n")

writeFile.close()
