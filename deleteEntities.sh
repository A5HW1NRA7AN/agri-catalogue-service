#!/usr/bin/env bash
set -euo pipefail

entities=(
  "seed"
  "cropType"
  "cropVariety"
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
  echo "Deleting entity: $entity"
  python3 main.py --action delete --name "$entity"
done
