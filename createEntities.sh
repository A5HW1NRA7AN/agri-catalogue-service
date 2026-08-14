#!/usr/bin/env bash
set -euo pipefail

entities=(
    "seed"
    "cropType"
    "cropVariety"
    "cropCategory"
    "livestock"
    "season"
    "soil"
    "extensionequipment"
    "pesticide"
    "insecticide"
    "fertilizer"
    "locationObject"
    "locationMapper"
    "marketPlace"
)

for entity in "${entities[@]}"; do
  echo "Creating entity: $entity"
  python3 main.py --action create --name "$entity"
done



# seed, cropType, cropVariety, cropCategory, livestock, season, soil, extensionequipment, pesticide, insecticide, fertilizer, locationObject, locationMapper, marketPlace