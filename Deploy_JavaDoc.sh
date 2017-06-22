#!/bin/bash

echo -e "Repo Slug:\t$TRAVIS_REPO_SLUG"
echo -e "JDK Version:\t$TRAVIS_JDK_VERSION"
echo -e "Pull Request:\t$TRAVIS_PULL_REQUEST"
echo -e "Branch:\t$TRAVIS_BRANCH"

if [ "$TRAVIS_REPO_SLUG" == "Valkryst/VTerminal" ] && [ "$TRAVIS_JDK_VERSION" == "java-8-oracle" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing javadoc...\n"

  cp -R /home/travis/build/Valkryst/VTerminal/target/site/apidocs/ $HOME/javadoc-latest

  cd $HOME
  git config --global user.name "Valkryst"
  git config --global user.email "valkryst@valkryst.com"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/Valkryst/VTerminal gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc
  cp -Rf /home/travis/build/Valkryst/VTerminal/target/site/apidocs/ ./javadoc
  git add -f .
  git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"
  
fi
