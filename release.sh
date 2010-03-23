#!/bin/bash
# Exit on error
set -e
# Require variable declaration
set -u

echo Running mvn install
mvn clean install

# read the version from pom.xml
version=$(sed -n "s/<version>\(.*\)<\/version>/\1/p" pom.xml | head -1)
# remove whitespace
version=$(echo $version)

echo Version is: $version

echo Copying artifacts
target="releases/hector-$version"
rm -rf $target*
mkdir -p $target
cp target/hector-$version* $target

echo Copying lib jars
cp lib/* $target
cp antlib/* $target

echo Zipping
pushd releases
zip -Tr hector-$version.zip hector-$version/
popd

echo DONE
