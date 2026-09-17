#!/usr/bin/env bash
set -euo pipefail

# All catalogue entities, comma-separated - main.py deletes the whole list in one run.
#
# Note: the "audit" catalogue is also generated from these templates but is deliberately
# NOT listed here - its service impl is hand-maintained (AuditService backs AuditLogService),
# so it must not be swept away by this script.
entities="seed,cropType,cropVariety,cropCategory,livestock,livestockBreed,livestockCategory,season,soil,extensionequipment,pesticide,insecticide,fertilizer,locationObject,locationConfig,marketPlace"

# --skipSchema true keeps the hand-filled payloadValidation and EsFieldsmapping JSONs.
# Extra flags are forwarded and override the default, e.g.
#   ./deleteEntities.sh --skipSchema false   # also remove those JSONs
python3 main.py --name "$entities" --action delete --skipSchema true "$@"
