#!/usr/bin/env bash
# ==============================================================================
# MiniBank - Build, Package & Execution Script
# Student: Bhavy Gol (ID: 25CS016)
# Subject: Object-Oriented Programming (CEUE203) - FTE, CHARUSAT
# ==============================================================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "=== Building MiniBank Project ==="
mkdir -p bin target

echo "[1/3] Compiling Main and Test Sources..."
javac -d bin $(find src/main/java -name "*.java") $(find src/test/java -name "*.java")

echo "[2/3] Creating Executable JAR (target/minibank.jar)..."
echo "Main-Class: com.minibank.MiniBank" > target/manifest.txt
jar cfm target/minibank.jar target/manifest.txt -C bin com

echo "[3/3] Running Automated Test Suite..."
java -cp bin com.minibank.MiniBankTest

echo ""
echo "Build succeeded!"
echo "To run interactive MiniBank Console Application:"
echo "  java -jar target/minibank.jar"
echo "  OR: java -cp bin com.minibank.MiniBank"
