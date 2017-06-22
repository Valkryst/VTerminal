#!/bin/bash

echo -e "Repo Slug:\t$TRAVIS_REPO_SLUG"
echo -e "JDK Version:\t$TRAVIS_JDK_VERSION"
echo -e "Pull Request:\t$TRAVIS_PULL_REQUEST"
echo -e "Branch:\t$TRAVIS_BRANCH"

canBuild="$TRAVIS_REPO_SLUG" == "Valkryst/VTerminal"
canBuild&="$TRAVIS_JDK_VERSION" == "oraclejdk8"
canBuild&="$TRAVIS_PULL_REQUEST" == "false"
canBuild&="$TRAVIS_BRANCH" == "master"

if [canBuild == true]; then
  echo -e "Publishing JavaDoc...\n"

  cp -R /home/travis/build/Valkryst/VTerminal/target/site/apidocs/ $HOME/javadoc-latest

  cd $HOME
  git config --global user.name "Valkryst"
  git config --global user.email "valkryst@valkryst.com"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/Valkryst/VTerminal gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc
  cp -Rf /home/travis/build/Valkryst/VTerminal/target/site/apidocs/ ./javadoc
  git add -f .
  git commit -m "Updates JavaDoc on successful Travis CI build. Build #$TRAVIS_BUILD_NUMBER auto-pushed to gh-pages."
  git push -fq origin gh-pages > /dev/null

  echo -e "Published JavaDoc to gh-pages.\n"
fi
