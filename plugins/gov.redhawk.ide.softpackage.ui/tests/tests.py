#!/usr/bin/python

import sys
sys.path.append("../resources")

import unittest
import json

import packageProjectGenerator

class PackageProjectGeneratorTests(unittest.TestCase):
    
    def test_existLib(self):
        jsonString = open("existLib.json").read()
        data = json.loads(jsonString)
        packageProjectGenerator.processJsonInput(data)
    
    def test_basicCreateNewLibrary(self):
        jsonString = open("basicCreateNewLibrary.json").read()
        data = json.loads(jsonString)
        packageProjectGenerator.processJsonInput(data)

    def test_basicUseExistingLibrary(self):
        jsonString = open("basicUseExistingLibrary.json").read()
        data = json.loads(jsonString)
        packageProjectGenerator.processJsonInput(data)        
        
if __name__ == "__main__":
    unittest.main()
        