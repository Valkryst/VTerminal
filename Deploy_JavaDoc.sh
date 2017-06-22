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
Let's do it, paragraph by paragraph:

#!/bin/bash
The standard shebang line. We don't really need to set this, as we execute it with bash deploy.sh, but I like to put it in anyway.

set -o errexit -o nounset
This sets two options for the shell to make the script more reliable:

errexit: stop executing if any errors occur, by default bash will just continue past any errors to run the next command
nounset: stop executing if an unset variable is encountered, by default bash will use an empty string for the value of such variables.
if [ "$TRAVIS_BRANCH" != "master" ]
then
  echo "This commit was made against the $TRAVIS_BRANCH and not the master! No deploy!"
  exit 0
fi
