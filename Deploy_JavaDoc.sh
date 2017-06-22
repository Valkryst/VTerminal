#!/bin/bash

set -o errexit -o nounset

if [ "$TRAVIS_BRANCH" != "master" ]
then
  echo "This commit was made against the $TRAVIS_BRANCH and not the master! No deploy!"
  exit 0
fi

rev=$(git rev-parse --short HEAD)

cd /home/travis/build/Valkryst/VTerminal/target/site/apidocs/

git init
git config user.name "Valkryst"
git config user.email "valkryst@valkryst.com"

git remote add upstream "https://$GH_TOKEN@github.com/Valkryst/VTerminal.git"
git fetch upstream
git reset upstream/gh-pages

touch .

git add -A .
git commit -m "Rebuilds JavaDoc pages at ${rev}."
git push -q upstream HEAD:gh-pages
