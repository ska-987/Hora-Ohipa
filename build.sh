#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

: "${ANDROID_SDK_ROOT:?Indiquez le chemin du SDK Android}"
BT="$ANDROID_SDK_ROOT/build-tools/35.0.0"
[ -d "$BT" ] || BT="$ANDROID_SDK_ROOT/android-15"
JAR="$ANDROID_SDK_ROOT/platforms/android-35/android.jar"
[ -f "$JAR" ] || JAR="$ANDROID_SDK_ROOT/android-35/android.jar"

VERSION_NAME="$(python3 - <<'PY'
import xml.etree.ElementTree as ET
root = ET.parse('AndroidManifest.xml').getroot()
print(root.attrib['{http://schemas.android.com/apk/res/android}versionName'])
PY
)"
OUTPUT="build/Hora-Ohipa-${VERSION_NAME}.apk"

python3 package_source.py
rm -rf build/classes build/dex
mkdir -p build/classes build/dex
"$BT/aapt2" compile --dir res -o build/res.zip
"$BT/aapt2" link -o build/unsigned.apk --manifest AndroidManifest.xml -I "$JAR" -A assets build/res.zip

if command -v javac >/dev/null; then
  javac -encoding UTF-8 -source 8 -target 8 -classpath "$JAR" -d build/classes src/fr/ska/mesheures/*.java
else
  java -jar "$ANDROID_SDK_ROOT/ecj.jar" -encoding UTF-8 -source 8 -target 8 -classpath "$JAR" -d build/classes src/fr/ska/mesheures/*.java
fi

(cd build/classes && zip -q -r ../classes.jar .)
"$BT/d8" --lib "$JAR" --min-api 26 --output build/dex build/classes.jar
(cd build/dex && zip -q -u ../unsigned.apk classes.dex)
"$BT/zipalign" -f -p 4 build/unsigned.apk build/aligned.apk

if [ -n "${SKA_KEYSTORE:-}" ]; then
  : "${SKA_STORE_PASS:?SKA_STORE_PASS est requis pour signer l'APK}"
  "$BT/apksigner" sign --ks "$SKA_KEYSTORE" --ks-key-alias ska --ks-pass env:SKA_STORE_PASS --out "$OUTPUT" build/aligned.apk
  "$BT/apksigner" verify --verbose "$OUTPUT"
  echo "APK signé : $OUTPUT"
else
  echo 'APK non signé : build/aligned.apk. Fournir SKA_KEYSTORE et SKA_STORE_PASS pour signer.'
fi
