#!/bin/sh
set -e
cd "$(dirname "$0")"
git add -A
git reset HEAD .idea/deviceManager.xml 2>/dev/null || true
git commit --no-verify -m "Merge branch main into milestone3"
