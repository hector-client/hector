#!/bin/bash
# Exit on error
set -e
# Require variable declaration
set -u

# read the version from pom.xml
version=$(sed -n "s/<version>\(.*\)<\/version>/\1/p" pom.xml | head -1)
# remove whitespace
version=$(echo $version)
echo Version is: $version
target="releases/hector-$version"
rm -rf $target*
mkdir -p $target

echo Running mvn install and copy-dependencies
mvn clean install -DskipTests dependency:copy-dependencies -DincludeScope=runtime -DexcludeTransitive=true -DexcludeArtifactIds=properties-maven-plugin -DoutputDirectory=$target

cp target/hector-$version* $target

echo Copying CHANGELOG
cp CHANGELOG $target

echo Zipping
pushd releases
zip -Tr hector-$version.zip hector-$version/
popd

echo DONE
