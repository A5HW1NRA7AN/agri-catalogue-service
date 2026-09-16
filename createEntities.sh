#!/usr/bin/env bash
set -euo pipefail

# All catalogue entities, comma-separated - main.py generates the whole list in one run.
#
# Note: the "audit" catalogue is also generated from these templates but is deliberately
# NOT listed here - its service impl is hand-maintained (AuditService backs AuditLogService),
# so it must not be regenerated or swept away by deleteEntities.sh.
entities="seed,cropType,cropVariety,cropCategory,livestock,livestockBreed,livestockCategory,season,soil,extensionequipment,pesticide,insecticide,fertilizer,locationObject,locationConfig,marketPlace"

# Extra flags are forwarded, e.g. ./createEntities.sh --skipSchema true
python3 main.py --name "$entities" --action create
