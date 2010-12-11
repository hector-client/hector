#!/bin/bash
# 
# providing the argument "tag" to this script will create and commit the tag 
# for this release. Ie:
#    ./release.sh tag
#
# Exit on error
set -e
# Require variable declaration
set -u

# read the version from pom.xml
version=$(sed -n "s/<hector.version>\(.*\)<\/hector.version>/\1/p" pom.xml | head -1)
# remove whitespace
version=$(echo $version)
echo Version is: $version
target="releases/hector-$version"
rm -rf $target*
mkdir -p $target

echo Running mvn install and copy-dependencies
mvn clean install -DskipTests dependency:copy-dependencies -DincludeScope=runtime -DexcludeTransitive=true -DexcludeArtifactIds=properties-maven-plugin -DoutputDirectory=../$target

cp core/target/hector-core-$version* $target

echo Copying CHANGELOG
cp CHANGELOG $target

echo Zipping
pushd releases
zip -Tr hector-$version.zip hector-$version/
popd
if [[ $1 && "$1" = "tag" ]]; then
  vstr="v$version"
  echo "Dropping tag $vstr"
  git tag -a $vstr -m "Tagged as $vstr from release script"
  git push origin $vstr
fi

echo DONE
