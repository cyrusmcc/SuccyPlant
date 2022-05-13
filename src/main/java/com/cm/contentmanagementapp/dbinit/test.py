file = open("plants.txt", 'r', encoding='utf-8')

for line in file:
    split = line.split(",", 1)
    scienceName = split[0].strip()
    scienceName = scienceName.title()
    print(scienceName)
    attributes = []

    # If plant has properties specified, else it just has scientific name
    if len(split) == 2:
        attributes = split[1].split(" -")
        commonName = attributes[0].title()
