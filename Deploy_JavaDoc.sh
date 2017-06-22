#!/bin/bash

# Display Repo:
echo -e "Repo Slug:"
echo -e "\tExpected:Valkryst/VTerminal"
echo -e "\tActual:$TRAVIS_REPO_SLUG\n"

# Display JDK Version:
echo -e "JDK Version:"
echo -e "\tExpected:oraclejdk8"
echo -e "\tActual:$TRAVIS_JDK_VERSION\n"

# Display 'Is Pull Request':
echo -e "Is Pull Request:"
echo -e "\tExpected:false"
echo -e "\tActual:$TRAVIS_PULL_REQUEST\n"

# Display Branch:
echo -e "Branch:"
echo -e "\tExpected:false"
echo -e "\tActual:$TRAVIS_BRANCH\n"

if ["$TRAVIS_REPO_SLUG" == "Valkryst/VTerminal"] &&
   ["$TRAVIS_JDK_VERSION" == "oraclejdk8"] &&
   ["$TRAVIS_PULL_REQUEST" == "false"] &&
   ["$TRAVIS_BRANCH" == "master"]; then
   
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
