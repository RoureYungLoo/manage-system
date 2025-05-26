#!/bin/bash
javac -d ./out/ ./src/com/randolflu/atm/*.java ./src/com/randolflu/atm/constants/Constants.java
jar cvfm ATM.jar MANIFEST.MF -C ./out/ .
