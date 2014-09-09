#!/usr/bin/python

from redhawk.packagegen.directoryPackageDependency import DirectoryPackageDependency
from redhawk.packagegen.cppPackageDependency import CppPackageDependency
from redhawk.packagegen.existingPackageDependency import ExistingPackageDependency

from getopt import getopt
import sys
import os
import json


def _createDirectoryPackageDependency(
        name,
        libraryLocation,
        implementation,
        outputDir,
        sharedLibraries=[],
        variant=""):
    '''
    Instantiate a package class instance for a directory package dependency.

    '''
    myDirectoryDependency = DirectoryPackageDependency(
        name=name,
        implementation=implementation,
        outputDir=outputDir,
        libraryLocation=libraryLocation,
        sharedLibraries=sharedLibraries,
        variant=variant)

    return myDirectoryDependency

def _createCppPackageDependency(
        type,
        name,
        implementation,
        outputDir,
        variant=""):

    myCppDependency = CppPackageDependency(
        type=type,
        name=name,
        implementation=implementation,
        outputDir=outputDir,
        variant=variant,
        )

    return myCppDependency

def _createExistingPackageDependency(
        type,
        name,
        implementation,
        outputDir,
        pkgConfig,
        variant=""):
    myExistingDependency = ExistingPackageDependency(
        type=type,
        name=name,
        implementation=implementation,
        outputDir=outputDir,
        variant=variant,
        pkgConfig=pkgConfig
        )

    return myExistingDependency

def processJsonInput(inputObject):
    libName = inputObject['name']
    outputDir = inputObject['outputDir']
    implObj = inputObject['impl']
    
    variant = implObj['variantName']
    
    for x in inputObject['library'].get('list', []):    
        type = x.get('typeName', None)
        implementation = x.get('implementation', None)
        sharedLibraries = x.get('libraryPaths', [])
        headerPaths = x.get('headerPaths', [])
        force = False
        buildRpm = False
        install = False
        
        pkgConfig = x.get('packageConfigurationPath', None)
       
        if implObj['createNewLibrary']:
            if type == "cpp":
                name = libName
                if not implementation:
                    implementation = implementation
        
                package = _createCppPackageDependency(
                    type=type,
                    name=name,
                    implementation=implementation,
                    outputDir=outputDir,
                    variant=variant)
            elif type == "directory":
                if not implementation:
                    implementation = "noarch"
        
                package = _createDirectoryPackageDependency(
                    name=name,
                    libraryLocation=[],
                    implementation=implementation,
                    outputDir=outputDir,
                    variant=variant)
            else:
                raise ValueError("Create New Library does not support type %s"%type)
        elif implObj['useExistingLibrary']:
            if type == "cpp":
                if (not libName) and (not pkgConfig):
                    raise ValueError("No libname or package config provided for existing lib")
                else:
                    name = libName
                if not implementation:
                    implementation = "noarch"
              
                package = _createExistingPackageDependency(
                    type=type,
                    name=name,
                    implementation=implementation,
                    outputDir=outputDir,
                    pkgConfig=pkgConfig,
                    variant=variant)
                
            elif type == "directory":
                if not implementation:
                    implementation = "noarch"
        
                package = _createDirectoryPackageDependency(
                    name=name,
                    libraryLocation=headerPaths,
                    implementation=implementation,
                    outputDir=outputDir,
                    sharedLibraries=sharedLibraries,
                    variant=variant)
            else:
                raise ValueError("Use existing Library does not support type %s"%type)
        
        package.writeXML()
    
        package.callCodegen(
            force=force,
            variant=variant)

if __name__ == "__main__":
    print "Started script..."
    jsonString = sys.stdin.read()
    inputObject = json.loads(jsonString)
    
    print "Input Data:"
    print inputObject
    
    processJsonInput(inputObject)
