#!/usr/bin/env python3
# Copyright (C) 2026 ska_987. SPDX-License-Identifier: GPL-3.0-only
from pathlib import Path
import zipfile

root = Path(__file__).resolve().parent
output = root / "assets/hora-ohipa-source.zip"

with zipfile.ZipFile(output, "w", zipfile.ZIP_DEFLATED) as archive:
    for folder in ["src", "res", "assets", "tests"]:
        for path in sorted((root / folder).rglob("*")):
            if path.is_file() and path.suffix != ".zip":
                archive.write(path, "hora-ohipa-android/" + str(path.relative_to(root)))

    for name in [
        "AndroidManifest.xml",
        "build.sh",
        "package_source.py",
        "LICENSE",
        "NOTICE",
        "README.md",
        "CHANGELOG.md",
        "SOURCES-FACTURATION.md",
        "verify-tax.cjs",
        "verify-backup.cjs",
    ]:
        archive.write(root / name, "hora-ohipa-android/" + name)
